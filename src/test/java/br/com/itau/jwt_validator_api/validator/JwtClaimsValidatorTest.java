package br.com.itau.jwt_validator_api.validator;

import br.com.itau.jwt_validator_api.domain.JwtToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtClaimsValidatorTest {

    @Test
    void deveRetornarTrueParaClaimsValidas() {
        JwtToken token = new JwtToken("Toninho Araujo", "Admin", "7841");
        assertTrue(JwtClaimsValidator.isValid(token));
    }

    @Test
    void deveRetornarFalseParaNomeComNumero() {
        JwtToken token = new JwtToken("M4ria", "Admin", "7841");
        assertFalse(JwtClaimsValidator.isValid(token));
    }

    @Test
    void deveRetornarFalseParaNomeNulo() {
        JwtToken token = new JwtToken(null, "Admin", "7841");
        assertFalse(JwtClaimsValidator.isValid(token));
    }

    @Test
    void deveRetornarFalseParaNomeComMaisDe256Caracteres() {
        String longName = "a".repeat(257);
        JwtToken token = new JwtToken(longName, "Admin", "7841");
        assertFalse(JwtClaimsValidator.isValid(token));
    }

    @Test
    void deveRetornarFalseParaRoleInvalida() {
        JwtToken token = new JwtToken("Toninho", "Manager", "7841");
        assertFalse(JwtClaimsValidator.isValid(token));
    }

    @Test
    void deveRetornarFalseParaSeedNaoPrimo() {
        JwtToken token = new JwtToken("Toninho", "Admin", "100");
        assertFalse(JwtClaimsValidator.isValid(token));
    }

    @Test
    void deveRetornarFalseParaSeedNaoNumerica() {
        JwtToken token = new JwtToken("Toninho", "Admin", "abc");
        assertFalse(JwtClaimsValidator.isValid(token));
    }

    @Test
    void deveRetornarFalseParaSeedNula() {
        JwtToken token = new JwtToken("Toninho", "Admin", null);
        assertFalse(JwtClaimsValidator.isValid(token));
    }
}
