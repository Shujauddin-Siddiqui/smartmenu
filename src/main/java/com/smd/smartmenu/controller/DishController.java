package com.smd.smartmenu.controller;

import java.util.List;
import java.util.Optional;

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
    private RestaurantService restaurantService;

    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

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

        // Update fields only if they are not null or differ from the existing values
        if (updatedDish.getName() != null) {
            existingDish.setName(updatedDish.getName());
        }
        if (updatedDish.getDescription() != null) {
            existingDish.setDescription(updatedDish.getDescription());
        }
        if (updatedDish.getImage() != null) {
            existingDish.setImage(updatedDish.getImage());
        }
        if (updatedDish.getVideo() != null) {
            existingDish.setVideo(updatedDish.getVideo());
        }
        if (updatedDish.getPrice() != 0) {
            existingDish.setPrice(updatedDish.getPrice());
        }
        if (updatedDish.getCategory() != null) {
            existingDish.setCategory(updatedDish.getCategory());
        }
        if (updatedDish.isAvailabilityStatus() != existingDish.isAvailabilityStatus()) {
            existingDish.setAvailabilityStatus(updatedDish.isAvailabilityStatus());
        }
        if (updatedDish.getPreparationTime() > 0) {
            existingDish.setPreparationTime(updatedDish.getPreparationTime());
        }
        if (updatedDish.getSpiceLevel() != null) {
            existingDish.setSpiceLevel(updatedDish.getSpiceLevel());
        }

        // Save the updated dish
        Dish savedDish = dishService.saveDish(existingDish);

        return ResponseEntity.ok(savedDish);
    }

    @DeleteMapping("/deleteDish/{id}")
    public ResponseEntity<Void> deleteDishById(@PathVariable Long id) {
        logger.info("Delete endpoint invoked for dish ID: {}", id);
        Optional<Dish> dishOptional = dishService.getDishById(id);

        if (dishOptional.isEmpty()) {
            logger.warn("Dish with ID: {} not found for deletion", id);
            return ResponseEntity.notFound().build();
        }

        Dish dishToBeDeleted = dishOptional.get();
        Restaurant restaurant = dishToBeDeleted.getRestaurant();

        // Safely remove the dish from the restaurant's list
        if (restaurant != null) {
            restaurant.getDishes().removeIf(d -> d.getId().equals(dishToBeDeleted.getId()));
            restaurantService.saveRestaurant(restaurant); // Save restaurant to update the association
        }

        // Delete dish
        dishService.deleteDish(dishToBeDeleted);

        // Log deletion
        logger.info("Dish with ID: {} deleted successfully", id);

        return ResponseEntity.noContent().build();
    }

    // Endpoint to check if a dish exists by ID
    @GetMapping("/exists/{dishId}")
    public ResponseEntity<Boolean> isDishExist(@PathVariable Long dishId) {
        boolean exists = dishService.isDishExistById(dishId);
        return ResponseEntity.ok(exists);
    }
}
