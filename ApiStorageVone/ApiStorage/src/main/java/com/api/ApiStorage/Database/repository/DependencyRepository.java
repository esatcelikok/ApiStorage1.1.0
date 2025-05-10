package com.api.ApiStorage.Database.repository;

import com.api.ApiStorage.Database.entity.DependencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DependencyRepository extends JpaRepository<DependencyEntity, Long> {


}