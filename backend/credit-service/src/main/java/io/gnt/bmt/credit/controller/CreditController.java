package io.gnt.bmt.credit.controller;

import io.gnt.bmt.credit.model.Credit;
import io.gnt.bmt.credit.service.CreditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CreditController extends BaseController {

    @Autowired
    CreditService creditService;


    @Operation(summary = "Get all customer credits")
    @GetMapping("")
    public ResponseEntity<List<Credit>> getCredits() {
        try {
            return new ResponseEntity<>(creditService.getCredits(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get customer's credits")
    @GetMapping("/")
    public ResponseEntity<List<Credit>> getCustomerCredits(
            @Parameter(description = "UID of customer to be data returned") @PathVariable("uid") String customerUid) {
        try {
            return new ResponseEntity<>(creditService.getCustomerCredits(customerUid), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Issue credit to customer")
    @PostMapping("/issue")
    public ResponseEntity<Credit> issueCredit(@RequestBody Credit credit) {
        try {
            return new ResponseEntity<>(creditService.issueCredit(credit), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
