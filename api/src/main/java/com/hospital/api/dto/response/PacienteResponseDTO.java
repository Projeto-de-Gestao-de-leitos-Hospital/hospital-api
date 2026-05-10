package com.hospital.api.dto.response;

import com.hospital.api.entity.Paciente;
import com.hospital.api.enums.SexoPaciente;
import java.time.LocalDate;

public record PacienteResponseDTO(
        Integer idPaciente,
        String nome,
        String cpf,
        String numeroProntuario,
        LocalDate dataNascimento,
        SexoPaciente sexo,
        String telefoneContato,
        String nomeResponsavel,
        String telefoneResponsavel,
        Boolean ativo
) {
    // O construtor inteligente agora sabe buscar os dados nas duas tabelas!
    public PacienteResponseDTO(Paciente paciente) {
        this(
                paciente.getIdPaciente(),
                paciente.getUsuario().getNome(),     // <-- Pega o nome lá da tabela de usuário!
                paciente.getUsuario().getCpf(),      // <-- Pega o CPF lá da tabela de usuário!
                paciente.getNumeroProntuario(),
                paciente.getDataNascimento(),
                paciente.getSexo(),
                paciente.getUsuario().getTelefone(), // <-- Pega o telefone lá da tabela de usuário!
                paciente.getNomeResponsavel(),
                paciente.getTelefoneResponsavel(),
                paciente.getAtivo()
        );
    }
}