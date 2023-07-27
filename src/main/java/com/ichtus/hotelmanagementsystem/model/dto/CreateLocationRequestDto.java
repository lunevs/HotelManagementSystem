package com.ichtus.hotelmanagementsystem.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Value;

@Value
public class CreateLocationRequestDto {

    @NotEmpty
    String locationName;

    @NotEmpty
    String locationDescription;

}
