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
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;


@Getter
public class Event {

  private final ObjectNode node;

  private String id;
  static final String APPLICATION = "scmm";
  static final String TYPE = "push";
  private String time;

  private ObjectNode data;

  public Event(ObjectNode node) {
    this.node = node;
    this.node.put("application", APPLICATION);
    this.node.put("type", TYPE);
  }

  public void setId(String id) {
    this.id = id;
    this.node.put("id", id);
  }

  public void setTime(String time) {
    this.time = time;
    this.node.put("time", time);
  }

  public void setData(Push data) {
    this.data = data.getNode();
    node.set("data", data.toJsonNode());
  }

  public JsonNode toJsonNode() {
    return node;
  }
}

/*
Beispiel eines Events das vom Redmine-Plugin an events.cloudogu.com gesendet wird

{
  "id": "d3106cff-701e-4625-9548-3f6cec67eb68",
  "application": "redmine",
  "type": "issue_created",
  "time": "2020-04-18T09:34:38.626+00:00",
  "data": {
    "id": 2,
    "subject": "Awesome Feature",
    "description": "New awesome feature",
    "lock_version": 0,
    "created_on": "2020-04-18T09:34:38.610Z",
    "updated_on": "2020-04-18T09:34:38.610Z",
      ...
    }
  }
}
 */
