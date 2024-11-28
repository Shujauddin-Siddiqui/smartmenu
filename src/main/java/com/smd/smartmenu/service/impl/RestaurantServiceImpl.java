package com.smd.smartmenu.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smd.smartmenu.helper.ResourceNotFoundException;
import com.smd.smartmenu.model.Restaurant;
import com.smd.smartmenu.model.User;
import com.smd.smartmenu.repository.RestaurantRepository;
import com.smd.smartmenu.repository.UserRepository;
import com.smd.smartmenu.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        //logger.info("Restaurant saved successfully: {}", savedRestaurant);
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

    //Note: not using this function logic is written in handler only we will refactor it later
    @Override
    public Optional<Restaurant> updateRestaurant(Restaurant restaurant) {
        Restaurant restaurantFetched = restaurantRepository.findById(restaurant.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Found"));

        // Fetch the user associated with this restaurant
        User user = restaurantFetched.getUser();
        if (user == null) {
            throw new ResourceNotFoundException("User associated with the restaurant not found");
        }

        // Update fields of the fetched restaurant
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

        // // Update associated dishes and social handles
        // restaurantFetched.setDishes(restaurant.getDishes());
        // restaurantFetched.setSocialHandles(restaurant.getSocialHandles());

        // Update the restaurant in the user's list
        List<Restaurant> userRestaurants = user.getRestaurants();
        int index = -1;

        // Find the index of the restaurant to be updated
        for (int i = 0; i < userRestaurants.size(); i++) {
            if (userRestaurants.get(i).getId().equals(restaurant.getId())) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            userRestaurants.set(index, restaurantFetched); // Update the restaurant in the user's list
        } else {
            throw new ResourceNotFoundException("Restaurant not found in user's list");
        }

        user.setRestaurants(userRestaurants); // Ensure the updated list is set to the user

        // Save the updated user, which will cascade and save the restaurant
        userRepository.save(user);

        logger.info("Restaurant updated successfully: {}", restaurantFetched);
        return Optional.of(restaurantFetched);
    }


    @Override
    public void deleteRestaurantById(Long id) {
        if (!restaurantRepository.existsById(id)) {
            logger.warn("Attempted to delete a non-existent restaurant with ID: {}", id);
            throw new ResourceNotFoundException("Restaurant Not Found");
        }
        Restaurant restaurantFetched = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Found"));
        restaurantRepository.delete(restaurantFetched);
        logger.info("Restaurant deleted successfully: {}", id);
    }

    @Override
    public void deleteRestaurant(Restaurant restaurant) {
        // if (!restaurantRepository.existsById(id)) {
        //     logger.warn("Attempted to delete a non-existent restaurant with ID: {}", id);
        //     throw new ResourceNotFoundException("Restaurant Not Found");
        // }
        // Restaurant restaurantFetched = restaurantRepository.findById(id)
        //         .orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Found"));
        restaurantRepository.delete(restaurant);
        logger.info("Restaurant deleted successfully: {}", restaurant.getId());
    }

    @Override
    public boolean isRestaurantExistById(Long id) {
        return restaurantRepository.existsById(id);
    }
    
}
