package com.scotersharing.dto.response;

import com.scotersharing.entity.BookingType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private Long scooterId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookingType bookingType;
    private double cost;
}
