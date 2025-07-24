package br.com.itau.jwt_validator_api.service.impl;

import br.com.itau.jwt_validator_api.domain.JwtToken;
import br.com.itau.jwt_validator_api.util.JwtParserUtil;
import br.com.itau.jwt_validator_api.validator.JwtClaimsValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtValidationServiceImplTest {

    private JwtValidationServiceImpl service;

    @BeforeEach
    void setup() {
        service = new JwtValidationServiceImpl();
    }

    @Test
    void deveRetornarTrueParaJwtValido() {
        String jwt = "token.valido.aqui";

        JwtToken mockToken = new JwtToken("Toninho", "Admin", "7841");

        try (
                MockedStatic<JwtParserUtil> parserMock = mockStatic(JwtParserUtil.class);
                MockedStatic<JwtClaimsValidator> validatorMock = mockStatic(JwtClaimsValidator.class)
        ) {
            parserMock.when(() -> JwtParserUtil.parse(jwt)).thenReturn(mockToken);
            validatorMock.when(() -> JwtClaimsValidator.isValid(mockToken)).thenReturn(true);

            boolean result = service.validate(jwt);

            assertTrue(result);
            parserMock.verify(() -> JwtParserUtil.parse(jwt), times(1));
            validatorMock.verify(() -> JwtClaimsValidator.isValid(mockToken), times(1));
        }
    }

    @Test
    void deveRetornarFalseQuandoParserLancaExcecao() {
        String jwt = "token.invalido";

        try (MockedStatic<JwtParserUtil> parserMock = mockStatic(JwtParserUtil.class)) {
            parserMock.when(() -> JwtParserUtil.parse(jwt))
                    .thenThrow(new IllegalArgumentException("JWT inválido"));

            boolean result = service.validate(jwt);

            assertFalse(result);
            parserMock.verify(() -> JwtParserUtil.parse(jwt), times(1));
        }
    }

    @Test
    void deveRetornarFalseQuandoValidacaoRetornaFalse() {
        String jwt = "token.qualquer";
        JwtToken mockToken = new JwtToken("Joana", "External", "123");

        try (
                MockedStatic<JwtParserUtil> parserMock = mockStatic(JwtParserUtil.class);
                MockedStatic<JwtClaimsValidator> validatorMock = mockStatic(JwtClaimsValidator.class)
        ) {
            parserMock.when(() -> JwtParserUtil.parse(jwt)).thenReturn(mockToken);
            validatorMock.when(() -> JwtClaimsValidator.isValid(mockToken)).thenReturn(false);

            boolean result = service.validate(jwt);

            assertFalse(result);
        }
    }
}
