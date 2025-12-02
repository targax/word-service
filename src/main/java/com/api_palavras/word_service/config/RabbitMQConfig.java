package com.api_palavras.word_service.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    //<serviço>.<domínio>.<ação>.queue
    public static final String QUEUE_NAME = "word-service.word.excluida.queue";

    //<serviço>.<domínio>.exchange
    public static   final String EXCHANGE_NAME = "word-service.word.exchange";


    //<serviço>.<domínio>.<ação>.routing-key
    public  static  final String ROUTING_KEY = "word-service.word.excluida.rk";


    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(
            final ConnectionFactory connectionFactory,
            final Jackson2JsonMessageConverter converter
            ){


        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        rabbitTemplate.setMessageConverter(converter);

        return rabbitTemplate;


    }


    // Criar Fila
    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME,true);
    }

    // Criar Exchange
    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(EXCHANGE_NAME);
    }

    // criar Binding
    @Bean
    public Binding binding(final Queue queue, final DirectExchange exchange){
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }
}
