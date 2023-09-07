package com.ichtus.hotelmanagementsystem.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", unique = true)
    private String accountName;

    @Column(name = "password")
    private String accountPassword;

    @Column(name = "email", unique = true)
    private String accountEmail;

    @OneToMany
    private List<Hotel> hotels;

    @OneToMany
    private List<Booking> bookings;

    @ManyToOne
    private Role role;

    private boolean deleted;
}
