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
import React, { FC, useEffect, useState } from "react";
import { Checkbox, InputField } from "@scm-manager/ui-components";
import { useTranslation } from "react-i18next";

export type PushEventConfiguration = {
  url: string;
  active: boolean;
  token: string;
};

type Props = {
  initialConfiguration: PushEventConfiguration;
  onConfigurationChange: (p1: PushEventConfiguration, p2: boolean) => void;
};

const GlobalPushEventConfigurationForm: FC<Props> = ({ initialConfiguration, onConfigurationChange }) => {
  const [t] = useTranslation("plugins");

  const [url, setUrl] = useState(initialConfiguration.url);
  const [active, setActive] = useState(initialConfiguration.active);
  const [token, setToken] = useState(initialConfiguration.token);

  useEffect(() => {
    onConfigurationChange({ url, active, token }, isValidConfig());
  }, [url, active, token]);

  const isValidConfig = () => {
    if (url != null && token != null) {
      return url.length > 0;
    }
    return false;
  };

  return (
    <>
      <Checkbox
        checked={active}
        label={"Activated"}
        name={"onOffToggle"}
        helpText={t("scm-pushevent-plugin.config.form.activeHelpText")}
        onChange={v => setActive(v)}
      />
      <InputField
        label={t("scm-pushevent-plugin.config.form.endpointUrl")}
        onChange={v => setUrl(v)}
        type="text"
        value={url}
        helpText={t("scm-pushevent-plugin.config.form.endpointUrlHelpText")}
      />
      <InputField
        label={t("scm-pushevent-plugin.config.form.token")}
        onChange={v => setToken(v)}
        type="text"
        value={token}
        helpText={t("scm-pushevent-plugin.config.form.tokenHelpText")}
      />
    </>
  );
};

export default GlobalPushEventConfigurationForm;
