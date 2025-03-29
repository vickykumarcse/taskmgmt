package com.vicky.taskmgmt.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.vicky.taskmgmt.config.RabbitMqConfig;

@Service
public class EmailSenderService {
    private static final int THREAD_COUNT = Integer.parseInt(RabbitMqConfig.PREFETCH_COUNT);
    private static final ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

    public void sendEmail(String emailId, String subject, String message, long deliveryTag, Channel channel) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000);
                // ✅ Acknowledge the message after successful email processing
                channel.basicAck(deliveryTag, false);
                System.out.println("Email sent to: " + emailId + " | Subject: " + subject);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                try {
                    //❌ If email sending fails, reject and requeue the message
                    channel.basicNack(deliveryTag, false, true);
                    System.out.println("Message requeued: " + emailId);
                } catch (Exception nackEx) {
                    nackEx.printStackTrace();
                }
            }
        }, executor);
    }

    // ✅ Graceful Shutdown Method (Only Call on Application Shutdown)
    public void shutdownExecutor() {
        executor.shutdown(); // Stop accepting new tasks
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) { // Wait for tasks to complete
                executor.shutdownNow(); // Forcefully terminate remaining tasks
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
