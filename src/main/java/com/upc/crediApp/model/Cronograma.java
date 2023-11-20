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
@Table(name = "cronograma")
public class Cronograma {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    public Long id;

    @OneToMany(mappedBy = "cronograma")
    public List<Cuota> cuotas;

/*    @ManyToOne()
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;*/

    @ManyToOne()
    @JoinColumn(name = "plan_pago_id", nullable = false)
    private PlanPago planPago;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "informacion_id", referencedColumnName = "id")
    private Informacion informacion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "indicadores_id", referencedColumnName = "id")
    private Indicadores indicadores;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehiculo_id", referencedColumnName = "id")
    private Vehiculo vehiculo;

    @Column(name = "tipo_cronograma",length = 100, nullable = false)
    private String tipoCronograma;

}
