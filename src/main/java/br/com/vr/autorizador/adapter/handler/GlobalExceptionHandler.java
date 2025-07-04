package br.com.vr.autorizador.adapter.handler;

import br.com.vr.autorizador.domain.exception.CartaoExistenteException;
import br.com.vr.autorizador.domain.exception.CartaoNaoEncontradoException;
import br.com.vr.autorizador.domain.exception.SaldoInsuficienteException;
import br.com.vr.autorizador.domain.exception.SenhaInvalidaException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartaoExistenteException.class)
    public ResponseEntity<?> handleCartaoExistente(CartaoExistenteException ex) {
        return ResponseEntity.unprocessableEntity().body(ex.getResponse());
    }

//    @ExceptionHandler(CartaoNaoEncontradoException.class)
//    public ResponseEntity<Void> handleCartaoNaoEncontrado() {
//        return ResponseEntity.notFound().build();
//    }

    @ExceptionHandler(CartaoNaoEncontradoException.class)
    public ResponseEntity<String> handleCartaoNaoEncontrado() {
        return ResponseEntity.unprocessableEntity().body("CARTAO_INEXISTENTE");
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<String> handleSenhaInvalida() {
        return ResponseEntity.unprocessableEntity().body("SENHA_INVALIDA");
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<String> handleSaldoInsuficiente() {
        return ResponseEntity.unprocessableEntity().body("SALDO_INSUFICIENTE");
    }


}
