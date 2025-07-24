package br.com.itau.jwt_validator_api.util;

import br.com.itau.jwt_validator_api.domain.JwtToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@UtilityClass
@Slf4j
public class JwtParserUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static JwtToken parse(String jwt) {
        log.debug("Iniciando parsing do JWT.");
        String[] parts = jwt.split("\\.");

        if (parts.length != 3) {
            log.warn("JWT inválido: esperado 3 partes, recebido {}", parts.length);
            throw new IllegalArgumentException("JWT malformado: deve conter exatamente 3 partes (header.payload.signature).");
        }

        String payloadBase64 = parts[1];
        String payloadJson = decodeBase64(payloadBase64);
        Map<String, Object> claims = parseJsonToMap(payloadJson);

        return extractTokenFromClaims(claims);
    }

    private static String decodeBase64(String encoded) {
        try {
            byte[] decodedBytes = Base64.getUrlDecoder().decode(encoded);
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            log.error("Erro ao decodificar Base64: {}", e.getMessage());
            throw new IllegalArgumentException("Payload do JWT não é um Base64 válido.");
        }
    }

    private static Map<String, Object> parseJsonToMap(String json) {
        try {
            log.debug("Convertendo JSON em mapa de claims: {}", json);
            return mapper.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            log.error("Erro ao converter JSON do payload: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Payload do JWT não é um JSON válido.");
        }
    }

    private static JwtToken extractTokenFromClaims(Map<String, Object> claims) {
        if (claims == null || claims.size() != 3) {
            log.warn("Claims inválidas: esperadas 3, encontradas {}", claims == null ? 0 : claims.size());
            throw new IllegalArgumentException("JWT inválido: claims obrigatórias ausentes ou quantidade incorreta.");
        }

        String name = getStringClaim(claims, "Name");
        String role = getStringClaim(claims, "Role");
        String seed = getStringClaim(claims, "Seed");

        log.debug("Claims extraídas com sucesso - Name: {}, Role: {}, Seed: {}", name, role, seed);
        return new JwtToken(name, role, seed);
    }

    private static String getStringClaim(Map<String, Object> claims, String key) {
        Object value = claims.get(key);
        if (value == null || !(value instanceof String str) || str.isBlank()) {
            log.warn("Claim '{}' ausente ou inválida: {}", key, value);
            throw new IllegalArgumentException("Claim obrigatória inválida ou ausente: " + key);
        }
        return str;
    }
}
