package com.smd.smartmenu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smd.smartmenu.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
