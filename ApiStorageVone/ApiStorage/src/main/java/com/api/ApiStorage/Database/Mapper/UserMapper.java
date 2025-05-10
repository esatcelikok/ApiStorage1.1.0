package com.api.ApiStorage.Database.Mapper;

import com.api.ApiStorage.Database.DTO.RegisterRequest;
import com.api.ApiStorage.Database.entity.Role;
import com.api.ApiStorage.Database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User toEntity(RegisterRequest dto, Role role) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(role);
        return user;
    }
}
