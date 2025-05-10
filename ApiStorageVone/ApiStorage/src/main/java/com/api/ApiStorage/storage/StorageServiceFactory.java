
package com.api.ApiStorage.storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceFactory {
    private final FileSystemStorageService fileSystemStorageService;
    private final MinioStorageService minioStorageService;
    private final String storageStrategy;


    public StorageServiceFactory(FileSystemStorageService fileSystemStorageService,
                                 MinioStorageService minioStorageService,
                                 @Value("${storageStrategy}") String storageStrategy) {
        this.fileSystemStorageService = fileSystemStorageService;
        this.minioStorageService = minioStorageService;
        this.storageStrategy = storageStrategy;
    }

    public StorageService getStorageService() {
        if ("file-system".equalsIgnoreCase(storageStrategy)) {
            return fileSystemStorageService;
        } else if ("object-storage".equalsIgnoreCase(storageStrategy)) {
            return minioStorageService;
        }
        throw new IllegalStateException("Invalid storage strategy: " + storageStrategy);
    }
}


