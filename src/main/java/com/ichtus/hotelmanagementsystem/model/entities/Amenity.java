package com.ichtus.hotelmanagementsystem.model.entities;

import com.ichtus.hotelmanagementsystem.model.dictionaries.AmenityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "type"})})
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String amenityName;

    @Column(name = "description")
    private String amenityDescription;

    @Column(name = "type")
    private AmenityType amenityType;

    @Column(name = "price")
    private float amenityPrice;

}
