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
import org.elasticsearch.cluster.metadata.RepositoryMetaData;
import org.elasticsearch.common.blobstore.BlobPath;
import org.elasticsearch.common.blobstore.BlobStore;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.repositories.RepositoryException;
import org.elasticsearch.repositories.blobstore.BlobStoreRepository;

public class MinioRepository extends BlobStoreRepository {

    // The internal "type" for Elasticsearch
    public static final String TYPE = "minio";

    static final Setting<String> BUCKET_SETTING = Setting.simpleString("bucket");

    private final BlobStore blobStore;
    private final BlobPath basePath;

    /**
     * Constructs new BlobStoreRepository
     *
     * @param metadata              The metadata for this repository including name and settings
     * @param settings        Settings for the node this repository object is created on
     * @param namedXContentRegistry foo
     */
    @Inject
    MinioRepository(RepositoryMetaData metadata, Settings settings, NamedXContentRegistry namedXContentRegistry,
                    MinioService minioService) {
        super(metadata, settings, namedXContentRegistry);

        String bucket = BUCKET_SETTING.get(metadata.settings());
        if (bucket == null) {
            throw new RepositoryException(metadata.name(), "No bucket defined for s3 gateway");
        }

        final MinioClient client;
        try {
            client = minioService.createClient(metadata.settings());
        } catch (InvalidPortException | InvalidEndpointException e) {
            throw new RepositoryException(metadata.name(), "Unable to initialize repository", e);
        }

        this.blobStore = new MinioBlobStore(client, bucket);

        this.basePath = BlobPath.cleanPath();
    }

    @Override
    protected BlobStore blobStore() {
        return blobStore;
    }

    @Override
    protected BlobPath basePath() {
        return basePath;
    }
}
