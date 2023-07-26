package io.gnt.bmt.messaging.service.impl;

import io.gnt.bmt.messaging.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender mailSender;
    private final  String sender;

    public EmailServiceImpl(JavaMailSender mailSender, @Value("${spring.mail.username}") String sender) {
        this.mailSender = mailSender;
        this.sender = sender;
    }

    public void send(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.sender);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        log.info("Email sent Successfully to : {}", to);

    }

    public void send(String[] to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.sender);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        log.info("Email sent Successfully to : {}", to);
    }


    public void sendAsHtml(String to, String subject,String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);

        message.setContent(htmlContent, "text/html; charset=utf-8");

        mailSender.send(message);
        log.info("Email sent Successfully to : {}", to);

    }

}



