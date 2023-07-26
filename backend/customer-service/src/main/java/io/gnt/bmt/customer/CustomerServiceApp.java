package io.gnt.bmt.customer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
public class CustomerServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApp.class, args);
    }

    static final String topicExchangeName = "exchange";

    static final String queueName = "customer.update";
    static final String queueNameError = "customer.update.error";

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
        return BindingBuilder.bind(queue).to(exchange).with("customer.#");
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

