package io.gnt.bmt.identity.model;

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
import jakarta.persistence.CascadeType;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table( name = "roles" )
@NoArgsConstructor
public class Role {

    public Role(String roleName){
        this.name = roleName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name",  length = 20, nullable = false)
    private String name;

    @Column(name = "type")
    private short type = 2;

    @Column(name = "details", length = 30)
    private String details;

}