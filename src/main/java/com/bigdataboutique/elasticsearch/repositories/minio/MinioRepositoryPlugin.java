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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.env.Environment;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.RepositoryPlugin;
import org.elasticsearch.repositories.Repository;

public class MinioRepositoryPlugin extends Plugin implements RepositoryPlugin {
    // overridable for tests
    @SuppressWarnings("WeakerAccess")
    protected MinioService createStorageService(Settings settings) {
        return new MinioService(settings);
    }

    @Override
    public Map<String, Repository.Factory> getRepositories(Environment env, NamedXContentRegistry namedXContentRegistry) {
        return Collections.singletonMap(MinioRepository.TYPE,
                (metadata) -> new MinioRepository(metadata, env.settings(), namedXContentRegistry, createStorageService(env.settings())));
    }

    @Override
    public List<Setting<?>> getSettings() {
        return Arrays.asList(
                MinioClientSettings.ACCESS_KEY_SETTING,
                MinioClientSettings.SECRET_KEY_SETTING,
                MinioClientSettings.ENDPOINT_SETTING,
                MinioClientSettings.REGION_SETTING,
                MinioClientSettings.IS_SECURE_SETTING);
    }
}
