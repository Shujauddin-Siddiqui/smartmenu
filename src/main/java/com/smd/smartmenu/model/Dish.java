package com.smd.smartmenu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private int preparationTime; // in minutes
    private String spiceLevel; // could be an enum, e.g., "MILD", "MEDIUM", "SPICY"

    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", video='" + video + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", availabilityStatus=" + availabilityStatus +
                ", preparationTime=" + preparationTime +
                ", spiceLevel='" + spiceLevel + '\'' +
                '}';
    }
}
