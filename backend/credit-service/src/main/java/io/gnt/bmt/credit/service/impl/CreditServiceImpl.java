package io.gnt.bmt.credit.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gnt.bmt.credit.model.CreditType;
import io.gnt.bmt.credit.model.Credit;
import io.gnt.bmt.credit.model.CreditStatus;
import io.gnt.bmt.credit.repository.CreditRepository;
import io.gnt.bmt.credit.service.CreditService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
public class CreditServiceImpl implements CreditService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    CreditRepository creditRepository;

    @PostConstruct
    void init(){
        if(creditRepository.count() < 1 ) {
            creditRepository.save(new Credit(CreditType.MORTGAGE, "6734922-3858-4245-ab06-0e7ee9b646de", 5));
            creditRepository.save(new Credit(CreditType.PERSONAL, "6734922-3858-4245-ab06-0e7ee9b646de", 15));

            creditRepository.save(new Credit(CreditType.PERSONAL, "017bd15e-ab6e-4325-bbfd-3df50b10c112", 15));
            creditRepository.save(new Credit(CreditType.PERSONAL, "017bd15e-ab6e-4325-bbfd-3df50b10c112", 25));
        }
    }

    @Override
    public List<Credit> getCustomerCredits(String customerUid) {
        return creditRepository.getCreditsByCustomerUid(customerUid);
    }

    @Override
    public List<Credit> getCredits() {
        return creditRepository.findAll();
    }


    @Transactional
    public Credit issueCredit(Credit credit){
        try {

            Credit createdCredit = creditRepository.save(
                    new Credit(credit.getType(), credit.getCustomerUid(),credit.getAmount())
            );

            rabbitTemplate.convertAndSend(
                    "exchange",
                    "customer.update",
                    "{\"creditId\":\""+ createdCredit.getId() +"\", \"customerUid\":\"" + createdCredit.getCustomerUid()
                            +"\", \"amount\":\""+ createdCredit.getAmount() + "\"}");

            return createdCredit;

        } catch (Exception e){
            log.error("Error issuing credit", e);
        }

        return null;
    }

    @Transactional
    public void disableCredit(String payload){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> result = objectMapper.readValue(payload, HashMap.class);
            int creditId = Integer.parseInt(result.get("creditId"));

            Optional<Credit> found = creditRepository.findById(creditId);

            if(found.isPresent()){
                Credit c = found.get();
                c.setStatus(CreditStatus.FAILED);
                creditRepository.save(c);
            }

        } catch (Exception e){
        }
    }


    @RabbitListener(queues = "credit.issue.error")
    public void revertIssuedCredit(String payload) {
        disableCredit(payload);
    }

}



