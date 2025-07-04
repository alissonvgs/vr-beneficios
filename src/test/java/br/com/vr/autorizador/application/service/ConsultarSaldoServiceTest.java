package br.com.vr.autorizador.application.service;

import br.com.vr.autorizador.domain.exception.CartaoNaoEncontradoException;
import br.com.vr.autorizador.domain.model.Cartao;
import br.com.vr.autorizador.domain.repository.CartaoRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultarSaldoServiceTest {

    private final CartaoRepository repository = mock(CartaoRepository.class);
    private final ConsultarSaldoService service = new ConsultarSaldoService(repository);

    @Test
    void deveRetornarSaldoDoCartaoComSucesso() {
        Cartao cartao = new Cartao("123", "9999");
        cartao.debitar(new BigDecimal("50.00")); // saldo agora é 450

        when(repository.findByNumeroCartao("123")).thenReturn(Optional.of(cartao));

        BigDecimal saldo = service.consultar("123");

        assertEquals(new BigDecimal("450.00"), saldo);
    }

    @Test
    void deveLancarExcecaoSeCartaoNaoExistir() {
        when(repository.findByNumeroCartao("999")).thenReturn(Optional.empty());

        assertThrows(CartaoNaoEncontradoException.class, () -> service.consultar("999"));
    }
}
