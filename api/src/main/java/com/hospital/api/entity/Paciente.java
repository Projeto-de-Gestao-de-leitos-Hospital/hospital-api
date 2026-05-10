package com.hospital.api.entity;

import com.hospital.api.enums.SexoPaciente;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "paciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idPaciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Integer idPaciente;

    // 👇 A PONTE ENTRE AS TABELAS QUE O SERVICE ESTAVA PEDINDO 👇
    // O CascadeType.ALL faz a mágica de salvar o usuário de login automaticamente quando você salva o paciente
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "numero_prontuario")
    private String numeroProntuario;

    // Dados pessoais
    private String nome;
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private SexoPaciente sexo;

    // 👇 AS GAVETAS DE EMERGÊNCIA QUE O SERVICE ESTAVA PEDINDO 👇
    @Column(name = "nome_responsavel")
    private String nomeResponsavel;

    @Column(name = "telefone_responsavel")
    private String telefoneResponsavel;

    private Boolean ativo;

    // Método para exclusão lógica
    public void inativar() {
        this.ativo = false;
    }
}