package com.nicolas.hotelreservation.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservationRequestDTO(
    @NotNull(message = "O id do quarto é obrigatório")
    Long roomId,

    @NotNull(message = "A data de check-in é obrigatória.")
    @FutureOrPresent(message = "O check-in não pode ser uma data no passado.")
    LocalDateTime checkInDate,

    @NotNull(message = "A data de check out é obrigatória")
    @Future(message = "O check out precisa ser uma data no futuro")
    LocalDateTime checkOutDate
) {
}
