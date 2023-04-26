package com.app.adminmodulebackend.service;

import com.app.adminmodulebackend.dto.LoginRequest;
import com.app.adminmodulebackend.dto.LoginResponse;
import com.app.adminmodulebackend.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IAuthService {

    public LoginResponse login(LoginRequest loginRequest);

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;


}
