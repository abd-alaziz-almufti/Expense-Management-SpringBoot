package com.example.demo.consumer;

import com.example.demo.config.RabbitMQConfig;
import com.example.demo.dto.ExpenseEvent;
import com.example.demo.service.BudgetService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseRequestConsumer {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.REQUEST_QUEUE)
    public void consumeRequest(ExpenseEvent event) {
        System.out.println("🔔 Processing expense request: " + event.getExpenseId() + " Amount: $" + event.getAmount());

        boolean success = budgetService.deductBudget(event.getAmount());

        if (success) {
            event.setStatus("APPROVED");
        } else {
            event.setStatus("REJECTED");
        }

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.RESPONSE_ROUTING_KEY, event);
        System.out.println("📤 Sent response for expense " + event.getExpenseId() + ": " + event.getStatus());
    }
}
