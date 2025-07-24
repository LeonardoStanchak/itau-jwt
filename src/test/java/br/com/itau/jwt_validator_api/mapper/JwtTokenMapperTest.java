package br.com.itau.jwt_validator_api.mapper;

import br.com.itau.jwt_validator_api.domain.JwtToken;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenMapperTest {

    @Test
    void deveMapearClaimsParaJwtToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("Name", "Toninho Araujo");
        claims.put("Role", "Admin");
        claims.put("Seed", "7841");

        JwtToken token = JwtTokenMapper.fromClaims(claims);

        assertNotNull(token);
        assertEquals("Toninho Araujo", token.name());
        assertEquals("Admin", token.role());
        assertEquals("7841", token.seed());
    }

    @Test
    void deveRetornarStringsMesmoComTiposDiferentes() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("Name", "Joana");
        claims.put("Role", "Member");
        claims.put("Seed", 1237);

        JwtToken token = JwtTokenMapper.fromClaims(claims);

        assertEquals("1237", token.seed());
    }

    @Test
    void devePermitirValoresNulos() {
        Map<String, Object> claims = new HashMap<>();

        JwtToken token = JwtTokenMapper.fromClaims(claims);

        assertNotNull(token);
        assertEquals("null", token.name());
        assertEquals("null", token.role());
        assertEquals("null", token.seed());
    }
}
