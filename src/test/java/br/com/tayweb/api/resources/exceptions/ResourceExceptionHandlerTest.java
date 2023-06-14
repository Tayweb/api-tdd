package br.com.tayweb.api.resources.exceptions;

import br.com.tayweb.api.service.exceptions.DataIntegratyViolationException;
import br.com.tayweb.api.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void quandoObjetoNaoForEncontradoReturnResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler.objectNotFound(
                new ObjectNotFoundException("Objeto não encontrado"),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("Objeto não encontrado", response.getBody().getError());
        assertNotEquals("/usuario/2", response.getBody().getPath());
    }

    @Test
    void dataIntegratyViolationException() {
        ResponseEntity<StandardError> response = exceptionHandler.dataIntegratyViolationException(
                new DataIntegratyViolationException("Email já cadastrado"),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getStatusCode().value());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("Email já cadastrado", response.getBody().getError());
    }
}