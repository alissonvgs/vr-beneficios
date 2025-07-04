package br.com.vr.autorizador.infra.repository;

import br.com.vr.autorizador.domain.model.Cartao;
import br.com.vr.autorizador.domain.repository.CartaoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CartaoRepositoryImp implements CartaoRepository {

    private final SpringCartaoJpaRepository jpaRepository;

    public CartaoRepositoryImp(SpringCartaoJpaRepository jpa) {
        this.jpaRepository = jpa;
    }

    @Override
    public Optional<Cartao> findByNumeroCartao(String numeroCartao) {
        return jpaRepository.findById(numeroCartao);
    }

    @Override
    public Optional<Cartao> findByNumeroCartaoComLock(String numeroCartao) {
        return jpaRepository.findByNumeroCartaoComLock(numeroCartao);
    }

    @Override
    public Cartao save(Cartao cartao) {
        return jpaRepository.save(cartao);
    }

}
