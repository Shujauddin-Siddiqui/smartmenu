package com.smd.smartmenu.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smd.smartmenu.helper.ResourceNotFoundException;
import com.smd.smartmenu.model.Dish;
import com.smd.smartmenu.model.Restaurant;
import com.smd.smartmenu.repository.DishRepository;
import com.smd.smartmenu.service.DishService;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository dishRepository;

    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());


    @Override
    public Dish saveDish(Dish dish) {
        Dish savedDish = dishRepository.save(dish);
        logger.info("Dish saved successfully: {}", savedDish);
        return savedDish;
    }

    @Override
    public Optional<Dish> getDishById(Long id) {
        return dishRepository.findById(id);
    }

    @Override
    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    @Override
    public List<Dish> getDishesByRestaurant(Restaurant restaurant) {
        return dishRepository.findByRestaurant(restaurant);
    }

    @Override
    public Optional<Dish> updateDish(Dish dish) {
        Dish dishFetched = dishRepository.findById(dish.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Dish Not Found"));

        // Update fields
        dishFetched.setName(dish.getName());
        dishFetched.setDescription(dish.getDescription());
        dishFetched.setImage(dish.getImage());
        dishFetched.setVideo(dish.getVideo());
        dishFetched.setPrice(dish.getPrice());
        dishFetched.setCategory(dish.getCategory());
        dishFetched.setAvailabilityStatus(dish.isAvailabilityStatus());
        dishFetched.setPreparationTime(dish.getPreparationTime());
        dishFetched.setSpiceLevel(dish.getSpiceLevel());

        // Save updated dish to the database
        dishRepository.save(dishFetched);
        logger.info("Dish updated successfully: {}", dishFetched);
        return Optional.of(dishFetched);
    }

    @Override
    public void deleteDish(Dish dish) {
        // Dish dishFetched = dishRepository.findById(id)
        //         .orElseThrow(() -> new ResourceNotFoundException("Dish Not Found"));
        dishRepository.delete(dish);
        logger.info("Dish deleted successfully: {}", dish.getId());
    }

    @Override
    public boolean isDishExistById(Long id) {
        return dishRepository.existsById(id);
    }
}
