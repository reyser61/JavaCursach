package com.scotersharing.service;

import com.scotersharing.entity.Scooter;
import com.scotersharing.repository.ScooterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScooterService {
    private final ScooterRepository scooterRepository;

    public void save(Scooter scooter) {
        scooterRepository.save(scooter);
    }

    public Optional<Scooter> findById(Long id) {
        return scooterRepository.findById(id);
    }

    public List<Scooter> findAll() {
        return scooterRepository.findAll();
    }

    public boolean existsByLicensePlate(String licensePlate) {
        return scooterRepository.existsByLicensePlate(licensePlate);
    }
}
