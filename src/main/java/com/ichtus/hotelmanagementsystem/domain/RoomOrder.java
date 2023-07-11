package com.ichtus.hotelmanagementsystem.domain;

import com.ichtus.hotelmanagementsystem.configuration.RoomOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Room room;

    @ManyToOne
    private User user;

    private RoomOrderStatus status;
    private Calendar startDate;
    private Calendar endDate;

}
