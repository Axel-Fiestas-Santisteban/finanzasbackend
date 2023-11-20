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
@Table(name = "indicadore")
public class Indicadores {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "VAN", length = 100, nullable = false)
    private double VAN;

    @Column(name = "TIR", length = 100, nullable = false)
    private double TIR;
}
