package com.smd.smartmenu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        //Fetch the user from database ("We will do this probably using principle in future")
        User user = userService.getUserById(userId);

        // Check if the user exists
        if(user==null) {
            return ResponseEntity.badRequest().body(null);
        }

        //set the fetched user to the restaurant
        restaurant.setUser(user);

        //save the restaurant
        Restaurant addedRestaurant = restaurantService.addRestaurant(restaurant);

        return ResponseEntity.ok(addedRestaurant);
    }

    @GetMapping("/getAllRestaurants")
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("getRestaurant/{id}")
    public Restaurant getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    @PutMapping("/updateRestaurant/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(id, restaurant);

        if (updatedRestaurant == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedRestaurant);
    }
    
}
