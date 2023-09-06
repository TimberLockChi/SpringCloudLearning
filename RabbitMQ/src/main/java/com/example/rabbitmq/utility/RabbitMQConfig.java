package com.example.rabbitmq.utility;

import com.example.rabbitmq.entity.Receiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConfig {

    public static String topicExchangeName = "spring-boot-exchange";
    public static String queueName = "message-queue-RQ";

    @Bean
    Queue queue(){
        return new Queue(queueName,false);
    }

    //创建消息交换器，使得队列跟消息发送者和接受者解耦
    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue,TopicExchange topic){
        //将队列与主题绑定，并设置路由键
        //路由键能够将交换机与队列进行绑定，从而确定该进行转发的队列
        //如果没有与其他队列进行绑定，交换机将丢弃接收到的信息
        return BindingBuilder.bind(queue).to(topic).with("foo.bar.#");
    }

    @Bean
    MessageListenerAdapter adapter(Receiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage");//绑定接受者与接收消息处理方法
    }
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter adapter){
        //定义消息接收容器
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(adapter);
        container.setQueueNames(queueName);
        return container;
    }



}
