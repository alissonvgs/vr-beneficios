package br.com.vr.autorizador.adapter.dto;

import java.math.BigDecimal;

public record TransacaoRequest(
        String numeroCartao,
        String senhaCartao,
        BigDecimal valor
) {}
