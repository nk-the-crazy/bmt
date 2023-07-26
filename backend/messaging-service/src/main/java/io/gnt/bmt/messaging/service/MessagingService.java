package io.gnt.bmt.messaging.service;

public interface MessagingService {

    void  sendEmail(String to, String subject, String message);

}
