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
@Table(name = "moneda")
public class Moneda {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", length = 100, nullable = false)
    public String nombre;
    @Column(name = "simbolo",length = 100,nullable = false)
    public String simbolo;
    @Column(name = "abreviatura",length = 100,nullable = false)
    public String abreviatura;

    @JsonIgnore
    @ManyToMany
    private List<Informacion> informacions;

}
