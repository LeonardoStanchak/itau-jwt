package br.com.itau.jwt_validator_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Objeto contendo o JWT a ser validado")
public record JwtRequestDTO(

        @NotBlank(message = "O campo JWT não pode estar vazio")
        @Schema(description = "Token JWT no formato compactado (header.payload.signature)", example = "eyJhbGciOiJIUzI1NiJ9...")
        String jwt

) {
}
