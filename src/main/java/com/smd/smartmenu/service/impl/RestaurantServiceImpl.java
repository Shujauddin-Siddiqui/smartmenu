package com.smd.smartmenu.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smd.smartmenu.helper.ResourceNotFoundException;
import com.smd.smartmenu.model.Restaurant;
import com.smd.smartmenu.model.User;
import com.smd.smartmenu.repository.RestaurantRepository;
import com.smd.smartmenu.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        logger.info("Restaurant saved successfully: {}", savedRestaurant);
        return savedRestaurant;
    }

    @Override
    public Optional<Restaurant> getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> getAllRestaurantOfUser(User user) {
        return restaurantRepository.findByUser(user);
    }

    @Override
    public Optional<Restaurant> updateRestaurant(Restaurant restaurant) {
        Restaurant restaurantFetched = restaurantRepository.findById(restaurant.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Found"));

        // Update fields
        restaurantFetched.setName(restaurant.getName());
        restaurantFetched.setLogo(restaurant.getLogo());
        restaurantFetched.setAddressLine(restaurant.getAddressLine());
        restaurantFetched.setCity(restaurant.getCity());
        restaurantFetched.setState(restaurant.getState());
        restaurantFetched.setZipCode(restaurant.getZipCode());
        restaurantFetched.setCountry(restaurant.getCountry());
        restaurantFetched.setContactNumber(restaurant.getContactNumber());
        restaurantFetched.setCuisineType(restaurant.getCuisineType());
        restaurantFetched.setRating(restaurant.getRating());
        restaurantFetched.setOpeningTime(restaurant.getOpeningTime());
        restaurantFetched.setClosingTime(restaurant.getClosingTime());

        // Update associated dishes and social handles
        restaurantFetched.setDishes(restaurant.getDishes());
        restaurantFetched.setSocialHandles(restaurant.getSocialHandles());

        restaurantRepository.save(restaurantFetched);
        logger.info("Restaurant updated successfully: {}", restaurantFetched);
        return Optional.of(restaurantFetched);
    }

    @Override
    public void deleteRestaurantById(Long id) {
        Restaurant restaurantFetched = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Found"));
        restaurantRepository.delete(restaurantFetched);
        logger.info("Restaurant deleted successfully: {}", id);
    }

    @Override
    public boolean isRestaurantExistById(Long id) {
        return restaurantRepository.existsById(id);
    }
}
