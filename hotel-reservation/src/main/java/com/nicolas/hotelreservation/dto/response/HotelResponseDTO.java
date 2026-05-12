package com.nicolas.hotelreservation.dto.response;

public record HotelResponseDTO(
        Long id,
        String name,
        String description,
        String city,
        String address
) {}