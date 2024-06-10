package com.scotersharing.mapper;

import com.scotersharing.dto.request.AddScooterRequest;
import com.scotersharing.entity.Scooter;

public class ScooterMapper {
    public Scooter toScooter(AddScooterRequest request) {
        return Scooter.builder()
                .vendor(request.getVendor())
                .model(request.getModel())
                .year(request.getYear())
                .color(request.getColor())
                .location(request.getLocation())
                .description(request.getDescription())
                .licensePlate(request.getLicensePlate())
                .build();
    }
}
