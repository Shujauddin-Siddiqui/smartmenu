package com.smd.smartmenu.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smd.smartmenu.model.Restaurant;
import com.smd.smartmenu.model.SocialHandle;
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

    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

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
        // Check if the restaurant exists
        Optional<Restaurant> existingRestaurantOptional = restaurantService.getRestaurantById(id);

        if (existingRestaurantOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if restaurant doesn't exist
        }

        Restaurant existingRestaurant = existingRestaurantOptional.get();

        // Update only non-relational fields
        if (restaurant.getName() != null) {
            existingRestaurant.setName(restaurant.getName());
        }
        if (restaurant.getLogo() != null) {
            existingRestaurant.setLogo(restaurant.getLogo());
        }
        if (restaurant.getAddressLine() != null) {
            existingRestaurant.setAddressLine(restaurant.getAddressLine());
        }
        if (restaurant.getCity() != null) {
            existingRestaurant.setCity(restaurant.getCity());
        }
        if (restaurant.getState() != null) {
            existingRestaurant.setState(restaurant.getState());
        }
        if (restaurant.getZipCode() != null) {
            existingRestaurant.setZipCode(restaurant.getZipCode());
        }
        if (restaurant.getCountry() != null) {
            existingRestaurant.setCountry(restaurant.getCountry());
        }
        if (restaurant.getContactNumber() != null) {
            existingRestaurant.setContactNumber(restaurant.getContactNumber());
        }
        if (restaurant.getCuisineType() != null) {
            existingRestaurant.setCuisineType(restaurant.getCuisineType());
        }
        if (restaurant.getRating() != 0) {
            existingRestaurant.setRating(restaurant.getRating());
        }
        if (restaurant.getOpeningTime() != null) {
            existingRestaurant.setOpeningTime(restaurant.getOpeningTime());
        }
        if (restaurant.getClosingTime() != null) {
            existingRestaurant.setClosingTime(restaurant.getClosingTime());
        }

        // Save the updated restaurant
        Restaurant updatedRestaurant = restaurantService.saveRestaurant(existingRestaurant);

        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/deleteRestaurant/{id}")
    public ResponseEntity<Void> deleteRestaurantById(@PathVariable Long id) {
        logger.info("Delete endpoint invoked for restaurant ID: {}", id);
        Optional<Restaurant> restaurantOptional = restaurantService.getRestaurantById(id);

        if (restaurantOptional.isEmpty()) {
            logger.warn("Restaurant with ID: {} not found for deletion", id);
            return ResponseEntity.notFound().build();
        }

        Restaurant restaurantToBeDeleted = restaurantOptional.get();
        User user = restaurantToBeDeleted.getUser();

        // Safely remove the restaurant from the user's list
        if (user != null) {
            user.getRestaurants().removeIf(r -> r.getId().equals(restaurantToBeDeleted.getId()));
            userService.saveUser(user); // Save user to update the association
        }

        // Save user to reflect changes (if necessary)
        userService.saveUser(user);

        // Delete restaurant and dependent entities (handled by CascadeType.REMOVE)
        restaurantService.deleteRestaurant(restaurantToBeDeleted);

        // Log deletion
        logger.info("Restaurant with ID: {} deleted successfully", id);

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

    @PostMapping("/{restaurantId}/addSocialHandles")
    public ResponseEntity<Restaurant> addSocialHandlesToRestaurant(
            @PathVariable Long restaurantId,
            @RequestBody List<SocialHandle> socialHandles) {

        // Fetch the restaurant from database
        Optional<Restaurant> restaurantOptional = restaurantService.getRestaurantById(restaurantId);

        if (restaurantOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Restaurant restaurant = restaurantOptional.get();

        // Add social handles to the restaurant
        socialHandles.forEach(handle -> handle.setRestaurant(restaurant));
        restaurant.getSocialHandles().addAll(socialHandles);

        // Save the updated restaurant
        Restaurant updatedRestaurant = restaurantService.saveRestaurant(restaurant);

        return ResponseEntity.ok(updatedRestaurant);
    }

}
