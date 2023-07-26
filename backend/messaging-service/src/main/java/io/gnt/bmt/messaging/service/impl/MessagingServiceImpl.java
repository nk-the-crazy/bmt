package io.gnt.bmt.messaging.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.gnt.bmt.messaging.service.EmailService;
import io.gnt.bmt.messaging.service.MessagingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MessagingServiceImpl implements MessagingService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    private final EmailService emailService;

    public MessagingServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    public void  sendEmail(String to, String subject, String message){
        try {
            //emailService.send(to, subject, message);
            log.info("Successfully sent email !!!");

        } catch(Exception e){
            rabbitTemplate.convertAndSend("exchange", "messenger.notify.error", message);
            log.error("Error sending email:", e);
        }
    }

    @RabbitListener(queues = "messenger.notify")
    public void notify(String payload) throws JsonProcessingException {
        this.sendEmail("","", payload);
    }


}



