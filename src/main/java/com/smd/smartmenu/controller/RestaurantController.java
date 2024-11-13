package com.smd.smartmenu.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smd.smartmenu.model.Restaurant;
import com.smd.smartmenu.model.User;
import com.smd.smartmenu.service.RestaurantService;
import com.smd.smartmenu.service.UserService;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping("/addRestaurant/{userId}")
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant, @PathVariable Long userId) {
        // Fetch the user from database
        Optional<User> userOptional = userService.getUserById(userId);

        // Check if the user exists
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Set the fetched user to the restaurant and save
        restaurant.setUser(userOptional.get());
        Restaurant addedRestaurant = restaurantService.saveRestaurant(restaurant);

        return ResponseEntity.ok(addedRestaurant);
    }

    @GetMapping("/getAllRestaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurant();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/getRestaurant/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Optional<Restaurant> restaurantOptional = restaurantService.getRestaurantById(id);

        return restaurantOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAllRestaurantsOfUser/{userId}")
    public ResponseEntity<List<Restaurant>> getAllRestaurantsOfUser(@PathVariable Long userId) {
        Optional<User> userOptional = userService.getUserById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<Restaurant> userRestaurants = restaurantService.getAllRestaurantOfUser(userOptional.get());
        return ResponseEntity.ok(userRestaurants);
    }

    @PutMapping("/updateRestaurant/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        restaurant.setId(id); // Ensure the restaurant ID is set for updating
        Optional<Restaurant> updatedRestaurant = restaurantService.updateRestaurant(restaurant);

        return updatedRestaurant
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteRestaurant/{id}")
    public ResponseEntity<Void> deleteRestaurantById(@PathVariable Long id) {
        if (!restaurantService.isRestaurantExistById(id)) {
            return ResponseEntity.notFound().build();
        }

        restaurantService.deleteRestaurantById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/updateRating/{id}")
    public ResponseEntity<Restaurant> updateRestaurantRating(@PathVariable Long id, @RequestParam double rating) {
        Optional<Restaurant> restaurantOptional = restaurantService.getRestaurantById(id);
        if (restaurantOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Restaurant restaurant = restaurantOptional.get();
        restaurant.setRating(rating);
        Restaurant updatedRestaurant = restaurantService.saveRestaurant(restaurant);
        return ResponseEntity.ok(updatedRestaurant);
    }
}
