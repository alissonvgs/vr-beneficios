package br.com.vr.autorizador.application.service;

import br.com.vr.autorizador.domain.exception.CartaoNaoEncontradoException;
import br.com.vr.autorizador.domain.exception.SaldoInsuficienteException;
import br.com.vr.autorizador.domain.exception.SenhaInvalidaException;
import br.com.vr.autorizador.domain.model.Cartao;
import br.com.vr.autorizador.domain.repository.CartaoRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutorizarTransacaoServiceTest {

    private final CartaoRepository repository = mock(CartaoRepository.class);
    private final AutorizarTransacaoService service = new AutorizarTransacaoService(repository);

    @Test
    void deveAutorizarTransacaoComSucesso() {
        Cartao cartao = new Cartao("123", "9999");

        when(repository.findByNumeroCartaoComLock("123")).thenReturn(Optional.of(cartao));

        service.autorizar("123", "9999", new BigDecimal("100.00"));

        assertEquals(new BigDecimal("400.00"), cartao.getSaldo()); // saldo original era 500
        verify(repository).save(cartao);
    }

    @Test
    void deveLancarExcecaoSeCartaoNaoExistir() {
        when(repository.findByNumeroCartaoComLock("123")).thenReturn(Optional.empty());

        assertThrows(CartaoNaoEncontradoException.class,
                () -> service.autorizar("123", "9999", new BigDecimal("100.00")));
    }

    @Test
    void deveLancarExcecaoSeSenhaIncorreta() {
        Cartao cartao = new Cartao("123", "1234");
        when(repository.findByNumeroCartaoComLock("123")).thenReturn(Optional.of(cartao));

        assertThrows(SenhaInvalidaException.class,
                () -> service.autorizar("123", "9999", new BigDecimal("100.00")));
    }

    @Test
    void deveLancarExcecaoSeSaldoInsuficiente() {
        Cartao cartao = new Cartao("123", "9999");
        when(repository.findByNumeroCartaoComLock("123")).thenReturn(Optional.of(cartao));

        assertThrows(SaldoInsuficienteException.class,
                () -> service.autorizar("123", "9999", new BigDecimal("1000.00")));
    }
}
