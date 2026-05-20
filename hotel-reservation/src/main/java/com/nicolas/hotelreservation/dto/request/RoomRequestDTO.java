package com.nicolas.hotelreservation.dto.request;

import com.nicolas.hotelreservation.enums.RoomType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record RoomRequestDTO(
        @NotNull(message = "O número do quarto é obrigatório")
        @Min(value = 1, message = "O número do quarto deve ser no mínimo 1")
        @Max(value = 9999, message = "O número do quarto deve ser no máximo 9999")
        Integer roomNumber,

        @NotNull(message = "O tipo do quarto é obrigatório.")
        RoomType roomType,

        @NotNull(message = "O preço da diária é obrigatório.")
        @Positive(message = "O preço deve ser um valor positivo.")
        BigDecimal pricePerNight,

        @NotNull(message = "A capacidade máxima de hóspedes é obrigatória.")
        @Min(value = 1, message = "O quarto deve acomodar pelo menos 1 pessoa.")
        Integer maxGuests
) {
}
