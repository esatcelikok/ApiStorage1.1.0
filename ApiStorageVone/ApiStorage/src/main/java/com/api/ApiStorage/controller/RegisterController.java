package com.api.ApiStorage.controller;

import com.api.ApiStorage.Database.DTO.RegisterRequest;
import com.api.ApiStorage.Database.Mapper.UserMapper;
import com.api.ApiStorage.Database.entity.Role;
import com.api.ApiStorage.Database.entity.User;
import com.api.ApiStorage.Database.repository.RoleRepository;
import com.api.ApiStorage.Database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @PostMapping()
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        if(request.getUsername().equals("Admin")){
            Role userRole = roleRepository.findById(2L)
                    .orElseThrow(() -> new RuntimeException("ROLE_USER not found."));
            User user = userMapper.toEntity(request,userRole);
            userRepository.save(user);
            return ResponseEntity.ok("User registered successfully");
        }

        Role userRole = roleRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found."));

        User user = userMapper.toEntity(request,userRole);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}
