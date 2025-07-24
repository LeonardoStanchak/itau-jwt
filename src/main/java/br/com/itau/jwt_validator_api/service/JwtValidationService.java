package br.com.itau.jwt_validator_api.service;

import jakarta.validation.constraints.NotBlank;

@FunctionalInterface
public interface JwtValidationService {
    boolean validate(@NotBlank(message = "O campo JWT não pode estar vazio") String jwt);
}
