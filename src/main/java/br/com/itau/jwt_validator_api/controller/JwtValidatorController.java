package br.com.itau.jwt_validator_api.controller;

import br.com.itau.jwt_validator_api.dto.JwtRequestDTO;
import br.com.itau.jwt_validator_api.dto.JwtResponseDTO;
import br.com.itau.jwt_validator_api.service.JwtValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/validate")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "JWT Validator", description = "Valida tokens JWT conforme regras de segurança definidas no desafio do Itaú")
public class JwtValidatorController {

    private final JwtValidationService jwtValidationService;

    @Operation(
            summary = "Valida um JWT",
            description = "Recebe um JWT como entrada e retorna se ele é válido conforme regras: estrutura válida, 3 claims exatos (Name, Role, Seed), valores coerentes e segurança semântica."
    )
    @ApiResponse(responseCode = "200", description = "Retorna verdadeiro ou falso conforme validade do JWT")
    @PostMapping
    public ResponseEntity<JwtResponseDTO> validateJwt(@RequestBody @Valid JwtRequestDTO request) {
        log.info("Recebida requisição de validação para token JWT.");
        boolean isValid = jwtValidationService.validate(request.jwt());
        log.info("Resultado da validação: {}", Optional.of(isValid));
        return ResponseEntity.ok(new JwtResponseDTO(isValid));
    }
}
