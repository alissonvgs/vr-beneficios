package br.com.vr.autorizador.adapter.controller;

import br.com.vr.autorizador.adapter.dto.TransacaoRequest;
import br.com.vr.autorizador.application.service.AutorizarTransacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final AutorizarTransacaoService service;

    public TransacaoController(AutorizarTransacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> autorizar(@RequestBody TransacaoRequest request) {
        service.autorizar(
                request.numeroCartao(),
                request.senhaCartao(),
                request.valor()
        );
        return ResponseEntity.status(201).body("OK");
    }
}
