package com.api.ApiStorage.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void store(MultipartFile file, String path,String bucketname);
    byte[] retrieve(String path,String bucketname);
}
