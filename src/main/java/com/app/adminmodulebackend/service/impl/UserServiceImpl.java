package com.app.adminmodulebackend.service.impl;

import com.app.adminmodulebackend.dao.IUserDAO;
import com.app.adminmodulebackend.dto.RoleResponse;
import com.app.adminmodulebackend.dto.UserRequest;
import com.app.adminmodulebackend.dto.UserResponse;
import com.app.adminmodulebackend.exception.UserNotFoundException;
import com.app.adminmodulebackend.model.Role;
import com.app.adminmodulebackend.model.User;
import com.app.adminmodulebackend.service.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private IUserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(IUserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {

        User user = convertUserRequestToUser(userRequest);
        User savedUser = userDAO.save(user);
        return convertUserToUserResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userDAO.findAll();
        List<UserResponse> userResponses = users.stream().map(this::convertUserToUserResponse).collect(Collectors.toList());
        return userResponses;
    }

    @Override
    public UserResponse getUserById(Integer id) throws UserNotFoundException {
        User user = userDAO.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return convertUserToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Integer id, UserRequest userRequest) throws UserNotFoundException {
        User user = userDAO.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());
        user.setRole(new Role(userRequest.roleId(), null));
        User savedUser = userDAO.save(user);
        return convertUserToUserResponse(savedUser);
    }

    @Override
    public void deleteUser(Integer id) {
        userDAO.deleteById(id);
    }

    private UserResponse convertUserToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                new RoleResponse(user.getRole().getId(), user.getRole().getName()));
    }

    private User convertUserRequestToUser(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setRole(new Role(userRequest.roleId()));
        return user;
    }
}
