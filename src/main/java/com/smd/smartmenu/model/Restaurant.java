package com.smd.smartmenu.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String logo;
    
    private String addressLine;
    
    private String city;
    
    private String state;
    
    private String zipCode;
    
    private String country;
    
    private String contactNumber;
    
    private String cuisineType;
    
    private double rating;
    
    private String openingTime;
    
    private String closingTime;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "restaurant", orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<Dish> dishes = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "restaurant", orphanRemoval = true)
    @Builder.Default
    private List<SocialHandle> socialHandles = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    private User user;
}