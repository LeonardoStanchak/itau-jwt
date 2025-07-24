package br.com.itau.jwt_validator_api.validator;

import br.com.itau.jwt_validator_api.util.PrimeNumberUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@UtilityClass
@Slf4j
public class JwtClaimsValidator {

    public static boolean isValid(Map<String, Object> claims) {
        log.debug("Iniciando validação de claims: {}", claims);

        if (claims.size() != 3) {
            log.warn("JWT inválido: número incorreto de claims. Esperado 3, encontrado {}", claims.size());
            return false;
        }

        if (!claims.containsKey("Name") || !claims.containsKey("Role") || !claims.containsKey("Seed")) {
            log.warn("JWT inválido: claims obrigatórios ausentes.");
            return false;
        }

        Object nameObj = claims.get("Name");
        if (!(nameObj instanceof String name) || name.length() > 256 || name.matches(".*\\d.*")) {
            log.warn("Claim 'Name' inválida: {}", nameObj);
            return false;
        }

        Object roleObj = claims.get("Role");
        if (!(roleObj instanceof String role) ||
                !(role.equals("Admin") || role.equals("Member") || role.equals("External"))) {
            log.warn("Claim 'Role' inválida: {}", roleObj);
            return false;
        }

        Object seedObj = claims.get("Seed");
        String seedStr = seedObj.toString();
        if (!seedStr.matches("\\d+")) {
            log.warn("Claim 'Seed' não numérica: {}", seedStr);
            return false;
        }

        int seed = Integer.parseInt(seedStr);
        if (!PrimeNumberUtil.isPrime(seed)) {
            log.warn("Claim 'Seed' não é número primo: {}", seed);
            return false;
        }

        log.debug("Claims válidas.");
        return true;
    }
}
