package io.gnt.bmt.customer.repository;

import io.gnt.bmt.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> getCreditsByUid(String customerUid);

    Optional<Customer> findByUid(String uid);
}
