package com.nicolas.hotelreservation.dto.response;

import com.nicolas.hotelreservation.enums.RoomType;

import java.math.BigDecimal;

public record RoomResponseDTO(
        Long id,
        Integer roomNumber,
        RoomType roomType,
        BigDecimal pricePerNight,
        Integer maxGuests
) {}
