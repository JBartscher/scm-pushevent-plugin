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
package com.jb.pushevent.pathcollect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb.pushevent.dto.FileChanges;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sonia.scm.repository.Changeset;
import sonia.scm.repository.Modifications;
import sonia.scm.repository.api.RepositoryService;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * The PathCollector class collects all types of modifications which are part of a changeset.
 * <p>
 * These modification types are additions, modifications, coping of files, moving of files and file removables.
 */
public class PathCollector implements Closeable {

  private static final Logger LOG = LoggerFactory.getLogger(PathCollector.class);

  private final RepositoryService repositoryService;

  private final Set<String> added = new HashSet<>();
  private final Set<String> removed = new HashSet<>();
  private final Set<String> modified = new HashSet<>();
  private final Set<String> copied = new HashSet<>();
  private final Set<String> moved = new HashSet<>();

  FileChanges fileChanges = new FileChanges(new ObjectMapper().createObjectNode());


  PathCollector(RepositoryService repositoryService) {
    this.repositoryService = repositoryService;
  }

  /**
   * collect all changes from a changeset and return it as collections of various scopes (added, modified ...)
   *
   * @param changesets
   * @return
   * @throws IOException
   */
  public FileChanges collectAll(Iterable<Changeset> changesets) throws IOException {
    added.clear();
    removed.clear();
    modified.clear();
    copied.clear();
    moved.clear();
    for (Changeset c : changesets) {
      collect(c);
    }
    return fileChanges;
  }

  /**
   * collects all changes in a changeset and fills the right sets corresponding to the change scope
   *
   * @param changeset all changes on that changeset
   * @throws IOException
   */
  private void collect(Changeset changeset) throws IOException {
    Modifications modifications = repositoryService.getModificationsCommand()
      .revision(changeset.getId())
      .getModifications();

    if (modifications != null) {
      sortModificationsIntoAppropriateSet(modifications);
    } else {
      LOG.warn("there is no modifications for the changeset {}", changeset.getId());
    }
  }

  /**
   * takes the modifications and sorts them in the respective sets of their scope
   *
   * @param modifications all modifications from the changeset
   */
  private void sortModificationsIntoAppropriateSet(Modifications modifications) {
    modifications.getAdded().forEach(add -> appendNormalizedPathToSet(added, add.getPath()));
    modifications.getRemoved().forEach(rmv -> appendNormalizedPathToSet(removed, rmv.getPath()));
    modifications.getModified().forEach(mod -> appendNormalizedPathToSet(modified, mod.getPath()));
    modifications.getRenamed().forEach(mov -> appendNormalizedPathToSet(moved, mov.getOldPath() + " --> " + mov.getNewPath()));
    modifications.getCopied().forEach(cpy -> appendNormalizedPathToSet(copied, cpy.getSourcePath() + " --> " + cpy.getTargetPath()));

    fileChanges.setAdded(added);
    fileChanges.setRemoved(removed);
    fileChanges.setModified(modified);
    fileChanges.setMoved(moved);
    fileChanges.setCopied(copied);
  }

  private void appendNormalizedPathToSet(Set<String> modificationSet, String modifiedPaths) {
    modificationSet.add(normalizePath(modifiedPaths));
  }

  private String normalizePath(String path) {
    if (path.startsWith("/")) {
      return path.substring(1);
    }
    return path;
  }

  @Override
  public void close() {
    repositoryService.close();
  }
}
