package br.com.vr.autorizador.adapter.handler;

import br.com.vr.autorizador.domain.exception.CartaoExistenteException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartaoExistenteException.class)
    public ResponseEntity<?> handleCartaoExistente(CartaoExistenteException ex) {
        return ResponseEntity.unprocessableEntity().body(ex.getResponse());
    }
}
