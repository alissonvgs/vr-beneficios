package br.com.vr.autorizador.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Cartao {

    @Id
    private String numeroCartao;

    private String senha;

    @Column(nullable = false)
    private BigDecimal saldo = new BigDecimal("500.00");

    public Cartao() {}

    public Cartao(String numeroCartao, String senha) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.saldo = new BigDecimal("500.00");
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String getSenha() {
        return senha;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void debitar(BigDecimal valor) {
        if (saldo.compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        this.saldo = saldo.subtract(valor);
    }
}
