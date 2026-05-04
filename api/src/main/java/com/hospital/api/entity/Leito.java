package com.hospital.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "leito")
public class Leito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_leito")
    private Integer idLeito;

    @Column(name = "codigo", length = 20, nullable = false, unique = true)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_quarto", nullable = false)
    private Quarto quarto;

    @Column(name = "disponivel", nullable = false)
    private Boolean disponivel = true;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    public Leito() {}
}