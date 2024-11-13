package com.smd.smartmenu.service;

import java.util.List;
import java.util.Optional;

import com.smd.smartmenu.model.Dish;
import com.smd.smartmenu.model.Restaurant;

public interface DishService {

    Dish saveDish(Dish dish);

    Optional<Dish> getDishById(Long id);

    List<Dish> getAllDishes();

    List<Dish> getDishesByRestaurant(Restaurant restaurant);

    Optional<Dish> updateDish(Dish dish);

    void deleteDishById(Long id);

    boolean isDishExistById(Long id);
}
