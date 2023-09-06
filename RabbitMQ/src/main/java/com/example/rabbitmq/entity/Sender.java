package com.example.rabbitmq.entity;

import com.example.rabbitmq.utility.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Sender {

    private RabbitTemplate rabbitTemplate;

    private int count = 0;
    public Sender(RabbitTemplate rabbitTemplate, Receiver receiver) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void sendMessage(){
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName,"foo.bar.baz",String.valueOf(count++));
    }

}
