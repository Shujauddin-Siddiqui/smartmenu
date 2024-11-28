package com.smd.smartmenu.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.smd.smartmenu.model.User;
import com.smd.smartmenu.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping({ "/addUser", "/saveUser" })
    public User addNewUser(@RequestBody User user) {
        // Create a new User instance using the builder pattern
        if (userService.isUserExistsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User newUser = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .mobile(user.getMobile())
                .profilePhoto(user.getProfilePhoto())
                .isEnabled(user.isEnabled())
                .isEmailVerified(user.isEmailVerified())
                .isPhoneVerified(user.isPhoneVerified())
                .restaurants(user.getRestaurants())
                .build();

        return userService.saveUser(newUser);
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("getUserById/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id).orElse(null);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        // Fetch the user from the database
        Optional<User> userOptional = userService.getUserById(id);

        // Check if the user exists
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Delete the user (and cascade delete restaurants, dishes, and social handles)
        userService.deleteUserById(id);

        return ResponseEntity.noContent().build(); // Return HTTP 204 No Content
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long userId) {
        // Check if the user exists
        Optional<User> existingUserOptional = userService.getUserById(userId);

        if (existingUserOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if user doesn't exist
        }

        User existingUser = existingUserOptional.get();

        // Update the user fields. Set the values that are passed in the request body.
        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            existingUser.setPassword(user.getPassword());
        }
        if (user.getMobile() != null) {
            existingUser.setMobile(user.getMobile());
        }
        if (user.getProfilePhoto() != null) {
            existingUser.setProfilePhoto(user.getProfilePhoto());
        }
        if (user.isEnabled() != existingUser.isEnabled()) {
            existingUser.setEnabled(user.isEnabled());
        }
        if (user.isEmailVerified() != existingUser.isEmailVerified()) {
            existingUser.setEmailVerified(user.isEmailVerified());
        }
        if (user.isPhoneVerified() != existingUser.isPhoneVerified()) {
            existingUser.setPhoneVerified(user.isPhoneVerified());
        }

        // Save the updated user
        User updatedUser = userService.saveUser(existingUser);

        // Return the updated user as a response
        return ResponseEntity.ok(updatedUser);
    }

    // If you want to allow enabling or disabling a user’s account directly, this
    // can be an API that toggles the isEnabled field of the user.
    @PutMapping("/toggleUserStatus/{userId}")
    public ResponseEntity<User> toggleUserStatus(@PathVariable Long userId) {
        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        user.setEnabled(!user.isEnabled()); // Toggle the status
        User updatedUser = userService.saveUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    // If you want to handle just updating the profile photo (without modifying
    // other fields), you can create a separate API for that.
    @PutMapping("/updateProfilePhoto/{userId}")
    public ResponseEntity<User> updateProfilePhoto(@RequestBody String profilePhoto, @PathVariable Long userId) {
        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        user.setProfilePhoto(profilePhoto);
        User updatedUser = userService.saveUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    // This would be helpful for front-end validation.
    @GetMapping("/checkEmailExists/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = userService.isUserExistsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @PutMapping("/resetPassword/{userId}")
    public ResponseEntity<User> resetPassword(@RequestBody String newPassword, @PathVariable Long userId) {
        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        user.setPassword(newPassword); // You might want to encrypt this in a real app
        User updatedUser = userService.saveUser(user);
        return ResponseEntity.ok(updatedUser);
    }

}
