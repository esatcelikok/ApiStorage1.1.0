package com.api.ApiStorage.Database.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "usersnew")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}