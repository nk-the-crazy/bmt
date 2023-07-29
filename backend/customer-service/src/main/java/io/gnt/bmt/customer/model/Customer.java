package io.gnt.bmt.customer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table( name = "customers" )
@NoArgsConstructor
public class Customer {

    public Customer(String uid, String firstName, String lastName, float balance) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "uid",  length = 100, unique = true, nullable = false)
    private String uid;

    @Column(name = "first_name",  length = 40, nullable = false)
    private String firstName;

    @Column(name = "last_name",  length = 40)
    private String lastName;

    @Column(name = "type")
    private short type = 2;

    @Column(name = "amount")
    private float balance = 0;

}