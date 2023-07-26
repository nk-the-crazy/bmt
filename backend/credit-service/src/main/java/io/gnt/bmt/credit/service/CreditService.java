package io.gnt.bmt.credit.service;

import io.gnt.bmt.credit.model.Credit;

import java.util.List;

public interface CreditService {
    List<Credit> getCustomerCredits(String customerUid);

    List<Credit> getCredits();

    Credit issueCredit(Credit credit);
}
