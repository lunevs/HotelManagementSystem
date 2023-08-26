package com.ichtus.hotelmanagementsystem.model.entities;

import com.ichtus.hotelmanagementsystem.model.dictionaries.AccountRole;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;


//    @Column(name = "granted_date")
//    private LocalDate grantedDate;
//
//    @ManyToOne
//    private Account grantedBy;
}
