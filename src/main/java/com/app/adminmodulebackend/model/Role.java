package com.app.adminmodulebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role_app")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    private String id;

    @Column(name = "name", nullable = false,length = 30)
    private String name;


    public Role(String roleId) {
        this.id = roleId;
    }
}
