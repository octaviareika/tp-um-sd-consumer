package com.example.spring_consumer.amqp.implementation;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.spring_consumer.amqp.AmqpConsumer;
import com.example.spring_consumer.dto.Message;
import com.example.spring_consumer.service.ConsumerService;

@Component
public class RabbitConsumer implements AmqpConsumer<Message> {

    @Autowired 
    private ConsumerService consumerService;

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.request.routing-key.producer}")
    public void consumer(Message message) {

        try {
            System.out.println("Consuming message: " + message.getText());

            consumerService.action(message);

        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }
        
    }
    
}
