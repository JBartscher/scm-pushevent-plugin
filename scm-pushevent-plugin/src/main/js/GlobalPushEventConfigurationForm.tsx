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
import React, {FC, useEffect, useState} from "react";
import {Checkbox, Configuration, InputField} from "@scm-manager/ui-components";
import {useTranslation} from "react-i18next";

export type Configuration = {
  url: string;
  active: boolean
};

type Props = {
  initialConfiguration: Configuration;
  onConfigurationChange: (p1: Configuration, p2: boolean) => void;
};

const GlobalPushEventConfigurationForm: FC<Props> = ({initialConfiguration, onConfigurationChange}) => {
  const [t] = useTranslation("plugins");

  const [url, setUrl] = useState<string>(initialConfiguration.url);
  const [active, setActive] = useState<boolean>(initialConfiguration.active);

  useEffect(() => {
    onConfigurationChange({url, active}, isValidConfig());
  }, [url, active]);

  const isValidConfig = () => {
    if(url != null){
      return url.length > 0;
    }
    return false;
  };

  return (
    <>
      <Checkbox checked={active} label={"Activated"} name={"onOffToggle"}
                helpText={t("scm-pushevent-plugin.config.form.activeHelpText")}
                onChange={active => setActive(active)}/>
      <InputField
        label={t("scm-pushevent-plugin.config.form.endpointUrl")}
        onChange={url => setUrl(url)}
        type="text"
        value={url}
        helpText={t("scm-pushevent-plugin.config.form.endpointUrlHelpText")}
      />
    </>
  );
};

export default GlobalPushEventConfigurationForm;
