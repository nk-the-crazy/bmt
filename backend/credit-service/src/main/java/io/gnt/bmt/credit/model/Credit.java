package io.gnt.bmt.credit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Entity
@Table( name = "credits" )
@NoArgsConstructor
public class Credit implements Serializable {


    public Credit(CreditType creditType, String customerUid, float amount){
        this.type = creditType;
        this.customerUid = customerUid;
        this.amount = amount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "type")
    private CreditType type = CreditType.PERSONAL;

    @Column(name = "customer_uid",  length = 80, nullable = false)
    private String customerUid;

    @Column(name = "status")
    private CreditStatus status = CreditStatus.PENDING;

    @Column(name = "amount")
    private float amount = 0;

    @Column(name = "issue_date")
    private LocalDateTime issueDate = LocalDateTime.now();

}