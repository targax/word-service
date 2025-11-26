package com.api_palavras.word_service.repository;

import com.api_palavras.word_service.model.Palavra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PalavraRepository extends JpaRepository<Palavra, Long> {

    boolean existsByTermo(String termo);
}
