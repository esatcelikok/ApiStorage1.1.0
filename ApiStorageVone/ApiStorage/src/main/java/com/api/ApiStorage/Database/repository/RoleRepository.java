package com.api.ApiStorage.Database.repository;

import com.api.ApiStorage.Database.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findById(Long id);
}