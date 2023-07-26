package io.gnt.bmt.identity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.FetchType;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_name", length = 60, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 80, nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "salt", length = 80, nullable = false)
    @JsonIgnore
    private String salt;

    @Column(name = "type")
    @JsonIgnore
    private short type = 2;

    @Column(name = "status")
    private short status = 1;

    @Column(name = "last_login")
    private Date lastLogin = null;

    // *********************************************
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id") })
    private List<Role> roles = new ArrayList<>();
    // *********************************************


}
