package com.smd.smartmenu.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smd.smartmenu.helper.ResourceNotFoundException;
import com.smd.smartmenu.model.User;
import com.smd.smartmenu.repository.UserRepository;
import com.smd.smartmenu.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        User savedUser = userRepository.save(user);
        logger.info("User saved successfully: {}", savedUser);
        return savedUser;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User userFetched = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        // Update fields
        userFetched.setName(user.getName());
        userFetched.setEmail(user.getEmail());
        userFetched.setMobile(user.getMobile());
        userFetched.setPassword(user.getPassword());
        userFetched.setProfilePhoto(user.getProfilePhoto());
        userFetched.setEnabled(user.isEnabled());
        userFetched.setEmailVerified(user.isEmailVerified());
        userFetched.setPhoneVerified(user.isPhoneVerified());

        // Update associated restaurants
        userFetched.setRestaurants(user.getRestaurants());

        // Save updated user to the database
        userRepository.save(userFetched);
        return Optional.of(userFetched);
    }

    @Override
    public void deleteUserById(Long id) {
        User userFetched = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        userRepository.delete(userFetched);
    }

    @Override
    public boolean isUserExistsById(Long id) {
        User userFetched = userRepository.findById(id).orElse(null);
        return (userFetched != null) ? true : false;
    }

    @Override
    public boolean isUserExistsByEmail(String email) {
        User userFetched = userRepository.findByEmail(email).orElse(null);
        return (userFetched != null) ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
