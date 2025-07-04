package br.com.vr.autorizador.application.service;

import br.com.vr.autorizador.domain.exception.CartaoNaoEncontradoException;
import br.com.vr.autorizador.domain.repository.CartaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ConsultarSaldoService {

    private final CartaoRepository repository;

    public ConsultarSaldoService(CartaoRepository repository) {
        this.repository = repository;
    }

    public BigDecimal consultar(String numeroCartao) {
        return repository.findByNumeroCartao(numeroCartao)
                .map(cartao -> cartao.getSaldo())
                .orElseThrow(() -> new CartaoNaoEncontradoException());
    }
}
