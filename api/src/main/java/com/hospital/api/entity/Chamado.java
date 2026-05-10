package com.hospital.api.entity;

import com.hospital.api.enums.StatusChamado;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chamado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idChamado")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chamado")
    private Integer idChamado;

    // Conectando direto com o Paciente (igual ao seu BD)
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    // Conectando com o Leito
    @ManyToOne
    @JoinColumn(name = "id_leito")
    private Leito leito;

    // Conectando com o Servico (Você tem id_servico no BD)
    @ManyToOne
    @JoinColumn(name = "id_servico")
    private Servico servico;

    // Quem vai atender (No seu BD está id_colaborador)
    @ManyToOne
    @JoinColumn(name = "id_colaborador")
    private Usuario colaborador;

    @Enumerated(EnumType.STRING)
    private StatusChamado status;

    private String prioridade;

    private String observacao;

    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;

    @Column(name = "data_inicio_atend")
    private LocalDateTime dataInicioAtend;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;
}