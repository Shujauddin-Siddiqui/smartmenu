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
public class SocialHandle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String platform; // e.g., "Facebook", "Instagram"

    private String link; // URL to the social profile

    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;

    @Override
    public String toString() {
        return "SocialHandle{" +
                "id=" + id +
                ", platform='" + platform + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

}
