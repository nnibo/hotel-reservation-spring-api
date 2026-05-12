package com.nicolas.hotelreservation.dto.response;

import com.nicolas.hotelreservation.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservationResponseDTO(
        Long id,
        LocalDateTime checkInDate,
        LocalDateTime checkOutDate,
        BigDecimal totalPrice,
        ReservationStatus status,

        Long userId,
        String userName,

        Long roomId,
        Integer roomNumber,
        String hotelName
) {
}
