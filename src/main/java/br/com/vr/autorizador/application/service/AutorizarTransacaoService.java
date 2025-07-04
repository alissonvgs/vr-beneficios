package br.com.vr.autorizador.application.service;

import br.com.vr.autorizador.domain.exception.CartaoNaoEncontradoException;
import br.com.vr.autorizador.domain.exception.SaldoInsuficienteException;
import br.com.vr.autorizador.domain.exception.SenhaInvalidaException;
import br.com.vr.autorizador.domain.model.Cartao;
import br.com.vr.autorizador.domain.repository.CartaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AutorizarTransacaoService {

    private final CartaoRepository repository;

    public AutorizarTransacaoService(CartaoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void autorizar(String numeroCartao, String senhaCartao, BigDecimal valor) {
        Cartao cartao = repository.findByNumeroCartaoComLock(numeroCartao).orElseThrow(CartaoNaoEncontradoException::new);

        validarSaldo(cartao, valor);
        validarSenha(cartao, senhaCartao);

        cartao.debitar(valor);
        repository.save(cartao);
    }

    private void validarSenha(Cartao cartao, String senhaInformada) {
        Optional.ofNullable(cartao.getSenha()).filter(senha -> senha.equals(senhaInformada)).orElseThrow(SenhaInvalidaException::new);
    }

    private void validarSaldo(Cartao cartao, BigDecimal valor) {
        Optional.of(cartao.getSaldo().compareTo(valor)).filter(comparacao -> comparacao >= 0).orElseThrow(SaldoInsuficienteException::new);
    }

}
