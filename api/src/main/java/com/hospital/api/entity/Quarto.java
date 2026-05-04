package com.hospital.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "quarto")
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_quarto")
    private Integer idQuarto;

    @Column(name = "numero", length = 10, nullable = false, unique = true)
    private String numero;

    @Column(name = "andar", nullable = false)
    private Short andar;

    @Column(name = "ala", length = 50, nullable = false)
    private String ala;

    @Column(name = "tipo", length = 20, nullable = false)
    private String tipo = "INDIVIDUAL";

    @Column(name = "capacidade", nullable = false)
    private Short capacidade;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    public Quarto() {}
}