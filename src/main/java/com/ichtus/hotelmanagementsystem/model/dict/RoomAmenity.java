package com.ichtus.hotelmanagementsystem.model.dict;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomAmenity {

    @Id
    private long id;

    private String amenityName;
    private String amenityDescription;
}
