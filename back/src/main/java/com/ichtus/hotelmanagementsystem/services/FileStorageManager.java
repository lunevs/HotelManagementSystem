package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.DefaultBadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.CompletableFuture;

@Service
public class FileStorageManager {

    private final Path fileStorageLocation;

    public FileStorageManager(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new DefaultBadRequestException("Could not create the directory where the uploaded files will be stored.");
        }
    }

    @Async
    public void save(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        fileName = fileName.replace(' ', '_');

        if (file.isEmpty()) {
            throw new DefaultBadRequestException("Empty file");
        }

        if(fileName.contains("..")) {
            throw new DefaultBadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
        }

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            CompletableFuture.completedFuture(fileName);
        } catch (IOException ex) {
            throw new DefaultBadRequestException("Could not store file " + fileName + ". Please try again!");
        }

    }
}
