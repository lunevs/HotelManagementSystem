package com.ichtus.hotelmanagementsystem.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank
    @Size(min = 5)
    @Column(name = "name", unique = true)
    private String hotelName;

    @Column(name = "description")
    private String hotelDescription;

    @NotBlank
    @Size(min = 5)
    @Column(name = "city")
    private String hotelCity;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> roomsList;

    @ManyToMany
    @JoinTable(
            name = "hotels_amenities",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<Amenity> amenities;

    @ManyToOne
    private Account hotelAdmin;

    private boolean deleted;
}
