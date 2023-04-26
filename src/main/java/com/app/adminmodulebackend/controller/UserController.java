package com.app.adminmodulebackend.controller;

import com.app.adminmodulebackend.dao.ITokenDAO;
import com.app.adminmodulebackend.dto.UserRequest;
import com.app.adminmodulebackend.dto.UserResponse;
import com.app.adminmodulebackend.exception.UserNotFoundException;
import com.app.adminmodulebackend.service.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final IUserService userService;
    private final ITokenDAO tokenDAO;

    public UserController(IUserService userService, ITokenDAO tokenDAO){
        this.userService = userService;
        this.tokenDAO = tokenDAO;
    }

    @GetMapping
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest){
        UserResponse createdUser = userService.createUser(userRequest);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) throws UserNotFoundException {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserRequest userRequest) throws UserNotFoundException {
        UserResponse userResponse = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        tokenDAO.deleteAllByUserId(id);
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
