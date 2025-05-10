package com.api.ApiStorage.Utility;

import com.api.ApiStorage.storage.StorageException;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


public final class MinioUtils {

    public static void validateBucketName(String bucketName) {
        if (bucketName == null || bucketName.trim().isEmpty()) {
            throw new StorageException("Bucket name cannot be empty");
        }

    }

    public static String generateObjectName(MultipartFile file) {
        return UUID.randomUUID() + "_" + file.getOriginalFilename();
    }
}