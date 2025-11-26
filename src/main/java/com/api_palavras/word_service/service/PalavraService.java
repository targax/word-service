package com.api_palavras.word_service.service;

import com.api_palavras.word_service.model.Palavra;
import com.api_palavras.word_service.repository.PalavraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PalavraService {

    private final PalavraRepository repository;

    public PalavraService(PalavraRepository repository) {
        this.repository = repository;
    }

    public Palavra criar(Palavra palavra) {
        if (repository.existsByTermo(palavra.getTermo())) {
            throw new RuntimeException("Palavra já cadastrada");
        }
        return repository.save(palavra);
    }

    public List<Palavra> listar() {
        return repository.findAll();
    }

    public Palavra buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Palavra não encontrada"));
    }

    public Palavra atualizar(Long id, Palavra novaPalavra) {
        Palavra existente = buscarPorId(id);
        existente.setTermo(novaPalavra.getTermo());
        return repository.save(existente);
    }

    public void deletar(Long id) {
        Palavra palavra = buscarPorId(id);
        repository.delete(palavra);
    }

    public boolean existePorId(Long id) {
        return repository.existsById(id);
    }
}
