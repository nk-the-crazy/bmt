package io.gnt.bmt.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
public class MessagingServer {
    public static void main(String[] args) {
        SpringApplication.run(MessagingServer.class, args);
    }

    static final String topicExchangeName = "exchange";
    static final String queueName = "messenger.notify";
    static final String queueNameError = "messenger.notify.error";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    Queue queueError() {
        return new Queue(queueNameError, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("messenger.notify.#");
    }

}

