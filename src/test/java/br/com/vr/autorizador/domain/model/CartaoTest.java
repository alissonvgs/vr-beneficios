package br.com.vr.autorizador.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CartaoTest {

    @Test
    void deveCriarCartaoComSaldoInicialDe500() {
        Cartao cartao = new Cartao("123", "9999");

        assertEquals("123", cartao.getNumeroCartao());
        assertEquals("9999", cartao.getSenha());
        assertEquals(new BigDecimal("500.00"), cartao.getSaldo());
    }

    @Test
    void deveDebitarValorDoSaldo() {
        Cartao cartao = new Cartao("123", "9999");
        cartao.debitar(new BigDecimal("150.00"));

        assertEquals(new BigDecimal("350.00"), cartao.getSaldo());
    }

    @Test
    void deveLancarExcecaoAoTentarDebitarSaldoInsuficiente() {
        Cartao cartao = new Cartao("123", "9999");

        assertThrows(IllegalArgumentException.class, () ->
                cartao.debitar(new BigDecimal("600.00")));
    }
}
