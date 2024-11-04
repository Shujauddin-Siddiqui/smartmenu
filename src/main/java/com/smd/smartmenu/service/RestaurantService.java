package com.smd.smartmenu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smd.smartmenu.model.Restaurant;
import com.smd.smartmenu.repository.RestaurantRepository;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) {
        return restaurantRepository.findById(id).map(existingRestaurant -> {
            existingRestaurant.setName(updatedRestaurant.getName());
            existingRestaurant.setLogo(updatedRestaurant.getLogo());
            existingRestaurant.setSocialHandles(updatedRestaurant.getSocialHandles());
            return restaurantRepository.save(existingRestaurant);
            
        }).orElse(null); // Return null if restaurant ID not found
    }
}
