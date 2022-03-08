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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;


@Getter
public class Push {

  private final ObjectNode node;

  private String id;
  private String user;

 // TODO private String instanceId;

  private Long datePushed; // client timestamp

  private String repositoryId;
  private String repositoryNamespace;
  private String repositoryName;

  private Set<Commit> commits;

  public Push(ObjectNode node) {
    this.node = node;
  }

  public void setId(String id) {
    this.id = id;
    node.put("id", this.id);
  }

  public void setUser(String user) {
    this.user = user;
    node.put("user", this.user);
  }

  public void setDatePushed(Long datePushed) {
    this.datePushed = datePushed;
    node.put("datePushed", this.datePushed);
  }

  public void setRepositoryId(String repositoryId) {
    this.repositoryId = repositoryId;
    node.put("repositoryId", this.repositoryId);
  }

  public void setRepositoryName(String repositoryName) {
    this.repositoryName = repositoryName;
    node.put("repositoryName", this.repositoryName);
  }

  public void setRepositoryNamespace(String repositoryNamespace) {
    this.repositoryNamespace = repositoryNamespace;
    node.put("repositoryNamespace", this.repositoryNamespace);
  }

  public void addCommit(Commit c) {
    if (this.commits == null) {
      commits = new HashSet<>();
    }
    commits.add(c);
  }

  public void setCommits(Set<Commit> commits) {
    this.commits = commits;
    ArrayNode arrayNode = node.putArray("commits");
    commits.forEach(commit -> arrayNode.add(commit.toJsonNode()));
  }

  public JsonNode toJsonNode() {
    return node;
  }
}


