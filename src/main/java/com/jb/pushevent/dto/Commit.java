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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;

import java.util.List;

@Getter
public class Commit {

  private final ObjectNode node;

  private String commitId;
  private String commitMessage;
  private Long dateCommitted;
  private String author;
  private FileChanges fileChanges = new FileChanges(new ObjectMapper().createObjectNode());
  private List<String> branches;

  public Commit(ObjectNode node) {
    this.node = node;
  }

  public void setCommitId(String id) {
    this.commitId = id;
    node.put("commitId", commitId);
  }

  public void setCommitMessage(String commitMessage) {
    this.commitMessage = commitMessage;
    node.put("message", commitMessage);
  }

  public void setDateCommitted(Long dateCommitted) {
    this.dateCommitted = dateCommitted;
    node.put("dateCommitted", dateCommitted);
  }

  public void setFilesChanged(FileChanges changes) {
    this.fileChanges = changes;
    node.set("fileChanges", changes.toJsonNode());
  }

  public void setAuthor(String author) {
    this.author = author;
    node.put("author", author);
  }

  public void setBranches(List<String> branches) {
    this.branches = branches;
    ArrayNode arrayNode = this.node.putArray("branches");
    branches.forEach(arrayNode::add);
  }

  public JsonNode toJsonNode() {
    return node;
  }
}
