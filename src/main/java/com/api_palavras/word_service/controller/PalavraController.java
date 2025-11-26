package com.api_palavras.word_service.controller;

import com.api_palavras.word_service.dto.PalavraRequest;
import com.api_palavras.word_service.dto.PalavraResponse;
import com.api_palavras.word_service.model.Palavra;
import com.api_palavras.word_service.service.PalavraService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/palavras")
public class PalavraController {

    private final PalavraService service;

    public PalavraController(PalavraService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PalavraResponse> criar(@RequestBody @Valid PalavraRequest request) {

        Palavra palavra = new Palavra();
        palavra.setTermo(request.termo());

        Palavra criada = service.criar(palavra);

        return ResponseEntity.ok(new PalavraResponse(criada.getId(), criada.getTermo()));
    }

    @GetMapping
    public ResponseEntity<List<PalavraResponse>> listar() {

        List<PalavraResponse> lista = service.listar()
                .stream()
                .map(p -> new PalavraResponse(p.getId(), p.getTermo()))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PalavraResponse> buscarPorId(@PathVariable Long id) {
        Palavra p = service.buscarPorId(id);
        return ResponseEntity.ok(new PalavraResponse(p.getId(), p.getTermo()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PalavraResponse> atualizar(@PathVariable Long id,
                                                     @RequestBody @Valid PalavraRequest request) {

        Palavra nova = new Palavra();
        nova.setTermo(request.termo());

        Palavra atualizada = service.atualizar(id, nova);

        return ResponseEntity.ok(new PalavraResponse(atualizada.getId(), atualizada.getTermo()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/existe/{id}")
    public ResponseEntity<Void> existePorId(@PathVariable Long id) {
        boolean existe = service.existePorId(id);
        return existe ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}
