package com.api.ApiStorage.Database.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "dependency_entity")
@Data
public class DependencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dependencyId;

    private String packageName;

    private String version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    private PackageEntity packageEntity;
}
