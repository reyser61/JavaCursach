package com.scotersharing.controller;

import com.scotersharing.dto.request.BookingRequest;
import com.scotersharing.entity.Booking;
import com.scotersharing.entity.BookingType;
import com.scotersharing.entity.Scooter;
import com.scotersharing.entity.User;
import com.scotersharing.exception.BadRequestException;
import com.scotersharing.service.BookingService;
import com.scotersharing.service.ScooterService;
import com.scotersharing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/scootersharing/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final ScooterService scooterService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @PostMapping("/add")
    public ResponseEntity<Void> addBooking(@RequestBody BookingRequest request) {
        String username = getCurrentUsername();
        User user = userService.findUserByEmail(username);

        logger.info("Booking request by user: {}", user.getEmail());

        Optional<Scooter> scooter = scooterService.findById(request.getScooterId());
        if (scooter.isEmpty() || scooter.get().getAvailable() == 0) {
            throw new BadRequestException("Scooter is not available or not found");
        }

        Booking booking = new Booking();
        booking.setScooter(scooter.get());
        booking.setUser(user);
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setBookingType(request.getBookingType());
        booking.setActive(true);

        if (request.getBookingType() == BookingType.FIXED_TIME) {
            Duration duration = Duration.between(request.getStartTime(), request.getEndTime());
            double cost = duration.toMinutes() * 1.0;
            booking.setCost(cost);
        }

        bookingService.save(booking);

        scooter.get().setAvailable(0);
        scooterService.save(scooter.get());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/complete/{bookingId}")
    public ResponseEntity<Void> completeBooking(@PathVariable Long bookingId) {
        String username = getCurrentUsername();
        User user = userService.findUserByEmail(username);

        Booking booking = bookingService.findById(bookingId)
                .orElseThrow(() -> new BadRequestException("Booking not found"));

        if (!booking.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("User is not authorized to complete this booking");
        }

        booking.setEndTime(LocalDateTime.now());
        booking.setActive(false);
        bookingService.save(booking);

        Scooter scooter = booking.getScooter();
        scooter.setAvailable(1);
        scooterService.save(scooter);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/user")
    public ResponseEntity<List<Booking>> getUserBookings() {
        String username = getCurrentUsername();
        User user = userService.findUserByEmail(username);

        logger.info("Fetching bookings for user: {}", user.getEmail());

        List<Booking> bookings = bookingService.findByUser(user.getId());
        return ResponseEntity.ok(bookings);
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
