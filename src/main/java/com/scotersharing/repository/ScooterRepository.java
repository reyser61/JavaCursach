package com.scotersharing.repository;

import com.scotersharing.entity.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScooterRepository extends JpaRepository<Scooter, Long> {
    boolean existsByLicensePlate(String licensePlate);
}
