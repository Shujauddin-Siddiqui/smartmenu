package com.smd.smartmenu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smd.smartmenu.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
