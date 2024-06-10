package com.scotersharing.controller;

import com.scotersharing.dto.request.AddScooterRequest;
import com.scotersharing.dto.response.ScooterIdResponse;
import com.scotersharing.entity.Booking;
import com.scotersharing.entity.Scooter;
import com.scotersharing.entity.User;
import com.scotersharing.exception.BadRequestException;
import com.scotersharing.exception.ConflictException;
import com.scotersharing.mapper.ScooterMapper;
import com.scotersharing.service.BookingService;
import com.scotersharing.service.ScooterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/scootersharing/scooters")
@RequiredArgsConstructor
public class ScooterController {
    private final ScooterService scooterService;
    private final BookingService bookingService;
    private final ScooterMapper scooterMapper;

    @PostMapping("/add")
    public ResponseEntity<ScooterIdResponse> addScooter(
            @RequestBody @Valid AddScooterRequest request,
            @AuthenticationPrincipal User user
    ) {
        if (scooterService.existsByLicensePlate(request.getLicensePlate())) {
            throw new ConflictException("Scooter with this license plate already exists");
        }

        Scooter scooter = scooterMapper.toScooter(request);
        scooter.setOwner(user);
        scooter.setAvailable(1);
        scooterService.save(scooter);

        return ResponseEntity.ok(new ScooterIdResponse(scooter.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scooter> getScooter(@PathVariable Long id) {
        Optional<Scooter> scooter = scooterService.findById(id);
        if (scooter.isEmpty()) {
            throw new BadRequestException("Scooter not found");
        }
        return ResponseEntity.ok(scooter.get());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Scooter>> getAllScooters(@AuthenticationPrincipal User user) {
        List<Scooter> scooters = scooterService.findAll().stream()
                .filter(scooter -> scooter.getAvailable() == 1)
                .collect(Collectors.toList());
        return ResponseEntity.ok(scooters);
    }

    @PostMapping("/{id}/book")
    public ResponseEntity<Void> bookScooter(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        Optional<Scooter> scooter = scooterService.findById(id);
        if (scooter.isEmpty() || scooter.get().getAvailable() == 0) {
            throw new BadRequestException("Scooter not available or not found");
        }

        Booking booking = new Booking();
        booking.setScooter(scooter.get());
        booking.setUser(user);
        bookingService.save(booking);

        scooter.get().setAvailable(0);
        scooterService.save(scooter.get());

        return ResponseEntity.ok().build();
    }
}
