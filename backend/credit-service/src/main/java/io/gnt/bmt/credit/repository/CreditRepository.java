package io.gnt.bmt.credit.repository;

import io.gnt.bmt.credit.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Integer> {

    List<Credit> getCreditsByCustomerUid(String customerUid);
}
