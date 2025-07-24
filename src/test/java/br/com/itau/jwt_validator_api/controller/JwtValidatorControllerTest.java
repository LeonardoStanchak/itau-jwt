package br.com.itau.jwt_validator_api.controller;

import br.com.itau.jwt_validator_api.dto.JwtRequestDTO;
import br.com.itau.jwt_validator_api.dto.JwtResponseDTO;
import br.com.itau.jwt_validator_api.service.JwtValidationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JwtValidatorController.class)
class JwtValidatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtValidationService jwtValidationService;

    private static final String VALID_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05sIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg";
    private static final String INVALID_JWT = "invalid.jwt.token";

    @Test
    @DisplayName("Deve retornar true para JWT válido")
    void deveRetornarTrueParaJwtValido() throws Exception {
        Mockito.when(jwtValidationService.validate(VALID_JWT)).thenReturn(true);

        String requestJson = "{\"jwt\":\"" + VALID_JWT + "\"}";

        mockMvc.perform(post("/api/v1/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid", is(true)));
    }

    @Test
    @DisplayName("Deve retornar false para JWT inválido")
    void deveRetornarFalseParaJwtInvalido() throws Exception {
        Mockito.when(jwtValidationService.validate(INVALID_JWT)).thenReturn(false);

        String requestJson = "{\"jwt\":\"" + INVALID_JWT + "\"}";

        mockMvc.perform(post("/api/v1/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid", is(false)));
    }
}
