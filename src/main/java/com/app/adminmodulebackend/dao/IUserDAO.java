package com.app.adminmodulebackend.dao;

import com.app.adminmodulebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDAO extends JpaRepository<User, Integer> {

}
