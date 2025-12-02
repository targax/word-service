package com.api_palavras.word_service.service;


import com.api_palavras.word_service.dto.PalavraExcluidaMensage;
import com.api_palavras.word_service.model.Palavra;
import com.api_palavras.word_service.producer.IWordExcluidaProducer;
import com.api_palavras.word_service.repository.PalavraRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@Slf4j
public class PalavraService {

    private final IWordExcluidaProducer wordExcluidaProducer;

    private final PalavraRepository repository;

    public PalavraService(IWordExcluidaProducer wordExcluidaProducer, PalavraRepository repository) {
        this.wordExcluidaProducer = wordExcluidaProducer;
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
                .orElseThrow(() -> new RuntimeException("Palavra não encontrada com id: " + id));
    }

    public Palavra atualizar(Long id, Palavra novaPalavra) {
        Palavra existente = buscarPorId(id);
        existente.setTermo(novaPalavra.getTermo());
        return repository.save(existente);
    }

    public void deletar(Long id) {

        Palavra palavra = buscarPorId(id);

        repository.delete(palavra);

        // 3. Monta a mensagem
       PalavraExcluidaMensage mensage = new PalavraExcluidaMensage(palavra.getId(),
                LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant()

        );

        // 4. Dispara evento para o RabbitMQ
        wordExcluidaProducer.notifyEtiquetaExcluida(mensage);
        log.info("🔥 Enviando mensagem para RabbitMQ: {}", mensage);


    }

    public boolean existePorId(Long id) {
        return repository.existsById(id);
    }
}
