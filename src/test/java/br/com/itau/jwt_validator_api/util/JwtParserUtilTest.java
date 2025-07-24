package br.com.itau.jwt_validator_api.util;

import br.com.itau.jwt_validator_api.domain.JwtToken;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class JwtParserUtilTest {

    @Test
    void deveParsearJwtValidoCorretamente() {

        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJOYW1lIjoiVG9uaW5obyIsIlJvbGUiOiJBZG1pbiIsIlNlZWQiOiI3ODQxIn0.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        JwtToken token = JwtParserUtil.parse(jwt);

        assertNotNull(token);
        assertEquals("Toninho", token.name());
        assertEquals("Admin", token.role());
        assertEquals("7841", token.seed());
    }


    @Test
    void deveLancarExcecaoParaJsonInvalido() {
        String jsonQuebrado = Base64.getUrlEncoder().encodeToString("não é um json".getBytes());
        String jwt = "eyJhbGciOiJIUzI1NiJ9." + jsonQuebrado + ".signature";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            JwtParserUtil.parse(jwt);
        });

        assertEquals("Payload do JWT não é um JSON válido.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoClaimsNaoPossuem3Campos() {
        String json = "{\"Name\":\"Leo\",\"Role\":\"Admin\"}"; // falta "Seed"
        String encoded = Base64.getUrlEncoder().encodeToString(json.getBytes());
        String jwt = "eyJhbGciOiJIUzI1NiJ9." + encoded + ".signature";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            JwtParserUtil.parse(jwt);
        });

        assertEquals("JWT inválido: claims obrigatórias ausentes ou quantidade incorreta.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoAlgumaClaimEhNulaOuVazia() {
        String json = "{\"Name\":\" \",\"Role\":\"Admin\",\"Seed\":\"7841\"}";
        String encoded = Base64.getUrlEncoder().encodeToString(json.getBytes());
        String jwt = "eyJhbGciOiJIUzI1NiJ9." + encoded + ".signature";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            JwtParserUtil.parse(jwt);
        });

        assertEquals("Claim obrigatória inválida ou ausente: Name", exception.getMessage());
    }
}
