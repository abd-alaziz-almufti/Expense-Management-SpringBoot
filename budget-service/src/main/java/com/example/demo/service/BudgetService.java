package com.example.demo.service;

import com.example.demo.model.Budget;
import com.example.demo.repository.BudgetRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @PostConstruct
    public void init() {
        if (budgetRepository.count() == 0) {
            Budget budget = new Budget();
            budget.setBalance(1000.0);
            budgetRepository.save(budget);
            System.out.println("🌱 Initial budget created: $1000");
        }
    }

    @Transactional
    public boolean deductBudget(Double amount) {
        Budget budget = budgetRepository.findAll().stream().findFirst().orElseThrow();

        if (budget.getBalance() >= amount) {
            budget.setBalance(budget.getBalance() - amount);
            budgetRepository.save(budget);
            System.out.println("💰 Budget deducted: $" + amount + ". Remaining Balance: $" + budget.getBalance());
            return true;
        } else {
            System.out.println("❌ Insufficient budget: Need $" + amount + ", but only have $" + budget.getBalance());
            return false;
        }
    }
}
