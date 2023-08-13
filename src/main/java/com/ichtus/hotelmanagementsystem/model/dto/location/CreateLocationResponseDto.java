package com.ichtus.hotelmanagementsystem.model.dto.location;

import lombok.Value;

@Value
public class CreateLocationResponseDto {
    long id;
    String locationName;
    String locationDescription;
}
