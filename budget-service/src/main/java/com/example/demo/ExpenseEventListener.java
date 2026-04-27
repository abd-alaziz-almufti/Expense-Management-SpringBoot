package com.example.demo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ExpenseEventListener {

    // الاستماع لأي رسالة تصل على هذا الطابور
    @RabbitListener(queues = "expense_queue")
    public void receiveMessage(String message) {
        System.out.println("🔔 Budget Service Received Event: " + message);
        System.out.println("🔄 Background task: Updating budget balance...");
    }
}