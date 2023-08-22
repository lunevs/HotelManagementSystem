package com.ichtus.hotelmanagementsystem.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", unique = true)
    private String locationName;

    @Column(name = "description")
    private String locationDescription;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private Set<Room> roomsList;

    @OneToMany
    private Set<Amenity> amenities;

    @ManyToOne
    private Account locationAdmin;

    private boolean deleted;
}
