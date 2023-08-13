package com.ichtus.hotelmanagementsystem.model.dao;

import com.ichtus.hotelmanagementsystem.model.dict.AccountRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String accountName;
    private String accountPassword;
    private String accountEmail;

    private Set<AccountRole> roles;

    @OneToMany
    private Set<Location> locations;

    @OneToMany
    private Set<Booking> bookings;

    private boolean deleted;
}
