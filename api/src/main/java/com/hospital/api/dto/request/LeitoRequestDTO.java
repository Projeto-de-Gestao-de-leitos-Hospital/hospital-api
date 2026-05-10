package com.hospital.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LeitoRequestDTO(
        @NotBlank String numero,
        @NotNull Boolean ocupado,
        @NotNull Boolean ativo
) {
}