package br.com.vr.autorizador.domain.repository;

import br.com.vr.autorizador.domain.model.Cartao;
import java.util.Optional;

public interface CartaoRepository {
    Optional<Cartao> findByNumeroCartao(String numeroCartao);
    Cartao save(Cartao cartao);
}
