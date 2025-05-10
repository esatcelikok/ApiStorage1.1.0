package com.api.ApiStorage.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileControl {

    @GetMapping("/file")
    public ResponseEntity<String> Packagecontrol() {

       String yazi ="merhaba filestorage";

        return ResponseEntity.ok(yazi);
    }
}
