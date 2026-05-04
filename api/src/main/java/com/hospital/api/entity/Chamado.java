package com.hospital.api.entity;

import com.hospital.api.enums.StatusChamado;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "chamado")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chamado")
    private Long idChamado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    // --- MUDANÇA AQUI: Tiramos o Integer e colocamos a Entidade Leito ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_leito", nullable = false)
    private Leito leito;

    // --- MUDANÇA AQUI: Tiramos o Integer e colocamos a Entidade Servico ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servico", nullable = false)
    private Servico servico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_colaborador")
    private Usuario colaborador;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private StatusChamado status = StatusChamado.ABERTO;

    @Column(name = "prioridade", nullable = false, length = 20)
    private String prioridade = "MEDIA";

    @Column(columnDefinition = "TEXT")
    private String observacao;

    @Column(name = "data_abertura", nullable = false, updatable = false)
    private LocalDateTime dataAbertura = LocalDateTime.now();

    @Column(name = "data_inicio_atend")
    private LocalDateTime dataInicioAtend;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

    public Chamado() {
    }
}