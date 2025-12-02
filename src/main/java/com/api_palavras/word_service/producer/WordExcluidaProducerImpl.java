package com.api_palavras.word_service.producer;

import com.api_palavras.word_service.dto.PalavraExcluidaMensage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WordExcluidaProducerImpl   implements IWordExcluidaProducer{
    //<serviço>.<domínio>.exchange
    public static   final String EXCHANGE_NAME = "word-service.word.exchange";


    //<serviço>.<domínio>.<ação>.routing-key
    public  static  final String ROUTING_KEY = "word-service.word.excluida.rk";

    private final RabbitTemplate rabbitTemplate;

    public WordExcluidaProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void notifyEtiquetaExcluida(PalavraExcluidaMensage mensage) {
        log.info("Enviando mesagem de etiqueta excluida:{}",mensage);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,ROUTING_KEY,mensage);

    }
}
