package com.ichtus.hotelmanagementsystem.dto;

import lombok.Value;

import java.util.Calendar;

@Value
public class RoomOrderDto {

    String userName;
    String roomTitle;
    Calendar startDate;
    Calendar endDate;

}
