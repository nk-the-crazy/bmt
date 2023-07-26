package io.gnt.bmt.customer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gnt.bmt.commons.exceptions.NotFoundException;
import io.gnt.bmt.customer.model.Customer;
import io.gnt.bmt.customer.repository.CustomerRepository;
import io.gnt.bmt.customer.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @PostConstruct
    void init(){
        if(customerRepository.count() < 1 ) {

            Customer c1 = new Customer("6734922-3858-4245-ab06-0e7ee9b646de", "Joe", "Xyz", 20);
            Customer c2 = new Customer("017bd15e-ab6e-4325-bbfd-3df50b10c112", "John", "Qwe", 40);

            customerRepository.saveAll(List.of(c1, c2));
        }
    }

    @Transactional
    public void updateCustomerBalance(String payload){
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> result = objectMapper.readValue(payload, HashMap.class);
            String customerUid = result.get("customerUid");
            float balance = Float.parseFloat(result.get("amount"));

            Optional<Customer> customer = customerRepository.findByUid(customerUid);


            if(customer.isPresent()){

                Customer c = customer.get();
                c.setBalance(c.getBalance() + balance);
                customerRepository.save(c);

                rabbitTemplate.convertAndSend("exchange","messenger.notify", payload);

                return;
            }

            throw new NotFoundException();

        } catch (Exception e){
            rabbitTemplate.convertAndSend("exchange","credit.issue.error",payload);
        }
    }

    @Transactional
    public void revertCustomerBalance(String payload){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> result = objectMapper.readValue(payload, HashMap.class);
            String customerUid = result.get("customerUid");
            float balance = Float.parseFloat(result.get("amount"));

            Optional<Customer> customer = customerRepository.findByUid(customerUid);


            if(customer.isPresent()){

                Customer c = customer.get();
                c.setBalance(c.getBalance() - balance);
                customerRepository.save(c);

                rabbitTemplate.convertAndSend("exchange","credit.issue.error",payload);

                return;
            }

            throw new NotFoundException();

        } catch (Exception e){
            rabbitTemplate.convertAndSend("exchange","credit.issue.error", payload);
        }
    }


    @RabbitListener(queues = "customer.update")
    public void revertIssuedCredit(String payload) throws JsonProcessingException {
        updateCustomerBalance(payload);
    }


    @RabbitListener(queues = "customer.update.error")
    public void onCustomerUpdateError(String payload) throws JsonProcessingException {
        revertCustomerBalance(payload);
    }


}



