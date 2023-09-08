package com.ichtus.hotelmanagementsystem.model.dto.search;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class RequestHotelsSearch {

    private String searchCity;
    private Date startDate;
    private Date endDate;
    private int neededCapacity;
    private float minPrice;
    private float maxPrice;

}
