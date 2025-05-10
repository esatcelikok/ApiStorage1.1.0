package com.api.ApiStorage.Database.Mapper;

import com.api.ApiStorage.Database.DTO.PackageDTO;
import com.api.ApiStorage.Database.entity.PackageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PackageMapper {

    PackageMapper INSTANCE = Mappers.getMapper(PackageMapper.class);

    PackageEntity toEntity(PackageDTO packageDTO);

    PackageDTO toDto(PackageEntity packageEntity);
}
