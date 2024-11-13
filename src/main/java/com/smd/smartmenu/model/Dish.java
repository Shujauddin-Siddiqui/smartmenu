package com.smd.smartmenu.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String image;
    private String video;
    private double price;
    private String category;

    private boolean availabilityStatus; // true if available, false if out of stock
    private int preparationTime;        // in minutes
    private String spiceLevel;          // could be an enum, e.g., "MILD", "MEDIUM", "SPICY"

    @ManyToOne
    @JsonIgnore
    @JsonBackReference
    private Restaurant restaurant;
}
