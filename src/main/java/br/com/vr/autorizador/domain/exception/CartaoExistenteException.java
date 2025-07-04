package br.com.vr.autorizador.domain.exception;

import br.com.vr.autorizador.adapter.dto.CartaoResponse;

public class CartaoExistenteException extends RuntimeException {
    private final CartaoResponse response;

    public CartaoExistenteException(String numero, String senha) {
        this.response = new CartaoResponse(numero, senha);
    }

    public CartaoResponse getResponse() {
        return response;
    }
}
