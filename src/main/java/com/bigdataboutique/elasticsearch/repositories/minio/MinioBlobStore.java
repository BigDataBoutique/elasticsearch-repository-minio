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
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.NoResponseException;
import org.elasticsearch.common.blobstore.BlobContainer;
import org.elasticsearch.common.blobstore.BlobPath;
import org.elasticsearch.common.blobstore.BlobStore;
import org.elasticsearch.common.blobstore.BlobStoreException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MinioBlobStore implements BlobStore {
    private final MinioClient client;
    private final String bucket;

    MinioBlobStore(MinioClient client, String bucket) {
        this.client = client;
        this.bucket = bucket;
    }

    @Override
    public BlobContainer blobContainer(BlobPath path) {
        return new MinioBlobContainer(path, this);
    }

    @Override
    public void delete(BlobPath path) throws IOException {
        // TODO
        try {
            client.removeObject(bucket, path.buildAsString());
        } catch (InvalidKeyException | InvalidBucketNameException e) {
            return;
        } catch (InsufficientDataException | NoResponseException | NoSuchAlgorithmException |
                ErrorResponseException | InternalException | XmlPullParserException e) {
            throw new BlobStoreException("Error while trying to delete path", e);
        }
    }

    public MinioClient client() {
        return client;
    }

    public String bucket() {
        return bucket;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public String toString() {
        return bucket;
    }
}
