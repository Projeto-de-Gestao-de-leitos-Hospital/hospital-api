package com.hospital.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate; // Importante: Mudamos para LocalDate
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "internacao")
public class Internacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_internacao")
    private Integer idInternacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_leito", nullable = false)
    private Leito leito;

    // --- MUDANÇA AQUI: LocalDate para refletir o "DATE" do Postgres ---
    @Column(name = "data_entrada", nullable = false)
    private LocalDate dataEntrada = LocalDate.now();

    @Column(name = "data_saida")
    private LocalDate dataSaida;
    // -----------------------------------------------------------------

    @Column(name = "motivo_internacao", columnDefinition = "TEXT")
    private String motivoInternacao;

    @Column(name = "medico_responsavel", length = 100)
    private String medicoResponsavel;

    @Column(name = "convenio", length = 100)
    private String convenio;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    // A data de criação continua sendo LocalDateTime (TIMESTAMP no banco)
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    public Internacao() {}
}