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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommitTest {

  private Commit commit;

  @BeforeEach
  void setUp() {
    commit = new Commit(new ObjectMapper().createObjectNode());
  }

  @Test
  void setCommitId() {
    String expected = "123commitId";
    assertNull(commit.getCommitId());
    commit.setCommitId(expected);
    assertNotNull(commit.getCommitId());
    assertEquals(expected, commit.getCommitId());
  }

  @Test
  void setCommitMessage() {
    String expected = "My Message of a regular commit";
    assertNull(commit.getCommitMessage());
    commit.setCommitMessage(expected);
    assertNotNull(commit.getCommitMessage());
    assertEquals(expected, commit.getCommitMessage());
  }

  @Test
  void setDateCommitted() {
    Long expected = 1646160400L;
    assertNull(commit.getDateCommitted());
    commit.setDateCommitted(expected);
    assertNotNull(commit.getDateCommitted());
    assertEquals(expected, commit.getDateCommitted());
  }

  @Test
  void setAuthor() {
    String expected = "Author Mc Authorface";
    assertNull(commit.getAuthor());
    commit.setAuthor(expected);
    assertNotNull(commit.getAuthor());
    assertEquals(expected, commit.getAuthor());
  }

  @Test
  void getCommitId() {
    String expected = "fe124Cs321DS123DSAD1312lolE6867gfdg";
    assertNull(commit.getCommitId());
    commit.setCommitId(expected);
    assertNotNull(commit.getCommitId());
    assertEquals(expected, commit.getCommitId());
  }

  @Test
  void getCommitMessage() {
    String expected = "My Message of a regular commit";
    commit.setCommitMessage(expected);
    assertNotNull(commit.getCommitMessage());
    assertEquals(expected, commit.getCommitMessage());
  }

  @Test
  void getDateCommitted() {
    Long expected = 1646160400L;
    commit.setDateCommitted(expected);
    assertNotNull(commit.getDateCommitted());
    assertEquals(expected, commit.getDateCommitted());
  }

  @Test
  void getAuthor() {
    String expected = "Author Mc Authorface";
    commit.setAuthor(expected);
    assertNotNull(commit.getAuthor());
    assertEquals(expected, commit.getAuthor());
  }

  @Test
  void setFilesChanged() {
    Set<String> fileChangeAdded = Stream.of("a", "b", "c").collect(Collectors.toCollection(HashSet::new));
    FileChanges fileChanges = new FileChanges(new ObjectMapper().createObjectNode());
    fileChanges.setAdded(fileChangeAdded);
    commit.setFilesChanged(fileChanges);
    assertNotNull(commit.getFileChanges());
    assertEquals(fileChanges, commit.getFileChanges());
  }

  @Test
  void getFileChanges() {
    Set<String> fileChangeAdded = Stream.of("a", "b", "c").collect(Collectors.toCollection(HashSet::new));
    FileChanges fileChanges = new FileChanges(new ObjectMapper().createObjectNode());
    fileChanges.setAdded(fileChangeAdded);
    commit.setFilesChanged(fileChanges);
    assertNotNull(commit.getFileChanges());
    assertEquals(fileChanges, commit.getFileChanges());
  }

  @Test
  void setBranches() {
    List<String> branches = Stream.of("a", "b", "c").collect(Collectors.toCollection(ArrayList::new));
    commit.setBranches(branches);
    assertEquals(branches, commit.getBranches());
  }

  @Test
  void getBranches() {
    List<String> branches = Stream.of("a", "b", "c").collect(Collectors.toCollection(ArrayList::new));
    commit.setBranches(branches);
    assertEquals(branches, commit.getBranches());
  }

  @Test
  void toJsonNode() {
    assertNotNull(commit.toJsonNode());
  }

  @Test
  void getNode() {
    assertNotNull(commit.getNode());
  }
}
