package br.com.itau.jwt_validator_api.exception.handler;

import br.com.itau.jwt_validator_api.exception.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/validate");
    }

    @Test
    void deveRetornarBadRequestParaIllegalArgumentException() {
        var response = handler.handleIllegalArgument(new IllegalArgumentException("Erro teste"), request);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        ApiErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("Requisição inválida", body.error());
        assertEquals("Erro teste", body.message());
        assertEquals("/api/v1/validate", body.path());
    }

    @Test
    void deveRetornarBadRequestParaMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldError()).thenReturn(new FieldError("obj", "jwt", "JWT obrigatório"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        var response = handler.handleValidationException(ex, request);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        ApiErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("Campo inválido", body.error());
        assertEquals("JWT obrigatório", body.message());
        assertEquals("/api/v1/validate", body.path());
    }

    @Test
    void deveRetornarInternalServerErrorParaExceptionGenerica() {
        var response = handler.handleGenericException(new RuntimeException("Falha interna"), request);

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        ApiErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("Erro interno", body.error());
        assertEquals("Algo deu errado ao processar a requisição. Por favor, tente novamente mais tarde.", body.message());
        assertEquals("/api/v1/validate", body.path());
    }
}
