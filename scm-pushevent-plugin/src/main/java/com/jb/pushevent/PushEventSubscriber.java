package com.jb.pushevent;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Iterables;
import com.jb.pushevent.pathcollect.PathCollectFactory;
import com.jb.pushevent.pathcollect.PathCollector;
import com.jb.pushevent.push.Commit;
import com.jb.pushevent.push.Push;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sonia.scm.EagerSingleton;
import sonia.scm.net.ahc.AdvancedHttpClient;
import sonia.scm.plugin.Extension;
import com.github.legman.Subscribe;
import sonia.scm.repository.Changeset;
import sonia.scm.repository.PostReceiveRepositoryHookEvent;
import sonia.scm.repository.Repository;
import sonia.scm.repository.RepositoryHookEvent;
import sonia.scm.repository.api.HookContext;
import sonia.scm.repository.api.HookFeature;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

@Extension
@EagerSingleton
public class PushEventSubscriber {

  private final PathCollectFactory pathCollectorFactory;
  private final Provider<AdvancedHttpClient> httpClientProvider;

  private static final Logger logger = LoggerFactory.getLogger(PushEventSubscriber.class);

  @Inject
  public PushEventSubscriber(PathCollectFactory pathCollectorFactory, Provider<AdvancedHttpClient> httpClientProvider) {
    this.pathCollectorFactory = pathCollectorFactory;
    this.httpClientProvider = httpClientProvider;
  }


  @Subscribe
  public void onEvent(PostReceiveRepositoryHookEvent event) {
    handlePushEvent(event);
  }

  private void handlePushEvent(RepositoryHookEvent event) {
    Repository repository = event.getRepository();

    if (repository != null) {
      Iterable<Changeset> changesets = event.getContext().getChangesetProvider().getChangesets();

      if (!Iterables.isEmpty(changesets)) {
        try {
          Push push = handlePush(repository, changesets, event);
          // send Push to REST-Api
          EventsCloudoguRestApiService restApiService = new EventsCloudoguRestApiService(httpClientProvider.get());
          restApiService.sendPush(push);
        } catch (IOException e) {
          e.printStackTrace();
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        logger.warn("received hook without changesets");
      }
    } else {
      logger.warn("received hook without repository");
    }
  }

  private Push handlePush(Repository repository, Iterable<Changeset> changesets, RepositoryHookEvent event) throws IOException {
    return createPushObjectFromEvent(repository, changesets, event);
  }

  Push createPushObjectFromEvent(Repository repository, Iterable<Changeset> changesets, RepositoryHookEvent event) throws IOException {
    ObjectNode objectNode = new ObjectMapper().createObjectNode();
    Push push = new Push(objectNode);

    push.setRepositoryId(repository.getId());
    push.setRepositoryName(repository.getName());

    push.setInstanceId("NO YET IMPLEMENTED");

    Iterator changesetsIter = changesets.iterator();

    while (changesetsIter.hasNext()) {
      Changeset changeset = (Changeset) changesetsIter.next();

      objectNode = new ObjectMapper().createObjectNode();
      Commit commit = new Commit(objectNode);

      commit.setCommitId(changeset.getId());
      commit.setCommitMessage(changeset.getDescription());
      commit.setFilesChanged(collectPaths(event.getContext(), repository, changeset));
      commit.setDateCommitted(changeset.getCreationDate());
      commit.setAuthor(changeset.getAuthor().toString());
      push.addCommit(commit);
      // last commit reached
      if (!changesetsIter.hasNext()) {
        push.setDatePushed(commit.getDateCommitted());
        push.setLastCommit(commit);
        push.setAuthor(changeset.getAuthor().toString());
      }
    }
    push.setCommits(push.getCommits()); // this is necessary as addCommit does not update the json-node
    push.setFilesChangedOverall(collectAllPaths(event.getContext(), repository));

    return push;
  }


  private Set<String> collectAllPaths(HookContext eventContext, Repository repository) throws IOException {
    if (eventContext.isFeatureSupported(HookFeature.CHANGESET_PROVIDER)) {
      try (PathCollector collector = pathCollectorFactory.create(repository)) {
        return collector.collectAll(eventContext.getChangesetProvider().getChangesets());
      }
    }
    return Collections.emptySet();
  }

  private Set<String> collectPaths(HookContext eventContext, Repository repository, Changeset changeset) throws IOException {
    if (eventContext.isFeatureSupported(HookFeature.CHANGESET_PROVIDER)) {
      try (PathCollector collector = pathCollectorFactory.create(repository)) {
        return collector.collectSingle(changeset);
      }
    }
    return Collections.emptySet();
  }

}