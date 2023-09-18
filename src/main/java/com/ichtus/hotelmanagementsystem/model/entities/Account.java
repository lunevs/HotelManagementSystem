package com.ichtus.hotelmanagementsystem.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(min = 3)
    @Column(name = "name", unique = true)
    private String accountName;

    @NotBlank
    @Size(min = 5)
    @Column(name = "password")
    private String accountPassword;

    @NotBlank
    @Email
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
