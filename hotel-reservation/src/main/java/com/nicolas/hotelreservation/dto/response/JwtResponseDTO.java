package com.nicolas.hotelreservation.dto.response;

public record JwtResponseDTO(String token, long expiresInDays) {
}
