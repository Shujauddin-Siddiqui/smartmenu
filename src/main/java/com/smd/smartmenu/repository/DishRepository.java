package com.smd.smartmenu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smd.smartmenu.model.Dish;
import com.smd.smartmenu.model.Restaurant;

public interface DishRepository extends JpaRepository<Dish, Long> {

    List<Dish> findByRestaurant(Restaurant restaurant);
}
