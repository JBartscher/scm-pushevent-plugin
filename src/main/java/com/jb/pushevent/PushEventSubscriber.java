/*
 * MIT License
 *
 * Copyright (c) 2020-present Cloudogu GmbH and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.jb.pushevent;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.legman.Subscribe;
import com.google.common.collect.Iterables;
import com.jb.pushevent.config.PushEventConfigurationStore;
import com.jb.pushevent.dto.Commit;
import com.jb.pushevent.dto.Event;
import com.jb.pushevent.dto.FileChanges;
import com.jb.pushevent.dto.Push;
import com.jb.pushevent.pathcollect.PathCollectFactory;
import com.jb.pushevent.pathcollect.PathCollector;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sonia.scm.EagerSingleton;
import sonia.scm.SCMContextProvider;
import sonia.scm.net.ahc.AdvancedHttpClient;
import sonia.scm.plugin.Extension;
import sonia.scm.repository.Changeset;
import sonia.scm.repository.PostReceiveRepositoryHookEvent;
import sonia.scm.repository.Repository;
import sonia.scm.repository.RepositoryHookEvent;
import sonia.scm.repository.api.HookContext;
import sonia.scm.repository.api.HookFeature;
import sonia.scm.security.Role;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Extension
@EagerSingleton
public class PushEventSubscriber {

  private final PathCollectFactory pathCollectorFactory;
  private final Provider<AdvancedHttpClient> httpClientProvider;

  private static final Logger logger = LoggerFactory.getLogger(PushEventSubscriber.class);
  private final PushEventConfigurationStore pushEventConfigurationStore;

  @Inject
  public PushEventSubscriber(PathCollectFactory pathCollectorFactory, Provider<AdvancedHttpClient> httpClientProvider, PushEventConfigurationStore pushEventConfigurationStore) {
    this.pathCollectorFactory = pathCollectorFactory;
    this.httpClientProvider = httpClientProvider;
    this.pushEventConfigurationStore = pushEventConfigurationStore;
  }

  @Subscribe
  public void onEvent(PostReceiveRepositoryHookEvent event) {
    if (pushEventConfigurationStore.get().getActive()) {
      log.info("Propagate event: " + event.toString());
      handlePushEvent(event);
    } else {
      log.warn("Event was not propagated as the event propagation is turned off. If you want to propagate events go to the settings of this plugin and mark it as active.");
    }
  }

  private void handlePushEvent(RepositoryHookEvent event) {
    Repository repository = event.getRepository();
    if (repository != null) {
      Iterable<Changeset> changesets = event.getContext().getChangesetProvider().getChangesets();

      if (!Iterables.isEmpty(changesets)) {
        try {
          Event eventDto = handlePush(repository, changesets, event);
          // send Push to REST-Api
          EventsCloudoguRestApiService restApiService = new EventsCloudoguRestApiService(httpClientProvider.get(), pushEventConfigurationStore);
          restApiService.sendPush(eventDto);
        } catch (IOException e) {
          log.error("An IOException occurred during the processing of an event. The end point may not be reachable. You may check your plugin configuration. " + e.getMessage());
        }
      } else {
        logger.warn("received hook without changesets");
      }
    } else {
      logger.warn("received hook without repository");
    }
  }

  private Event handlePush(Repository repository, Iterable<Changeset> changesets, RepositoryHookEvent event) throws IOException {
    Push push = createPushDtoFromEvent(repository, changesets, event,  SecurityUtils.getSubject());
    Event eventDto = new Event(new ObjectMapper().createObjectNode());
    eventDto.setData(push);
    eventDto.setId("id");
    eventDto.setTime("time");
    return eventDto;
  }

  Push createPushDtoFromEvent(Repository repository, Iterable<Changeset> changesets, RepositoryHookEvent event, Subject subject) throws IOException {
    ObjectNode objectNode = new ObjectMapper().createObjectNode();
    Push push = new Push(objectNode);

    push.setRepositoryId(repository.getId());
    push.setRepositoryName(repository.getName());
    push.setRepositoryNamespace(repository.getNamespace());
    // push.setInstanceId("NO YET IMPLEMENTED"); Maybe a InstanceId can be used later

    if (subject.hasRole(Role.USER)) {
      String username = (String) subject.getPrincipal();

      if (username != null && !username.equals("")) {
        push.setUser(username);
      } else {
        logger.warn("username is null or empty");
      }
    } else {
      logger.warn("subject has no user role, skip");
    }

    Iterator<Changeset> changesetsIter = changesets.iterator();

    while (changesetsIter.hasNext()) {
      Changeset changeset = changesetsIter.next();

      Commit commit = new Commit(new ObjectMapper().createObjectNode());

      commit.setCommitId(changeset.getId());
      commit.setCommitMessage(changeset.getDescription());
      commit.setDateCommitted(changeset.getCreationDate());
      // TODO find SCMM User
      commit.setAuthor(changeset.getAuthor().toString());
      commit.setBranches(changeset.getBranches());

      FileChanges fileChanges = collectPaths(event.getContext(), repository, changeset);

      commit.setFilesChanged(fileChanges);

      push.addCommit(commit);
      // last commit reached
      if (!changesetsIter.hasNext()) {
        push.setDatePushed(commit.getDateCommitted());
      }
    }
    push.setCommits(push.getCommits()); // this is necessary as addCommit does not update the json-node
    return push;
  }

  private FileChanges collectPaths(HookContext eventContext, Repository repository, Changeset changeset) throws IOException {
    if (eventContext.isFeatureSupported(HookFeature.CHANGESET_PROVIDER)) {
      try (PathCollector collector = pathCollectorFactory.create(repository)) {
        List<Changeset> tmpList = new ArrayList<>();
        tmpList.add(changeset);
        return collector.collectAll(tmpList);
      }
    }
    return new FileChanges(new ObjectMapper().createObjectNode()); //empty object
  }

}
