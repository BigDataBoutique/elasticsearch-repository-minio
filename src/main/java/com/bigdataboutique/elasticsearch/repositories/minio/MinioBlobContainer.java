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

import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.NoResponseException;
import org.elasticsearch.common.blobstore.BlobMetaData;
import org.elasticsearch.common.blobstore.BlobPath;
import org.elasticsearch.common.blobstore.BlobStoreException;
import org.elasticsearch.common.blobstore.support.AbstractBlobContainer;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class MinioBlobContainer extends AbstractBlobContainer {
    private final MinioBlobStore blobStore;
    private final String keyPath;

    MinioBlobContainer(BlobPath path, MinioBlobStore blobStore) {
        super(path);
        this.blobStore = blobStore;
        this.keyPath = path.buildAsString();
    }

    @Override
    public boolean blobExists(String blobName) {
        try {
            return blobStore.client().statObject(blobStore.bucket(), buildKey(blobName)).length() > 0;
        } catch (InvalidKeyException | InvalidBucketNameException e) {
            return false;
        } catch (IOException | XmlPullParserException | InsufficientDataException | NoSuchAlgorithmException |
                InternalException | ErrorResponseException | NoResponseException e) {
            throw new BlobStoreException("failed to check if blob exists", e);
        }
    }

    @Override
    public InputStream readBlob(String blobName) throws IOException {
        return null;
    }

    @Override
    public void writeBlob(String blobName, InputStream inputStream, long blobSize) throws IOException {
        if (blobExists(blobName)) {
            throw new FileAlreadyExistsException("blob [" + blobName + "] already exists, cannot overwrite");
        }
    }

    @Override
    public void deleteBlob(String blobName) throws IOException {

    }

    @Override
    public Map<String, BlobMetaData> listBlobs() throws IOException {
        return null;
    }

    @Override
    public Map<String, BlobMetaData> listBlobsByPrefix(String blobNamePrefix) throws IOException {
        return null;
    }

    @Override
    public void move(String sourceBlobName, String targetBlobName) throws IOException {

    }

    private String buildKey(String blobName) {
        return keyPath + blobName;
    }
}
