package com.api_palavras.word_service.producer;

import com.api_palavras.word_service.dto.EtiquetaExcluidaMensage;

public interface IWordExcluidaProducer {

    void notifyEtiquetaExcluida(final EtiquetaExcluidaMensage mensage);
}
