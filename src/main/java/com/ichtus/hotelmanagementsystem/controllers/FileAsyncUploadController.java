package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.services.FileStorageManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class FileAsyncUploadController {

    private final FileStorageManager storageManager;

    @PostMapping("/upload")
    public CompletableFuture<?> handleFileUploadUsingCurl(@RequestParam("file") MultipartFile file) {
        storageManager.save(file);
        return CompletableFuture.completedFuture(
                ResponseEntity.ok("File upload started")
        );
    }
}

