package com.smd.smartmenu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smd.smartmenu.model.User;
import com.smd.smartmenu.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
