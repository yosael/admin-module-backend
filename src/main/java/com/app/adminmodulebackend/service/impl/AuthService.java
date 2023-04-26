package com.app.adminmodulebackend.service.impl;

import com.app.adminmodulebackend.config.JwtService;
import com.app.adminmodulebackend.dao.ITokenDAO;
import com.app.adminmodulebackend.dao.IUserDAO;
import com.app.adminmodulebackend.dto.LoginRequest;
import com.app.adminmodulebackend.dto.LoginResponse;
import com.app.adminmodulebackend.model.CustomUserDetails;
import com.app.adminmodulebackend.model.User;
import com.app.adminmodulebackend.service.IAuthService;
import com.app.adminmodulebackend.token.Token;
import com.app.adminmodulebackend.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final IUserDAO userDAO;
    private final ITokenDAO tokenDAO;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final  CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var user = userDAO.findByEmail(request.email()).orElseThrow();
        var userDetail = new CustomUserDetails(user);
        var jwtToken = jwtService.generateToken(userDetail);
        var refreshToken = jwtService.generateRefreshToken(userDetail);
        revokeAllUserTokens(user.getId());
        saveUserToken(user, jwtToken);
        return new LoginResponse(jwtToken,refreshToken,user.getEmail(),user.getName(),user.getRole().getId());

    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenDAO.save(token);
    }

    private void revokeAllUserTokens(Integer userId) {
        var validUserTokens = tokenDAO.findAllValidTokenByUser(userId);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenDAO.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userDAO.findByEmail(userEmail)
                    .orElseThrow();
            var userDetails = new CustomUserDetails(user);
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                var accessToken = jwtService.generateToken(userDetails);
                revokeAllUserTokens(user.getId());
                saveUserToken(user, accessToken);
                var authResponse = new LoginResponse(accessToken,refreshToken,user.getEmail(),user.getName(),user.getRole().getId());
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
