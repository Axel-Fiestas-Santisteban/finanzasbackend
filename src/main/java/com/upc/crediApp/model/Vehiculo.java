package com.upc.crediApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Vehiculo")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(name = "marca", length = 100, nullable = false)
    public String marca;
    @Column(name = "modelo", length = 100, nullable = false)
    public String modelo;
    @Column(name = "precio", length = 100, nullable = false)
    public double precio;

/*    @OneToOne(mappedBy = "vehiculo")
    private Cronograma cronograma;*/

}
