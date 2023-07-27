package com.ichtus.hotelmanagementsystem.model.dto.error;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ValidationError {

    private String code;
    private String message;

}
