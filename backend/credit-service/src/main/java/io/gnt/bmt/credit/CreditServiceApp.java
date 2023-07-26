package io.gnt.bmt.credit;

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
public class CreditServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(CreditServiceApp.class, args);
    }


    static final String topicExchangeName = "exchange";

    static final String queueName = "credit.issue";
    static final String queueNameError = "credit.issue.error";

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
        return BindingBuilder.bind(queue).to(exchange).with("credit.issue.#");
    }

}

