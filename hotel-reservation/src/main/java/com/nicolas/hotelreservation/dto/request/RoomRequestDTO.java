package com.nicolas.hotelreservation.dto.request;

import com.nicolas.hotelreservation.enums.RoomType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RoomRequestDTO(
        @NotNull(message = "O número do quarto é obrigatório.")
        @Size(min = 1, max = 4, message = "O numero precisa ser entre 1 e 9999")
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
