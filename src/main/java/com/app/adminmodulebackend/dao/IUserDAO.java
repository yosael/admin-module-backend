package com.app.adminmodulebackend.dao;

import com.app.adminmodulebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserDAO extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
