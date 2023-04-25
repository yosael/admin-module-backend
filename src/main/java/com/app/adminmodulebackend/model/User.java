package com.app.adminmodulebackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
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
