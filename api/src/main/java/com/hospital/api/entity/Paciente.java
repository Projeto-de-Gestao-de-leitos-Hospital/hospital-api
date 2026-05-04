package com.hospital.api.entity;

import com.hospital.api.enums.SexoPaciente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Long idPaciente;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", unique = true)
    private Usuario usuario;

    @Column(name = "numero_prontuario", unique = true, length = 20)
    private String numeroProntuario;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SexoPaciente sexo;

    @Column(name = "nome_responsavel", length = 100)
    private String nomeResponsavel;

    @Column(name = "telefone_responsavel", length = 20)
    private String telefoneResponsavel;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    public Paciente() {
    }

    public void inativar() {
        this.ativo = false;
    }
}