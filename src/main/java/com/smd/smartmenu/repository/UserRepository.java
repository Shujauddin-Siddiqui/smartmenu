package com.smd.smartmenu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smd.smartmenu.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

}
