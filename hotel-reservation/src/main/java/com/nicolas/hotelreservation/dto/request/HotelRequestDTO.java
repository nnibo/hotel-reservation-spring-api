package com.nicolas.hotelreservation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HotelRequestDTO(
        @NotBlank(message = "O nome do hotel é obrigatório.")
        String name,
        @Size(max = 255, message = "A descrição não pode ter mais que 255 caracteres.")
        String description,
        @NotBlank(message = "O nome da cidade é obrigatório")
        String city,
        @NotBlank(message = "O endereço é obrigatório")
        String address
) {
}
