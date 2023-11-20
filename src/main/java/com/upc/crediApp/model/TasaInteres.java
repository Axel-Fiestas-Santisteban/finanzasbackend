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
@Table(name = "tasa_interes")

public class TasaInteres {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tipo", length = 20, nullable = false)
    public String tipo;
    @Column(name = "plazo", length = 40, nullable = false)
    public String plazo;
    @Column(name = "abreviatura", length = 40, nullable = false)
    public String abreviatura;
}
