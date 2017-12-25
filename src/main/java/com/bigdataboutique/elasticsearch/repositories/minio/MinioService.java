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

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.elasticsearch.common.component.AbstractLifecycleComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;

public class MinioService extends AbstractLifecycleComponent {

    @Inject
    protected MinioService(Settings settings) {
        super(settings);
    }

    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }

    @Override
    protected void doClose() throws IOException {

    }

    public MinioClient createClient(Settings settings) throws InvalidPortException, InvalidEndpointException {
        MinioClient minioClient = new MinioClient(
                MinioClientSettings.ENDPOINT_SETTING.get(settings),
                MinioClientSettings.PORT_SETTING.get(settings),
                MinioClientSettings.ACCESS_KEY_SETTING.get(settings).toString(),
                MinioClientSettings.SECRET_KEY_SETTING.get(settings).toString(),
                MinioClientSettings.REGION_SETTING.get(settings),
                MinioClientSettings.IS_SECURE_SETTING.get(settings));
        return minioClient;
    }
}
