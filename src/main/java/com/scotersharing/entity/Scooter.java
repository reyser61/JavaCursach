package com.scotersharing.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "scooters")
public class Scooter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vendor;
    private String model;
    private int year;
    private String color;
    private String location;
    private String description;
    private String licensePlate;

    @Column(name = "available")
    private int available;

    @Column(name = "required_driving_experience")
    private int requiredDrivingExperience;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
