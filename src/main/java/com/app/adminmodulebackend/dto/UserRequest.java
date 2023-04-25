package com.app.adminmodulebackend.dto;

public record UserRequest(
        String name,
        String email,
        String password,
        String roleId) {

}
