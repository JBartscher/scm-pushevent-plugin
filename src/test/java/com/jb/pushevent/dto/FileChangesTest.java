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
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileChangesTest {

  private FileChanges fileChanges;

  @BeforeEach
  void setUp() {
    fileChanges = new FileChanges(new ObjectMapper().createObjectNode());
  }

  @Test
  void setAdded() {
    Set<String> added = Stream.of("file").collect(Collectors.toCollection(HashSet::new));
    fileChanges.setAdded(added);
    assertNotNull(fileChanges.getAdded());
  }

  @Test
  void getAdded() {
    Set<String> added = Stream.of("file").collect(Collectors.toCollection(HashSet::new));
    fileChanges.setAdded(added);
    assertEquals(added, fileChanges.getAdded());
  }

  @Test
  void setModified() {
    Set<String> modified = Stream.of("file").collect(Collectors.toCollection(HashSet::new));
    fileChanges.setModified(modified);
    assertNotNull(fileChanges.getModified());
  }

  @Test
  void getModified() {
    Set<String> modified = Stream.of("file").collect(Collectors.toCollection(HashSet::new));
    fileChanges.setModified(modified);
    assertEquals(modified, fileChanges.getModified());
  }

  @Test
  void setMoved() {
    Set<String> moved = Stream.of("file").collect(Collectors.toCollection(HashSet::new));
    fileChanges.setMoved(moved);
    assertNotNull(fileChanges.getMoved());
  }

  @Test
  void getMoved() {
    Set<String> moved = Stream.of("file").collect(Collectors.toCollection(HashSet::new));
    fileChanges.setMoved(moved);
    assertEquals(moved, fileChanges.getMoved());
  }

  @Test
  void setCopied() {
    Set<String> copied = Stream.of("file").collect(Collectors.toCollection(HashSet::new));
    fileChanges.setCopied(copied);
    assertNotNull(fileChanges.getCopied());
  }

  @Test
  void getCopied() {
    Set<String> copied = Stream.of("file").collect(Collectors.toCollection(HashSet::new));
    fileChanges.setCopied(copied);
    assertEquals(copied, fileChanges.getCopied());
  }

  @Test
  void setRemoved() {
    Set<String> removed = Stream.of("file").collect(Collectors.toCollection(HashSet::new));
    fileChanges.setRemoved(removed);
    assertNotNull(fileChanges.getRemoved());
  }

  @Test
  void getRemoved() {
    Set<String> removed = Stream.of("file").collect(Collectors.toCollection(HashSet::new));
    fileChanges.setRemoved(removed);
    assertEquals(removed, fileChanges.getRemoved());
  }

  @Test
  void toJsonNode() {
    assertNotNull(fileChanges.toJsonNode());
  }

  @Test
  void getNode() {
    assertNotNull(fileChanges.getNode());
  }

}
