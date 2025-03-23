package com.vicky.taskmgmt.dto;

import java.io.Serializable;

public class EmailRequest implements Serializable {
    private String emailId;
    private String subject;
    private String message;

    // Default constructor (needed for RabbitMQ serialization)
    public EmailRequest() {}
    
    public EmailRequest(String emailId, String subject, String message) {
        this.emailId = emailId;
        this.subject = subject;
        this.message = message;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "EmailRequest{" +
                "email='" + emailId + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
