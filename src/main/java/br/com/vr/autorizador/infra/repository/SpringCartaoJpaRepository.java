package br.com.vr.autorizador.infra.repository;

import br.com.vr.autorizador.domain.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface SpringCartaoJpaRepository extends JpaRepository<Cartao, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Cartao c WHERE c.numeroCartao = :numeroCartao")
    Optional<Cartao> findByNumeroCartaoComLock(@Param("numeroCartao") String numeroCartao);
}
