package com.nicolas.hotelreservation.dto.response;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        String phoneNumber
) {
}
