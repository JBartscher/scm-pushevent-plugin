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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventTest {

  private Event event;

  @BeforeEach
  void setUp() {
    event = new Event(new ObjectMapper().createObjectNode());
  }

  @Test
  void setId() {
    String expected = "123EventId";
    event.setId(expected);
    assertEquals(expected, event.getId());
  }

  @Test
  void getId() {
    String expected = "123EventId";
    event.setId(expected);
    assertEquals(expected, event.getId());
  }

  @Test
  void setTime() {
    String expected = "12-45-12";
    event.setTime(expected);
    assertEquals(expected, event.getTime());
  }

  @Test
  void getTime() {
    String expected = "12-45-12";
    event.setTime(expected);
    assertEquals(expected, event.getTime());
  }

  @Test
  void setData() {
    Push push = new Push(new ObjectMapper().createObjectNode());
    event.setData(push);
    assertNotNull(event.getData());
  }

  @Test
  void getData() {
    Push push = new Push(new ObjectMapper().createObjectNode());
    event.setData(push);
    assertNotNull(event.getData());
  }

  @Test
  void toJsonNode() {
    assertNotNull(event.toJsonNode());
  }

  @Test
  void getNode() {
    assertNotNull(event.getNode());
  }


  @Test
  void getApplication() {
    assertEquals("scmm", Event.APPLICATION);
  }

  @Test
  void getType() {
    assertEquals("push", Event.TYPE);
  }
}
