/*
 * Copyright 2018 BigData Boutique
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bigdataboutique.elasticsearch.repositories.minio;

import org.elasticsearch.common.settings.SecureSetting;
import org.elasticsearch.common.settings.SecureString;
import org.elasticsearch.common.settings.Setting;

class MinioClientSettings {
    // prefix for the client settings
    private static final String PREFIX = "minio.client.";

    /** The blob storage endpoint. */
    static final Setting.AffixSetting<String> ENDPOINT_SETTING = Setting.affixKeySetting(PREFIX, "endpoint",
            key -> Setting.simpleString(key, Setting.Property.NodeScope));    /** The blob storage endpoint. */
    static final Setting.AffixSetting<Integer> PORT_SETTING = Setting.affixKeySetting(PREFIX, "port",
            key -> Setting.intSetting(key,9000, Setting.Property.NodeScope));

    /** The access key. */
    static final Setting.AffixSetting<SecureString> ACCESS_KEY_SETTING = Setting.affixKeySetting(PREFIX, "access_key",
            key -> SecureSetting.secureString(key, null));

    /** The secret key. */
    static final Setting.AffixSetting<SecureString> SECRET_KEY_SETTING = Setting.affixKeySetting(PREFIX, "secret_key",
            key -> SecureSetting.secureString(key, null));

    /** The (optional) region name. */
    static final Setting.AffixSetting<String> REGION_SETTING = Setting.affixKeySetting(PREFIX, "region",
            key -> Setting.simpleString(key, Setting.Property.NodeScope));

    /** Whether or not to use secure connection to the blob storage. */
    static final Setting.AffixSetting<Boolean> IS_SECURE_SETTING = Setting.affixKeySetting(PREFIX, "secure",
            key -> Setting.boolSetting(key, true, Setting.Property.NodeScope));
}
