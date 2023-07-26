package io.gnt.bmt.customer.controller;

import io.gnt.bmt.customer.model.Customer;
import io.gnt.bmt.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CustomerController extends BaseController {

    @Autowired
    CustomerService customerService;


    @Operation(summary = "Get all customers")
    @GetMapping("/")
    public ResponseEntity<List<Customer>> getCustomers() {
        try {
            return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
