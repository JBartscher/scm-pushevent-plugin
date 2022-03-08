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

import com.jb.pushevent.dto.FileChanges;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import sonia.scm.repository.Changeset;
import sonia.scm.repository.Modifications;
import sonia.scm.repository.Person;
import sonia.scm.repository.Repository;
import sonia.scm.repository.api.ModificationsCommandBuilder;
import sonia.scm.repository.api.RepositoryService;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

class PathCollectorTest {

  @Mock
  RepositoryService repositoryService;
  @Mock
  Repository repository;
  @Mock
  PathCollectFactory pathCollectorFactory;
  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  ModificationsCommandBuilder modificationsCommandBuilder;
  Set<Changeset> changesetSet;
  PathCollector pathCollector;

  @BeforeEach
  public void init() throws IOException {
    MockitoAnnotations.initMocks(this);
    repositoryService = Mockito.mock(RepositoryService.class);
    repository = Mockito.mock(Repository.class);

    pathCollectorFactory = Mockito.mock(PathCollectFactory.class);
    when(pathCollectorFactory.create(repository)).thenReturn(new PathCollector(repositoryService));
    when(repositoryService.getModificationsCommand()).thenReturn(modificationsCommandBuilder);
    when(modificationsCommandBuilder.getModifications()).thenReturn(new Modifications("a"));

    changesetSet = new HashSet<>();

    Person p1 = new Person();
    Changeset c1 = new Changeset("id1", 20L, p1, "description for first changeset");
    changesetSet.add(c1);

    Person p2 = new Person();
    Changeset c2 = new Changeset("id2", 30L, p2, "description for second changeset");
    changesetSet.add(c2);

    Person p3 = new Person();
    Changeset c3 = new Changeset("id3", 40L, p3, "description for third changeset");
    changesetSet.add(c3);
  }

  @BeforeEach
  void setUp() {
    pathCollector = pathCollectorFactory.create(repository);
  }

  @Test
  void collectAll() {
    FileChanges f = null;
    try {
      f = pathCollector.collectAll(changesetSet);
    } catch (IOException e) {
      Assertions.fail();
    }
    Assertions.assertNotNull(f);
    System.out.println(f.getAdded());

  }

  @AfterEach
  void close() {
    pathCollector.close();
  }
}
