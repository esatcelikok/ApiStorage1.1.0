package com.api.ApiStorage.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Merhaba {

    @GetMapping("/merhaba")
    public String Packagecontrol() {

        String fileContent ="merhaba filestorage";

        return fileContent;
    }

}
