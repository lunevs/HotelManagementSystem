package com.ichtus.hotelmanagementsystem.model.dto.amenity;

import com.ichtus.hotelmanagementsystem.model.dictionaries.ActionType;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class RequestAmenityAction {

    @NotBlank
    long amenityId;

    @NotBlank
    ActionType actionType;
}
