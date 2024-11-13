package com.smd.smartmenu.service;

import java.util.List;
import java.util.Optional;

import com.smd.smartmenu.model.Restaurant;
import com.smd.smartmenu.model.User;

public interface RestaurantService {

    Restaurant saveRestaurant(Restaurant restaurant);

    Optional<Restaurant> getRestaurantById(Long id);

    List<Restaurant> getAllRestaurant();

    List<Restaurant> getAllRestaurantOfUser(User user);

    Optional<Restaurant> updateRestaurant(Restaurant restaurant);

    void deleteRestaurantById(Long id);

    boolean isRestaurantExistById(Long id);
}