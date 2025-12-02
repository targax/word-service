package com.api_palavras.word_service.producer;

import com.api_palavras.word_service.dto.PalavraExcluidaMensage;

public interface IWordExcluidaProducer {

    void notifyEtiquetaExcluida(final PalavraExcluidaMensage mensage);
}
