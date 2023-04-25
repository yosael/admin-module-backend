package com.app.adminmodulebackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_sequence")
    private Integer id;

    @Column(name = "name", nullable = false,length = 90)
    private String name;

    @Column(name = "email", nullable = false,length = 60)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Role role;
}
