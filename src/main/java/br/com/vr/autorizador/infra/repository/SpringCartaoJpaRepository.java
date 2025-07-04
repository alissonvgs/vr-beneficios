package br.com.vr.autorizador.infra.repository;

import br.com.vr.autorizador.domain.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

interface SpringCartaoJpaRepository extends JpaRepository<Cartao, String> {}
