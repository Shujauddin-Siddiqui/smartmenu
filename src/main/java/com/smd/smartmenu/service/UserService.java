package com.smd.smartmenu.service;

import java.util.List;
import java.util.Optional;

import com.smd.smartmenu.model.User;

public interface UserService {

    User saveUser(User user);

    Optional<User> getUserById(Long id);

    Optional<User> updateUser(User user);

    void deleteUserById(Long id);

    boolean isUserExistsById(Long id);

    boolean isUserExistsByEmail(String email);

    List<User> getAllUsers();
}
