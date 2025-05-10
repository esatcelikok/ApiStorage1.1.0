package com.api.ApiStorage.Database.DTO;

import lombok.Data;
import java.util.List;

@Data
public class PackageDTO {

    private String name;
    private String version;
    private String author;
    private List<DependencyDTO> dependencies;
}