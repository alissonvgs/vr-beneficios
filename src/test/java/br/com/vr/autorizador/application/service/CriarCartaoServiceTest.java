package br.com.vr.autorizador.application.service;

import br.com.vr.autorizador.adapter.dto.CartaoRequest;
import br.com.vr.autorizador.domain.exception.CartaoExistenteException;
import br.com.vr.autorizador.domain.model.Cartao;
import br.com.vr.autorizador.domain.repository.CartaoRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CriarCartaoServiceTest {

    private final CartaoRepository repository = mock(CartaoRepository.class);
    private final CriarCartaoService service = new CriarCartaoService(repository);

    @Test
    void deveCriarCartaoComSucesso() {
        CartaoRequest request = new CartaoRequest("123", "9999");

        when(repository.findByNumeroCartao("123")).thenReturn(Optional.empty());
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var response = service.criar(request);

        assertEquals("123", response.numeroCartao());
        assertEquals("9999", response.senha());
    }

    @Test
    void deveLancarExcecaoSeCartaoJaExiste() {
        CartaoRequest request = new CartaoRequest("123", "9999");

        when(repository.findByNumeroCartao("123"))
                .thenReturn(Optional.of(new Cartao("123", "9999")));

        assertThrows(CartaoExistenteException.class, () -> service.criar(request));
    }
}
