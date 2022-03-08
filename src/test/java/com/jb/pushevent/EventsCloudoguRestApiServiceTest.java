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
package com.jb.pushevent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb.pushevent.config.PushEventConfiguration;
import com.jb.pushevent.config.PushEventConfigurationStore;
import com.jb.pushevent.dto.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import sonia.scm.net.ahc.AdvancedHttpClient;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EventsCloudoguRestApiServiceTest {

  private EventsCloudoguRestApiService eventsCloudoguRestApiService;

  @Mock
  private AdvancedHttpClient httpClient;

  @BeforeEach
  void setUp() {
    PushEventConfigurationStore pushEventConfigurationStore = mock(PushEventConfigurationStore.class);
    PushEventConfiguration pushEventConfiguration = mock(PushEventConfiguration.class);

    when(pushEventConfiguration.getUrl()).thenReturn("/endpoint");
    when(pushEventConfiguration.getToken()).thenReturn("token");
    when(pushEventConfiguration.getActive()).thenReturn(true);
    when(pushEventConfigurationStore.get()).thenReturn(pushEventConfiguration);

    eventsCloudoguRestApiService = new EventsCloudoguRestApiService(httpClient, pushEventConfigurationStore);
  }

  @Test
  void sendPush() {
    Event event = new Event(new ObjectMapper().createObjectNode());
      assertThrows(RuntimeException.class, () -> eventsCloudoguRestApiService.sendPush(event));
  }
}
