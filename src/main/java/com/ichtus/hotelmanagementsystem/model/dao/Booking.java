package com.ichtus.hotelmanagementsystem.model.dao;

import com.ichtus.hotelmanagementsystem.model.dict.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Room room;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BookingStatus bookingStatus;

    private int numberOfGuests;

    @ManyToOne
    private Account account;

    private boolean deleted;

}
