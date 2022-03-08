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
package com.jb.pushevent.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PushTest {

  private Push push;

  @BeforeEach
  void setUp() {
    push = new Push(new ObjectMapper().createObjectNode());
  }

  @Test
  void setId() {
    String expected = "push123";
    push.setId(expected);
    assertEquals(expected, push.getId());
  }

  @Test
  void getId() {
    String expected = "push123";
    push.setId(expected);
    assertEquals(expected, push.getId());
  }

  @Test
  void setUser() {
    String expected = "User Mc Userface";
    push.setUser(expected);
    assertEquals(expected, push.getUser());
  }

  @Test
  void getUser() {
    String expected = "User Mc Userface";
    push.setUser(expected);
    assertEquals(expected, push.getUser());
  }

  @Test
  void setDatePushed() {
    Long expected = 1499070300L;
    push.setDatePushed(expected);
    assertEquals(expected, push.getDatePushed());
  }

  @Test
  void getDatePushed() {
    Long expected = 1499070300L;
    push.setDatePushed(expected);
    assertEquals(expected, push.getDatePushed());
  }


  @Test
  void setRepositoryId() {
    String expected = "saf912DJ1230sadjk12p3";
    push.setRepositoryId(expected);
    assertEquals(expected, push.getRepositoryId());
  }

  @Test
  void getRepositoryId() {
    String expected = "saf912DJ1230sadjk12p3";
    push.setRepositoryId(expected);
    assertEquals(expected, push.getRepositoryId());
  }


  @Test
  void setRepositoryName() {
    String expected = "RepositoryName";
    push.setRepositoryName(expected);
    assertEquals(expected, push.getRepositoryName());
  }

  @Test
  void getRepositoryName() {
    String expected = "RepositoryName";
    push.setRepositoryName(expected);
    assertEquals(expected, push.getRepositoryName());
  }


  @Test
  void setRepositoryNamespace() {
    String expected = "RepositoryNamespace";
    push.setRepositoryNamespace(expected);
    assertEquals(expected, push.getRepositoryNamespace());
  }


  @Test
  void getRepositoryNamespace() {
    String expected = "RepositoryNamespace";
    push.setRepositoryNamespace(expected);
    assertEquals(expected, push.getRepositoryNamespace());
  }

  @Test
  void addCommit() {
    Commit commit = new Commit(new ObjectMapper().createObjectNode());
    push.addCommit(commit);
    assertFalse(push.getCommits().isEmpty());
  }

  @Test
  void setCommits() {
    Set<Commit> commits = Stream.of(new Commit(new ObjectMapper().createObjectNode())).collect(Collectors.toCollection(HashSet::new));
    push.setCommits(commits);
    assertFalse(push.getCommits().isEmpty());
  }

  @Test
  void getCommits() {
    Set<Commit> commits = Stream.of(new Commit(new ObjectMapper().createObjectNode())).collect(Collectors.toCollection(HashSet::new));
    push.setCommits(commits);
    assertFalse(push.getCommits().isEmpty());
  }

  @Test
  void toJsonNode() {
    assertNotNull(push.toJsonNode());
  }

  @Test
  void getNode() {
    assertNotNull(push.getNode());
  }
}
