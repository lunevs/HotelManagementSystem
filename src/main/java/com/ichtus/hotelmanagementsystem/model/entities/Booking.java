package com.ichtus.hotelmanagementsystem.model.entities;

import com.ichtus.hotelmanagementsystem.model.anotations.ValidRoomCapacity;
import com.ichtus.hotelmanagementsystem.model.dictionaries.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
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

    @PositiveOrZero
    private int numberOfGuests;

    @ManyToOne
    private Account account;

    private boolean deleted;

}
