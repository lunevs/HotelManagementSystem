package com.ichtus.hotelmanagementsystem.model.dto.amenity;

import com.ichtus.hotelmanagementsystem.model.dao.Amenity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetRoomAmenitiesResponseDto {

    Long id;
    String amenityName;
    float amenityPrice;

    public static GetRoomAmenitiesResponseDto of(Amenity amenity) {
        return new GetRoomAmenitiesResponseDto()
                .setId(amenity.getId())
                .setAmenityName(amenity.getAmenityName())
                .setAmenityPrice(amenity.getAmenityPrice());
    }
}
