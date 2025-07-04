package br.com.vr.autorizador.application.service;

import br.com.vr.autorizador.domain.exception.CartaoNaoEncontradoException;
import br.com.vr.autorizador.domain.exception.SaldoInsuficienteException;
import br.com.vr.autorizador.domain.exception.SenhaInvalidaException;
import br.com.vr.autorizador.domain.model.Cartao;
import br.com.vr.autorizador.domain.repository.CartaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AutorizarTransacaoService {

    private final CartaoRepository repository;

    public AutorizarTransacaoService(CartaoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void autorizar(String numeroCartao, String senhaCartao, BigDecimal valor) {
        Cartao cartao = repository.findByNumeroCartao(numeroCartao)
                .orElseThrow(CartaoNaoEncontradoException::new);

        if (!cartao.getSenha().equals(senhaCartao)) {
            throw new SenhaInvalidaException();
        }

        if (cartao.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException();
        }

        cartao.debitar(valor);
        repository.save(cartao);
    }
}
