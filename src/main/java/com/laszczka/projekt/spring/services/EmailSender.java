package com.laszczka.projekt.spring.services;

public interface EmailSender {
    void sendEmail(String to, String subject, String content);
}
