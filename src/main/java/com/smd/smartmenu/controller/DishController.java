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

    @PostMapping("addDish/Restaurant/{restaurantId}")
    public ResponseEntity<Dish> addDish(@RequestBody Dish dish, @PathVariable Long restaurantId) {
        // Fetch the restaurant from the database
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        // Check if the restaurant exists
        if (restaurant == null) {
            return ResponseEntity.badRequest().body(null); // Restaurant not found, return error
        }

        // Set the fetched restaurant to the dish
        dish.setRestaurant(restaurant);

        // Save the dish
        Dish createdDish = dishService.addDish(dish);

        // Return the saved dish
        return ResponseEntity.ok(createdDish);
    }

    @GetMapping("/getAllDishes")
    public List<Dish> getAllDishes() {
        return dishService.getAllDishes();
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<Dish> getDishesByRestaurant(@PathVariable Long restaurantId) {
        // Fetch the restaurant from the database
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        // Check if the restaurant exists
        if (restaurant == null) {
            return null; // Handle restaurant not found appropriately
        }

        // Get the dishes for the restaurant
        return dishService.getDishesByRestaurant(restaurant);
    }

    @PutMapping("/updateDish/{dishId}")
    public ResponseEntity<Dish> updateDish(@PathVariable Long dishId, @RequestBody Dish updatedDish) {
        Dish existingDish = dishService.getDishById(dishId);

        if (existingDish == null) {
            return ResponseEntity.notFound().build(); // Dish not found, return 404
        }

        // Update fields
        existingDish.setName(updatedDish.getName());
        existingDish.setDescription(updatedDish.getDescription());
        existingDish.setImage(updatedDish.getImage());
        existingDish.setVideo(updatedDish.getVideo());
        existingDish.setPrice(updatedDish.getPrice());
        existingDish.setCategory(updatedDish.getCategory());

        // Save updated dish
        Dish savedDish = dishService.addDish(existingDish);

        return ResponseEntity.ok(savedDish);
    }
}
