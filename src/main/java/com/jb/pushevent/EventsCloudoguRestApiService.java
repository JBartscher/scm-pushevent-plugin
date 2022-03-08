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

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;
import com.jb.pushevent.config.PushEventConfigurationStore;
import com.jb.pushevent.dto.Event;
import lombok.extern.slf4j.Slf4j;
import sonia.scm.net.ahc.AdvancedHttpClient;
import sonia.scm.net.ahc.AdvancedHttpRequestWithBody;
import sonia.scm.net.ahc.AdvancedHttpResponse;

import java.io.IOException;

@Slf4j
public class EventsCloudoguRestApiService {

  private String endpointUrl = "http://127.0.0.1:8088/";
  private String token = "";

  private final AdvancedHttpClient httpClient;

  public EventsCloudoguRestApiService(AdvancedHttpClient httpClient, PushEventConfigurationStore pushEventConfigurationStore) {
    this.httpClient = httpClient;
    String configUrl = pushEventConfigurationStore.get().getUrl();
    if (!Strings.isNullOrEmpty(configUrl)) {
      this.endpointUrl = configUrl;
    }
    String configToken = pushEventConfigurationStore.get().getToken();
    if (!Strings.isNullOrEmpty(configToken)) {
      this.token = configToken;
    }
  }

  private AdvancedHttpRequestWithBody createPutRequest(JsonNode payload) {
    final AdvancedHttpRequestWithBody putRequest = this.httpClient.put(endpointUrl + "event/" + System.currentTimeMillis());
    putRequest.jsonContent(payload);
    putRequest.header("Authorization", "Bearer " + token);
    return putRequest;
  }

  public void sendPush(Event eventDto) {
    AdvancedHttpRequestWithBody putRequest = createPutRequest(eventDto.toJsonNode());

    try {
      AdvancedHttpResponse putPushResponse = putRequest.request();
      if (!putPushResponse.isSuccessful()) {
        log.error("Push was not transmitted to endpoint");
      }
    } catch (IOException e) {
      log.error("An IOException occurred during the processing of an event. The end point may not be reachable. You may check your plugin configuration. " + e.getMessage());
    }
  }
}
