package com.example.demo;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXPENSE_QUEUE = "expense_queue";

    @Bean
    public Queue expenseQueue() {
        return new Queue(EXPENSE_QUEUE, false);
    }
}