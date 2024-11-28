package com.smd.smartmenu.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private List<Dish> dishes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "restaurant", orphanRemoval = true)
    private List<SocialHandle> socialHandles = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    private User user;

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", addressLine='" + addressLine + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", cuisineType='" + cuisineType + '\'' +
                ", rating=" + rating +
                ", openingTime='" + openingTime + '\'' +
                ", closingTime='" + closingTime + '\'' +
                '}';
    }

}