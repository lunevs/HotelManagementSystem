package com.ichtus.hotelmanagementsystem.model.dto.search;

import com.ichtus.hotelmanagementsystem.model.anotations.ValidRoomCapacity;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class RequestHotelsSearch {

    private String searchCity;

    @Future
    private Date startDate;

    @Future
    private Date endDate;

    @ValidRoomCapacity
    private int neededCapacity;

    @PositiveOrZero
    private float minPrice;

    @PositiveOrZero
    private float maxPrice;

}
