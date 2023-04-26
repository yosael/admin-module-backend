package com.app.adminmodulebackend.config;

import com.app.adminmodulebackend.dao.ITokenDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LogoutService implements LogoutHandler {

    private final ITokenDAO tokenDAO;

    public LogoutService(ITokenDAO tokenDAO) {
        this.tokenDAO = tokenDAO;
    }

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenDAO.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenDAO.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
