package br.com.vr.autorizador.adapter.controller;

import br.com.vr.autorizador.adapter.dto.TransacaoRequest;
import br.com.vr.autorizador.application.service.AutorizarTransacaoService;
import br.com.vr.autorizador.domain.exception.CartaoNaoEncontradoException;
import br.com.vr.autorizador.domain.exception.SaldoInsuficienteException;
import br.com.vr.autorizador.domain.exception.SenhaInvalidaException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransacaoController.class)
@Import(TransacaoControllerTest.MockConfig.class)
class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AutorizarTransacaoService autorizarTransacaoService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public AutorizarTransacaoService autorizarTransacaoService() {
            return mock(AutorizarTransacaoService.class);
        }
    }

    @Test
    void deveAutorizarTransacaoComSucesso() throws Exception {
        TransacaoRequest request = new TransacaoRequest("123", "9999", new BigDecimal("10.00"));

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("OK"));

        verify(autorizarTransacaoService).autorizar("123", "9999", new BigDecimal("10.00"));
    }

    @Test
    void deveRetornar422SeSaldoInsuficiente() throws Exception {
        TransacaoRequest request = new TransacaoRequest("123", "9999", new BigDecimal("1000.00"));

        doThrow(new SaldoInsuficienteException()).when(autorizarTransacaoService)
                .autorizar(anyString(), anyString(), any());

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("SALDO_INSUFICIENTE"));
    }

    @Test
    void deveRetornar422SeSenhaInvalida() throws Exception {
        TransacaoRequest request = new TransacaoRequest("123", "0000", new BigDecimal("10.00"));

        doThrow(new SenhaInvalidaException()).when(autorizarTransacaoService)
                .autorizar(anyString(), anyString(), any());

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("SENHA_INVALIDA"));
    }

    @Test
    void deveRetornar422SeCartaoInexistente() throws Exception {
        TransacaoRequest request = new TransacaoRequest("999", "1234", new BigDecimal("10.00"));

        doThrow(new CartaoNaoEncontradoException()).when(autorizarTransacaoService)
                .autorizar(anyString(), anyString(), any());

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("CARTAO_INEXISTENTE"));
    }
}
