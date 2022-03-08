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
package com.jb.pushevent.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PushEventConfigurationTest {

  private PushEventConfiguration pushEventConfiguration;

  @BeforeEach
  void setUp() {
    pushEventConfiguration = new PushEventConfiguration("url", "token", false);
  }

  @Test
  void getUrl() {
    assertEquals("url", pushEventConfiguration.getUrl());
  }

  @Test
  void getToken() {
    assertEquals("token", pushEventConfiguration.getToken());
  }

  @Test
  void getActive() {
    assertFalse(pushEventConfiguration.getActive());
  }

  @Test
  void isValid() {
    assertTrue(pushEventConfiguration.isValid());
  }

  @Test
  void setUrl() {
    pushEventConfiguration.setUrl("newurl");
    assertEquals("newurl", pushEventConfiguration.getUrl());
  }

  @Test
  void setToken() {
    pushEventConfiguration.setToken("newtoken");
    assertEquals("newtoken", pushEventConfiguration.getToken());
  }

  @Test
  void setActive() {
    pushEventConfiguration.setActive(true);
    assertTrue(pushEventConfiguration.getActive());
  }
}
