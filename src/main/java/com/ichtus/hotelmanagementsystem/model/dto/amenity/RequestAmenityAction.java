package com.ichtus.hotelmanagementsystem.model.dto.amenity;

import com.ichtus.hotelmanagementsystem.model.dictionaries.ActionType;
import lombok.Value;

@Value
public class RequestAmenityAction {

    long amenityId;
    ActionType actionType;
}
