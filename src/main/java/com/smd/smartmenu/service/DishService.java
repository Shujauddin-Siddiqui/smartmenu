package com.smd.smartmenu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smd.smartmenu.model.Dish;
import com.smd.smartmenu.model.Restaurant;
import com.smd.smartmenu.repository.DishRepository;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    public Dish addDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public List<Dish> getDishesByRestaurant(Restaurant restaurant) {
        return dishRepository.findByRestaurant(restaurant);
    }

    public Dish getDishById(Long id) {
        return dishRepository.findById(id).orElse(null);
    }
}
