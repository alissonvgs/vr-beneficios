package br.com.vr.autorizador.application.service;

import br.com.vr.autorizador.domain.exception.CartaoExistenteException;
import br.com.vr.autorizador.domain.model.Cartao;
import br.com.vr.autorizador.domain.repository.CartaoRepository;
import br.com.vr.autorizador.adapter.dto.CartaoRequest;
import br.com.vr.autorizador.adapter.dto.CartaoResponse;
import org.springframework.stereotype.Service;

@Service
public class CriarCartaoService {

    private final CartaoRepository repository;

    public CriarCartaoService(CartaoRepository repository) {
        this.repository = repository;
    }

    public CartaoResponse criar(CartaoRequest request) {
        if (repository.findByNumeroCartao(request.numeroCartao()).isPresent()) {
            throw new CartaoExistenteException(request.numeroCartao(), request.senha());
        }

        Cartao novo = new Cartao(request.numeroCartao(), request.senha());
        repository.save(novo);
        return new CartaoResponse(novo.getNumeroCartao(), novo.getSenha());
    }
}
