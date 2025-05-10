package com.api.ApiStorage.Database.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DependencyDTO {

    @JsonProperty("package")
    private String packageName;
    private String version;
}