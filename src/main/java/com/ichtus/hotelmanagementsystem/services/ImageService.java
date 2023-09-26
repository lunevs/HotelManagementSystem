package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.DefaultBadRequestException;
import com.ichtus.hotelmanagementsystem.model.dictionaries.ImageType;
import com.ichtus.hotelmanagementsystem.model.dto.image.ResponseImageData;
import com.ichtus.hotelmanagementsystem.model.entities.Image;
import com.ichtus.hotelmanagementsystem.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Image getImage(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isPresent()) {
            return image.get();
        } else {
            throw new ObjectNotFoundException(Image.class.getName(), id);
        }
    }

    @Async
    public CompletableFuture<?> saveImage(MultipartFile file) {
        if (file.isEmpty()) {
            return CompletableFuture.failedFuture(
                    new DefaultBadRequestException("Empty file")
            );
        }
        Image image = new Image();
        image.setImageTitle(file.getOriginalFilename());
        if (Objects.requireNonNull(file.getContentType()).contains("png")) {
            image.setImageType(ImageType.PNG);
        } else if (Objects.requireNonNull(file.getContentType()).contains("jpeg")) {
            image.setImageType(ImageType.JPEG);
        } else {
            return CompletableFuture.failedFuture(
                new DefaultBadRequestException("incorrect file type")
            );
        }
        try {
            image.setImageBogy(file.getBytes());
            return CompletableFuture.completedFuture(
                    ResponseImageData.of(imageRepository.save(image))
            );
        } catch (IOException ex) {
            return CompletableFuture.failedFuture(
                    new DefaultBadRequestException("Could not store file " + file.getOriginalFilename() + ". Please try again!")
            );
        }

    }

}
