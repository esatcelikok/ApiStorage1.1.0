package com.api.ApiStorage.storage;

import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class MinioStorageService implements StorageService {
    private final MinioClient minioClient;


    public MinioStorageService(MinioClient minioClient) {
        this.minioClient = minioClient;

    }

    @Override
    public void store(MultipartFile file, String path,String bucketName) {
        try {

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(path)
                            .contentType(file.getContentType())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build());

        } catch (Exception e) {
            throw new StorageException("Failed to store file", e);
        }
    }

    @Override
    public byte[] retrieve(String bucketName,String path) {
        try (InputStream inputStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(path)
                        .build()  )   ) {
            return inputStream.readAllBytes();
        } catch (Exception e) {
            throw new StorageException("Failed to retrieve file", e);
        }
    }
}


