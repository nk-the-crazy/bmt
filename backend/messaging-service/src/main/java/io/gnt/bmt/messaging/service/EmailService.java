package io.gnt.bmt.messaging.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void send(String to, String subject, String body);
    void send(String[] to, String subject, String body);

    void sendAsHtml(String to, String subject,String htmlContent) throws MessagingException;
}
