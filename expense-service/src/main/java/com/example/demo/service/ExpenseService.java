package com.example.demo.service;

import com.example.demo.config.RabbitMQConfig;
import com.example.demo.dto.ExpenseEvent;
import com.example.demo.model.Expense;
import com.example.demo.repository.ExpenseRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    public Expense createExpense(Expense expense) {
        expense.setStatus(Expense.Status.PENDING);
        Expense savedExpense = expenseRepository.save(expense);

        // Send event to RabbitMQ
        ExpenseEvent event = new ExpenseEvent(
                savedExpense.getId(),
                savedExpense.getAmount(),
                savedExpense.getDescription(),
                savedExpense.getStatus().name()
        );

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.REQUEST_ROUTING_KEY, event);

        return savedExpense;
    }

    @Transactional
    public void updateExpenseStatus(Long id, Expense.Status status) {
        expenseRepository.findById(id).ifPresent(expense -> {
            expense.setStatus(status);
            expenseRepository.save(expense);
            System.out.println("✅ Expense " + id + " updated to " + status);
        });
    }
}
