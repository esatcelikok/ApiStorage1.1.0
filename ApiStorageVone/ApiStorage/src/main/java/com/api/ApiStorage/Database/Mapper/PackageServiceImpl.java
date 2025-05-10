package com.api.ApiStorage.Database.Mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.api.ApiStorage.Database.DTO.PackageDTO;
import com.api.ApiStorage.Database.entity.DependencyEntity;
import com.api.ApiStorage.Database.entity.PackageEntity;
import com.api.ApiStorage.Database.repository.PackageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PackageServiceImpl {

    private final PackageRepository packageRepository;
    private final PackageMapper packageMapper;
    public PackageServiceImpl(PackageRepository packageRepository,
                              PackageMapper packageMapper) {
        this.packageRepository = packageRepository;
        this.packageMapper = packageMapper;
    }

    public void savePackageFromFile(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        PackageDTO packageDTO = objectMapper.readValue(file.getInputStream(),
                PackageDTO.class);

        PackageEntity packageEntity = packageMapper.toEntity(packageDTO);

        if (packageEntity.getDependencies() != null) {
            for (DependencyEntity dependency : packageEntity.getDependencies()) {

                dependency.setPackageEntity(packageEntity);
            }
        }



        packageRepository.save(packageEntity);
    }
}
