package com.example.demo.consumer;

import com.example.demo.config.RabbitMQConfig;
import com.example.demo.dto.ExpenseEvent;
import com.example.demo.model.Expense;
import com.example.demo.service.ExpenseService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseResponseConsumer {

    @Autowired
    private ExpenseService expenseService;

    @RabbitListener(queues = RabbitMQConfig.RESPONSE_QUEUE)
    public void consumeResponse(ExpenseEvent event) {
        System.out.println("🔔 Received response for expense: " + event.getExpenseId() + " Status: " + event.getStatus());
        
        Expense.Status status = Expense.Status.valueOf(event.getStatus());
        expenseService.updateExpenseStatus(event.getExpenseId(), status);
    }
}
