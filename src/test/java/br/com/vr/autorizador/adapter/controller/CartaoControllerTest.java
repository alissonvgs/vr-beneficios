package br.com.vr.autorizador.adapter.controller;

import br.com.vr.autorizador.adapter.dto.CartaoRequest;
import br.com.vr.autorizador.adapter.dto.CartaoResponse;
import br.com.vr.autorizador.application.service.ConsultarSaldoService;
import br.com.vr.autorizador.application.service.CriarCartaoService;
import br.com.vr.autorizador.domain.exception.CartaoExistenteException;
import br.com.vr.autorizador.domain.exception.CartaoNaoEncontradoException;
import br.com.vr.autorizador.domain.model.Cartao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartaoController.class)
@Import(CartaoControllerTest.MockConfig.class)
class CartaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CriarCartaoService criarCartaoService;

    @Autowired
    private ConsultarSaldoService consultarSaldoService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public CriarCartaoService criarCartaoService() {
            return mock(CriarCartaoService.class);
        }

        @Bean
        public ConsultarSaldoService consultarSaldoService() {
            return mock(ConsultarSaldoService.class);
        }
    }

    @Test
    void deveRetornar422SeCartaoJaExistir() throws Exception {
        Cartao cartao = new Cartao("1234566789", "1234");
        when(criarCartaoService.criar(any())).thenThrow(new CartaoExistenteException(cartao.getNumeroCartao(), cartao.getSenha()));

        mockMvc.perform(post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CartaoRequest("123", "9999"))))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deveRetornarSaldoComSucesso() throws Exception {
        when(consultarSaldoService.consultar("123")).thenReturn(new BigDecimal("450.00"));

        mockMvc.perform(get("/cartoes/123"))
                .andExpect(status().isOk())
                .andExpect(content().string("450.00"));
    }

    @Test
    void deveRetornar422SeCartaoNaoExistir() throws Exception {
        when(consultarSaldoService.consultar("123")).thenThrow(new CartaoNaoEncontradoException());

        mockMvc.perform(get("/cartoes/123"))
                .andExpect(status().isUnprocessableEntity());
    }
}
