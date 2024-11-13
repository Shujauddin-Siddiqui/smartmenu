package com.smd.smartmenu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smd.smartmenu.helper.ResourceNotFoundException;
import com.smd.smartmenu.model.Dish;
import com.smd.smartmenu.model.Restaurant;
import com.smd.smartmenu.service.DishService;
import com.smd.smartmenu.service.RestaurantService;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RestaurantService restaurantService; // Inject RestaurantService to fetch the restaurant

    @PostMapping("/addDish/restaurant/{restaurantId}")
    public ResponseEntity<Dish> addDish(@RequestBody Dish dish, @PathVariable Long restaurantId) {
        // Fetch the restaurant from the database
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Found"));

        // Set the fetched restaurant to the dish
        dish.setRestaurant(restaurant);

        // Save the dish
        Dish createdDish = dishService.saveDish(dish);

        // Return the saved dish
        return ResponseEntity.ok(createdDish);
    }

    @GetMapping("/getAllDishes")
    public List<Dish> getAllDishes() {
        return dishService.getAllDishes();
    }

    @GetMapping("/getDish/{dishId}")
    public ResponseEntity<Dish> getDishById(@PathVariable Long dishId) {
        Dish dish = dishService.getDishById(dishId)
                .orElseThrow(() -> new ResourceNotFoundException("Dish Not Found"));
        return ResponseEntity.ok(dish);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<Dish> getDishesByRestaurant(@PathVariable Long restaurantId) {
        // Fetch the restaurant from the database
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Found"));

        // Get the dishes for the restaurant
        return dishService.getDishesByRestaurant(restaurant);
    }

    @PutMapping("/updateDish/{dishId}")
    public ResponseEntity<Dish> updateDish(@PathVariable Long dishId, @RequestBody Dish updatedDish) {
        Dish existingDish = dishService.getDishById(dishId)
                .orElseThrow(() -> new ResourceNotFoundException("Dish Not Found"));

        // Update fields
        existingDish.setName(updatedDish.getName());
        existingDish.setDescription(updatedDish.getDescription());
        existingDish.setImage(updatedDish.getImage());
        existingDish.setVideo(updatedDish.getVideo());
        existingDish.setPrice(updatedDish.getPrice());
        existingDish.setCategory(updatedDish.getCategory());
        existingDish.setAvailabilityStatus(updatedDish.isAvailabilityStatus());
        existingDish.setPreparationTime(updatedDish.getPreparationTime());
        existingDish.setSpiceLevel(updatedDish.getSpiceLevel());

        // Save updated dish
        Dish savedDish = dishService.saveDish(existingDish);

        return ResponseEntity.ok(savedDish);
    }

    @DeleteMapping("/deleteDish/{dishId}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long dishId) {
        dishService.deleteDishById(dishId);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to check if a dish exists by ID
    @GetMapping("/exists/{dishId}")
    public ResponseEntity<Boolean> isDishExist(@PathVariable Long dishId) {
        boolean exists = dishService.isDishExistById(dishId);
        return ResponseEntity.ok(exists);
    }
}
