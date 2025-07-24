package br.com.itau.jwt_validator_api.service.impl;

import br.com.itau.jwt_validator_api.domain.JwtToken;
import br.com.itau.jwt_validator_api.service.JwtValidationService;
import br.com.itau.jwt_validator_api.util.JwtParserUtil;
import br.com.itau.jwt_validator_api.validator.JwtClaimsValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtValidationServiceImpl implements JwtValidationService {

    @Override
    public boolean validate(String jwt) {
        log.debug("Validando token JWT...");

        try {
            JwtToken token = JwtParserUtil.parse(jwt);
            boolean isValid = JwtClaimsValidator.isValid(token);

            log.debug("Resultado da validação do token: {}", isValid);
            return isValid;

        } catch (IllegalArgumentException e) {
            log.warn("JWT inválido: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Erro inesperado ao validar JWT", e);
            return false;
        }
    }
}

