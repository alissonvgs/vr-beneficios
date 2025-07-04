package br.com.vr.autorizador.adapter.controller;

import br.com.vr.autorizador.adapter.dto.CartaoRequest;
import br.com.vr.autorizador.adapter.dto.CartaoResponse;
import br.com.vr.autorizador.application.service.CriarCartaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final CriarCartaoService service;

    public CartaoController(CriarCartaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CartaoResponse> criar(@RequestBody CartaoRequest request) {
        CartaoResponse response = service.criar(request);
        return ResponseEntity.status(201).body(response);
    }
}
