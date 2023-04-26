package com.app.adminmodulebackend.dto;

import lombok.Data;


public record LoginRequest (
        String email,
        String password) {

}
