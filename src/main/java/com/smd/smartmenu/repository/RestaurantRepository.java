package com.smd.smartmenu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smd.smartmenu.model.Restaurant;
import com.smd.smartmenu.model.User;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByUser(User user);
}
