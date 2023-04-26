package com.app.adminmodulebackend.dto;

public record LoginResponse (
        String token,
        String refreshToken,
        String email,
        String name,
        String roleId) {

}
