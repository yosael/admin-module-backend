package com.app.adminmodulebackend.service;

import com.app.adminmodulebackend.dto.UserRequest;
import com.app.adminmodulebackend.dto.UserResponse;
import com.app.adminmodulebackend.exception.UserNotFoundException;

import java.util.List;

public interface IUserService {
    public UserResponse createUser(UserRequest userRequest);

    public List<UserResponse> getAllUsers();

    public UserResponse getUserById(Integer id) throws UserNotFoundException;

    public UserResponse updateUser(Integer id, UserRequest userRequest) throws UserNotFoundException;

    public void deleteUser(Integer id);
}
