package com.smd.smartmenu.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NotBlank(message = "Name field is required")
    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private Long mobile;

    private String profilePhoto;

    private boolean isEnabled;

    private boolean isEmailVerified;

    private boolean isPhoneVerified;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    @Builder.Default
    private List<Restaurant> restaurants = new ArrayList<>();

}
