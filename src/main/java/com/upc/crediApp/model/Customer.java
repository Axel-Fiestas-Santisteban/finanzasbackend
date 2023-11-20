package com.upc.crediApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    public String name;
    @Column(name = "lastName", length = 100, nullable = false)
    public String lastName;
    @Column(name = "dni", length = 100, nullable = false)
    public String dni;
    @Column(name = "email", length = 100, nullable = false)
    public String email;
    @Column(name = "username", length = 100, nullable = false)
    public String username;
    @Column(name = "password", length = 100, nullable = false)
    public String password;
/*    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    public List<Cronograma> cronograma;*/


    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    public List<PlanPago> planPago;

}
