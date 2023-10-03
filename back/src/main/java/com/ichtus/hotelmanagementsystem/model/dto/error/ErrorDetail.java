package com.ichtus.hotelmanagementsystem.model.dto.error;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ErrorDetail {

    private int status;
    private long timeStamp;
    private String title;
    private String detail;
    private String developerMessage;

    private Map<String, List<ValidationError>> errors = new HashMap<>();
}
