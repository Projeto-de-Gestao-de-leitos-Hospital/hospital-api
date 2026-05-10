package com.hospital.api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "internacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idInternacao")
public class Internacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_internacao")
    private Integer idInternacao;

    // Ajustado para id_paciente
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    // Ajustado para id_leito
    @ManyToOne
    @JoinColumn(name = "id_leito")
    private Leito leito;

    // No seu banco é do tipo 'date', então usamos LocalDate em vez de LocalDateTime
    @Column(name = "data_entrada")
    private LocalDate dataEntrada;

    // Ajustado de data_alta para data_saida
    @Column(name = "data_saida")
    private LocalDate dataSaida;

    // 👇 NOVOS CAMPOS ESPELHADOS DO SEU BANCO DE DADOS 👇
    @Column(name = "motivo_internacao")
    private String motivoInternacao;

    @Column(name = "medico_responsavel")
    private String medicoResponsavel;

    private String convenio;

    private Boolean ativo;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}