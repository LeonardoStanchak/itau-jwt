package br.com.itau.jwt_validator_api.validator;

import br.com.itau.jwt_validator_api.domain.JwtToken;
import br.com.itau.jwt_validator_api.util.PrimeNumberUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class JwtClaimsValidator {

    public static boolean isValid(JwtToken token) {
        log.debug("Iniciando validação de claims: {}", token);

        if (!isValidName(token.name())) {
            log.warn("Nome inválido: '{}'", token.name());
            return false;
        }

        if (!isValidRole(token.role())) {
            log.warn("Role inválida: '{}'", token.role());
            return false;
        }

        if (!isValidSeed(token.seed())) {
            log.warn("Seed inválida: '{}'", token.seed());
            return false;
        }

        log.debug("Claims do JWT validadas com sucesso.");
        return true;
    }

    private boolean isValidName(String name) {
        return name != null && name.length() <= 256 && !name.matches(".*\\d.*");
    }

    private boolean isValidRole(String role) {
        return role != null && switch (role) {
            case "Admin", "Member", "External" -> true;
            default -> false;
        };
    }

    private boolean isValidSeed(String seedStr) {
        if (seedStr == null || !seedStr.matches("\\d+")) {
            return false;
        }

        try {
            int seed = Integer.parseInt(seedStr);
            return PrimeNumberUtil.isPrime(seed);
        } catch (NumberFormatException e) {
            log.error("Seed não pôde ser convertida para inteiro: {}", seedStr);
            return false;
        }
    }
}
