package com.ichtus.hotelmanagementsystem.model.dto.image;

import com.ichtus.hotelmanagementsystem.model.entities.Image;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.IOException;

@Data
@Accessors(chain = true)
public class ResponseImageData {

    Long imageId;
    String imageTile;
    String imageType;

    public static ResponseImageData of(Image image) throws IOException {
        return new ResponseImageData()
                .setImageId(image.getId())
                .setImageTile(image.getImageTitle())
                .setImageType(image.getImageType().name());
    }
}
