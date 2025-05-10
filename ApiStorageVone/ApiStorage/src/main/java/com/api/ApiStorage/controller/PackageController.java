package com.api.ApiStorage.controller;

import com.api.ApiStorage.Database.Mapper.PackageServiceImpl;
import com.api.ApiStorage.storage.StorageService;
import com.api.ApiStorage.storage.StorageServiceFactory;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static com.google.common.io.Files.getFileExtension;
import static org.springframework.http.MediaTypeFactory.getMediaType;


@RestController
public class PackageController {

    private final StorageServiceFactory storageServiceFactory;
    private final PackageServiceImpl packageServiceImpl;
    private final MinioClient minioClient;

    public PackageController(StorageServiceFactory storageServiceFactory,
                             PackageServiceImpl packageServiceImpl,MinioClient minioClient) {
        this.storageServiceFactory = storageServiceFactory;
        this.packageServiceImpl = packageServiceImpl;
        this.minioClient = minioClient;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{bucketname}/{packageName}/{version}")
    public ResponseEntity<String> uploadPackage(@PathVariable String bucketname,
                                                @PathVariable String packageName,
                                                @PathVariable String version,
                                                @RequestParam("file")
                                                MultipartFile file) {

        String prefix = packageName + "/" + version + "/";
        boolean pathExists = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketname)
                        .prefix(prefix)
                        .recursive(true)
                        .build()
        ).iterator().hasNext();

        if (!pathExists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Belirtilen yol sistemde yok: " + prefix);
        }

        String filename = file.getOriginalFilename();

        if (!filename.endsWith(".pdf") && !filename.endsWith(".json")) {
            return ResponseEntity
                    .badRequest()
                    .body("Sadece .rep ve .json dosyaları yüklenebilir!");
        }



        String objectPath = packageName+
                "/" + version + "/" + file.getOriginalFilename();

        StorageService storageService = storageServiceFactory.getStorageService();

        storageService.store(file,objectPath,bucketname);
        String message = "File uploaded successfully to MinIO.";

        if (filename.endsWith(".json")) {
            try {
                packageServiceImpl.savePackageFromFile(file);

                message += " JSON file also saved to database.";
            } catch (IOException e) {

                System.err.println("JSON dosyası veritabanına kaydedilemedi: " + e.getMessage());
                message += " However, JSON file could not be saved to database.";
            }
        }

        return ResponseEntity.status(HttpStatus.
                CREATED).body(message);
    }

    @GetMapping("{bucketname}/{packageName}/{version}/{fileName}")
    public ResponseEntity<byte[]> downloadPackage(@PathVariable String bucketname,
                                                  @PathVariable String packageName,
                                                  @PathVariable String version,
                                                  @PathVariable String fileName) {

        StorageService storageService = storageServiceFactory.getStorageService();

        try {

            String objectPath =  packageName+
                    "/" + version + "/" + fileName;
            byte[] fileContent = storageService.retrieve(bucketname,objectPath);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF).
                    header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(fileContent);
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving file".getBytes());}
    }

    @GetMapping("/get-pdf-url")
    public ResponseEntity<String> getPdfUrl(
            @RequestParam String bucketname,
            @RequestParam String packageName,
            @RequestParam String version,
            @RequestParam String fileName,
            HttpServletRequest request) {


        String baseUrl = request.getRequestURL().
                toString().replace(request.getRequestURI(),
                        request.getContextPath());

        String fullUrl = String.format("%s/%s/%s/%s/%s", baseUrl,
                bucketname, packageName, version, fileName);

        return ResponseEntity.ok(fullUrl);

    }


}

