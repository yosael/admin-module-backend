package com.app.adminmodulebackend.dto;

public record UserResponse(
        Integer id,
        String name,
        String email,
        String password,
        RoleResponse role) {

}
