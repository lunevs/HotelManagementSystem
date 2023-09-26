package com.ichtus.hotelmanagementsystem.model.entities;

import com.ichtus.hotelmanagementsystem.model.anotations.ValidRoomCapacity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * Room entity. Defines table and fields for all hotel's rooms
 * @author smlunev
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank
    @Size(min = 5)
    @Column(name = "name")
    private String roomName;

    @Positive
    @Column(name = "price")
    private BigDecimal roomPrice;

    @Column(name = "capacity")
    private int roomCapacity;

    @ManyToMany
    @JoinTable(
            name = "rooms_amenities",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<Amenity> amenities;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    @ToString.Exclude
    private Hotel hotel;


    private boolean deleted;

}
