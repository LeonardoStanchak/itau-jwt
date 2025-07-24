package br.com.itau.jwt_validator_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta da validação do JWT")
public record JwtResponseDTO(

        @Schema(description = "Indica se o JWT é válido ou não", example = "true")
        boolean valid

) {
}
