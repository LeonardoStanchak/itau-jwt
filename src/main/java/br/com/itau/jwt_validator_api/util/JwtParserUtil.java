package br.com.itau.jwt_validator_api.util;

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

    public static Map<String, Object> parseJwt(String jwt) {
        String[] parts = jwt.split("\\.");
        if (parts.length != 3) {
            log.error("JWT inválido - não possui 3 partes.");
            throw new IllegalArgumentException("JWT deve conter exatamente 3 partes: header.payload.signature");
        }

        try {
            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            log.debug("Payload decodificado com sucesso: {}", payloadJson);
            return mapper.readValue(payloadJson, new TypeReference<>() {});
        } catch (Exception e) {
            log.error("Erro ao decodificar payload do JWT: {}", e.getMessage());
            throw new IllegalArgumentException("Falha ao interpretar o payload do JWT: " + e.getMessage(), e);
        }
    }
}

