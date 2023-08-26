package com.ichtus.hotelmanagementsystem.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Set;

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
    private Set<Location> locations;

    @OneToMany
    private Set<Booking> bookings;

//    @ManyToMany
//    @JoinTable(
//            name = "accounts_roles",
//            joinColumns = @JoinColumn(name = "account_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private Collection<Role> roles;

    @ManyToOne
    private Role role;

    private boolean deleted;
}
