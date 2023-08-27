package com.ichtus.hotelmanagementsystem.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
    @ToString.Exclude
    private Location location;


    private boolean deleted;

}
