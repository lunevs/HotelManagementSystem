package com.ichtus.hotelmanagementsystem.model.entities;

import com.ichtus.hotelmanagementsystem.model.anotations.ValidRoomCapacity;
import com.ichtus.hotelmanagementsystem.model.dictionaries.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Room room;

    @NotNull
    @Future
    private Date startDate;

    @NotNull
    @Future
    private Date endDate;
    private BookingStatus bookingStatus;

    @ValidRoomCapacity
    private int numberOfGuests;

    @ManyToOne
    private Account account;

    private boolean deleted;

}
