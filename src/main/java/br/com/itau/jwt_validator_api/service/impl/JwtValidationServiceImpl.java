package br.com.itau.jwt_validator_api.service.impl;

import br.com.itau.jwt_validator_api.service.JwtValidationService;
import br.com.itau.jwt_validator_api.util.JwtParserUtil;
import br.com.itau.jwt_validator_api.validator.JwtClaimsValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class JwtValidationServiceImpl implements JwtValidationService {

    @Override
    public boolean validate(String jwt) {
        try {
            log.debug("Iniciando validação do JWT: {}", jwt);
            Map<String, Object> claims = JwtParserUtil.parseJwt(jwt);
            boolean isValid = JwtClaimsValidator.isValid(claims);
            log.debug("Validação das claims concluída. Resultado: {}", isValid);
            return isValid;
        } catch (Exception e) {
            log.warn("Erro ao validar JWT: {}", e.getMessage());
            return false;
        }
    }
}
