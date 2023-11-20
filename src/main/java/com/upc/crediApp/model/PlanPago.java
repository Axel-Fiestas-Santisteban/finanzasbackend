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
@Table(name = "plan_pago")
public class PlanPago {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_plan", nullable = false, length = 200)
    private String name;

    //Un plan de pago puede tener muchos cronogramas
    @OneToMany(mappedBy = "planPago")
    @JsonIgnore
    public List<Cronograma> cronograma;

    //Muchos planes de pago pueden pertenecer a un cliente
    @ManyToOne()
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;



}
