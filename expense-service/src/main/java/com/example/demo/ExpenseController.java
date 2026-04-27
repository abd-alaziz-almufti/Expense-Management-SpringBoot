package com.example.demo;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping
    public String addExpense(@RequestBody ExpenseRequest request) {
        System.out.println("✅ Expense received: " + request.getDescription() + " - $" + request.getAmount());

        // إرسال الحدث (Event) غير متزامن للـ Budget Service عبر RabbitMQ
        String eventMessage = "New Expense Added: $" + request.getAmount() + " for " + request.getDescription();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXPENSE_QUEUE, eventMessage);

        return "Expense saved and event published to RabbitMQ!";
    }
}

// كلاس بسيط لاستقبال البيانات (DTO)
class ExpenseRequest {
    private double amount;
    private String description;

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}