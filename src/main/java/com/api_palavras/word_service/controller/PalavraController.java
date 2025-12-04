package com.api_palavras.word_service.controller;

import com.api_palavras.word_service.dto.PalavraRequest;
import com.api_palavras.word_service.dto.PalavraResponse;
import com.api_palavras.word_service.model.Palavra;
import com.api_palavras.word_service.service.PalavraService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Criar Palavras")
    @PostMapping
    public ResponseEntity<PalavraResponse> criar(@RequestBody @Valid PalavraRequest request) {

        Palavra palavra = new Palavra();
        palavra.setTermo(request.termo());

        Palavra criada = service.criar(palavra);

        return ResponseEntity.ok(new PalavraResponse(criada.getId(), criada.getTermo()));
    }

    @Operation(summary = "Listar todas as Palavras")
    @GetMapping
    public ResponseEntity<List<PalavraResponse>> listar() {

        List<PalavraResponse> lista = service.listar()
                .stream()
                .map(p -> new PalavraResponse(p.getId(), p.getTermo()))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar Palavra por id")
    @GetMapping("/{id}")
    public ResponseEntity<PalavraResponse> buscarPorId(@PathVariable Long id) {
        Palavra p = service.buscarPorId(id);
        return ResponseEntity.ok(new PalavraResponse(p.getId(), p.getTermo()));
    }

    @Operation(summary = "Atualizar Palavra pelo id")
    @PutMapping("/{id}")
    public ResponseEntity<PalavraResponse> atualizar(@PathVariable Long id,
                                                     @RequestBody @Valid PalavraRequest request) {

        Palavra nova = new Palavra();
        nova.setTermo(request.termo());

        Palavra atualizada = service.atualizar(id, nova);

        return ResponseEntity.ok(new PalavraResponse(atualizada.getId(), atualizada.getTermo()));
    }

    @Operation(summary = "Deletar Palavra pelo id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Verificar existencia da Palavra pelo id")
    @GetMapping("/existe/{id}")
    public ResponseEntity<Void> existePorId(@PathVariable Long id) {
        boolean existe = service.existePorId(id);
        return existe ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}
