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
public class FileChanges {

  private final ObjectNode node;

  public FileChanges(ObjectNode node) {
    this.node = node;
  }

  private Set<String> added = new HashSet<>();
  private Set<String> modified = new HashSet<>();
  private Set<String> removed = new HashSet<>();
  private Set<String> copied = new HashSet<>();
  private Set<String> moved = new HashSet<>();


  public void setAdded(Set<String> added) {
    this.added = added;
    ArrayNode arrayNode = this.node.putArray("added");
    added.forEach(arrayNode::add);
  }

  public void setModified(Set<String> modified) {
    this.modified = modified;
    ArrayNode arrayNode = this.node.putArray("modified");
    modified.forEach(arrayNode::add);
  }

  public void setRemoved(Set<String> removed) {
    this.removed = removed;
    ArrayNode arrayNode = this.node.putArray("removed");
    removed.forEach(arrayNode::add);
  }

  public void setMoved(Set<String> moved) {
    this.moved = moved;
    ArrayNode arrayNode = this.node.putArray("moved");
    moved.forEach(arrayNode::add);
  }

  public void setCopied(Set<String> copied) {
    this.copied = copied;
    ArrayNode arrayNode = this.node.putArray("copied");
    copied.forEach(arrayNode::add);
  }


  public JsonNode toJsonNode() {
    return node;
  }

}
