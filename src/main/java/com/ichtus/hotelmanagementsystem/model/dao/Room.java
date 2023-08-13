package com.ichtus.hotelmanagementsystem.model.dao;

import com.ichtus.hotelmanagementsystem.model.anotations.ValidRoomCapacity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", unique = true)
    private String roomName;

    @Column(name = "price")
    private BigDecimal roomPrice;

    @Column(name = "capacity")
    private int roomCapacity;

    @OneToMany
    private Set<Amenity> amenities;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;


    private boolean deleted;

}
