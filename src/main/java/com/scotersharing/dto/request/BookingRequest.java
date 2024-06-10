package com.scotersharing.dto.request;

import com.scotersharing.entity.BookingType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingRequest {
    @NotNull
    private Long scooterId;

    @NotNull
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private BookingType bookingType;
}
