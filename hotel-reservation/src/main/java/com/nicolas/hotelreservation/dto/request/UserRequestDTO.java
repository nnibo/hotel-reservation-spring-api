package com.nicolas.hotelreservation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "O nome é obrigatório.")
        String name,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "Formato de e-mail inválido.")
        String email,

        @NotBlank(message = "O número de telefone é obrigatório.")
        @Size(max = 16, message = "O telefone não pode ter mais de 16 caracteres.")
        String phoneNumber
) {
}
