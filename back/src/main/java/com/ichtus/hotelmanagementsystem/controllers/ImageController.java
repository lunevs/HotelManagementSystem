package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.model.dictionaries.ImageType;
import com.ichtus.hotelmanagementsystem.model.entities.Image;
import com.ichtus.hotelmanagementsystem.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getImage(@PathVariable Long id) {
        Image image = imageService.getImage(id);
        MediaType contentType = MediaType.IMAGE_PNG;
        if (image.getImageType().equals(ImageType.JPEG)) {
            contentType = MediaType.IMAGE_JPEG;
        }
        return ResponseEntity.ok().contentType(contentType).body(image.getImageBogy());
    }

    @PostMapping
    public CompletableFuture<?> saveImage(@RequestParam("file") MultipartFile file) {
        return imageService.saveImage(file);
    }
}
