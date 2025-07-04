package br.com.vr.autorizador.adapter.controller;

import br.com.vr.autorizador.adapter.dto.CartaoRequest;
import br.com.vr.autorizador.adapter.dto.CartaoResponse;
import br.com.vr.autorizador.application.service.ConsultarSaldoService;
import br.com.vr.autorizador.application.service.CriarCartaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final CriarCartaoService criarCartaoService;
    private final ConsultarSaldoService consultarSaldoService;

    public CartaoController(CriarCartaoService criarCartaoService,
                            ConsultarSaldoService consultarSaldoService) {
        this.criarCartaoService = criarCartaoService;
        this.consultarSaldoService = consultarSaldoService;
    }

    @PostMapping
    public ResponseEntity<CartaoResponse> criar(@RequestBody CartaoRequest request) {
        CartaoResponse response = criarCartaoService.criar(request);
        return ResponseEntity.status(201).body(response);
    }
    @GetMapping("/{numeroCartao}")
    public ResponseEntity<String> consultarSaldo(@PathVariable String numeroCartao) {
        BigDecimal saldo = consultarSaldoService.consultar(numeroCartao);
        return ResponseEntity.ok(saldo.toPlainString());
    }

}
