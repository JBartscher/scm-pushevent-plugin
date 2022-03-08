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

import sonia.scm.repository.Repository;
import sonia.scm.repository.api.RepositoryServiceFactory;

import javax.inject.Inject;

/**
 * This class is a duplication of the implementation in scm-pathwp-plugin/src/main/java/sonia/scm/pathwp/PathCollectorFactory.java v2.0.2
 * normally the dependency to the scm-pathwp-plugin in build.gradle would solve this but the class is package private and as such not usable in
 * other plugin projects
 * <p>
 * Remove this class if the implementation in https://github.com/scm-manager/scm-pathwp-plugin/blob/5f21ff1b27814662155acd8933f331b20e6421d4/src/main/java/sonia/scm/pathwp/PathCollectorFactory.java#L37
 * is publicly available
 */
public class PathCollectFactory {

  private final RepositoryServiceFactory repositoryServiceFactory;

  @Inject
  public PathCollectFactory(RepositoryServiceFactory repositoryServiceFactory) {
    this.repositoryServiceFactory = repositoryServiceFactory;
  }

  public PathCollector create(Repository repository) {
    return new PathCollector(repositoryServiceFactory.create(repository));
  }
}
