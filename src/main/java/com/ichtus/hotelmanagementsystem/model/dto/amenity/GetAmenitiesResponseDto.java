package com.ichtus.hotelmanagementsystem.model.dto.amenity;

import com.ichtus.hotelmanagementsystem.model.dao.Amenity;
import com.ichtus.hotelmanagementsystem.model.dict.AmenityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
@NoArgsConstructor
public class GetAmenitiesResponseDto {

    long id;
    String amenityName;
    AmenityType amenityType;
    float amenityPrice;
    String amenityPriceType;

    public static GetAmenitiesResponseDto of(Amenity amenity) {
        return new GetAmenitiesResponseDto()
                .setId(amenity.getId())
                .setAmenityName(amenity.getAmenityName())
                .setAmenityType(amenity.getAmenityType())
                .setAmenityPrice(amenity.getAmenityPrice())
                .setAmenityPriceType(
                        (amenity.getAmenityPrice() > 0) ? "paid" : "free"
                );
    }
}
