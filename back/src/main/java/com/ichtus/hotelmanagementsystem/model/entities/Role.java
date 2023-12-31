package com.ichtus.hotelmanagementsystem.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Role entity. Defines table and fields for all account roles
 * @author smlunev
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @Size(min = 5)
    @Column(name = "name")
    private String name;

}
