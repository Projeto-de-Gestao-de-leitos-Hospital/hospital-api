package com.hospital.api.dto.request;

import com.hospital.api.enums.SexoPaciente;
import java.time.LocalDate;

public record PacienteRequestDTO(
        String nome,
        String cpf,
        String numeroProntuario,
        LocalDate dataNascimento,
        SexoPaciente sexo,
        String telefoneContato,
        String nomeResponsavel,
        String telefoneResponsavel
) {
}