package com.api.ApiStorage.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path rootLocation = Paths.get("uploads");

    @Override
    public void store(MultipartFile file, String path,String bucketname) {
        try {
            Files.copy(file.getInputStream(),
                    this.rootLocation.resolve(path));
        } catch (IOException e) {
            throw new StorageException("Failed to store file", e);
        }
    }

    @Override
    public byte[] retrieve(String path,String bucketname) {
        try {
            return Files.readAllBytes(rootLocation.resolve(path));
        } catch (IOException e) {
            throw new StorageException("Failed to retrieve file", e);
        }
    }
}
