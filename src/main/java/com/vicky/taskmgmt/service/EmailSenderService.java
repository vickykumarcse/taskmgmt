package com.vicky.taskmgmt.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;

@Service
public class EmailSenderService {

    private static final ExecutorService executor = Executors.newFixedThreadPool(100);

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
}
