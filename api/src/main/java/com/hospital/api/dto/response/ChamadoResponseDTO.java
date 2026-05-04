package com.hospital.api.dto.response;

import com.hospital.api.entity.Chamado;
import java.time.LocalDateTime;

public record ChamadoResponseDTO(
        Long idChamado,
        Integer idServico,
        String observacao,
        String status,
        LocalDateTime dataAbertura,
        LocalDateTime dataInicioAtend, // <-- NOVO!
        LocalDateTime dataConclusao,   // <-- NOVO!
        String nomePaciente,
        String leitoPaciente
) {
    public ChamadoResponseDTO(Chamado chamado) {
        this(
                chamado.getIdChamado(),
                chamado.getServico().getIdServico(),
                chamado.getObservacao(),
                chamado.getStatus().name(),
                chamado.getDataAbertura(),
                chamado.getDataInicioAtend(), // <-- NOVO!
                chamado.getDataConclusao(),   // <-- NOVO!
                chamado.getPaciente().getUsuario().getNome(),
                "Leito: " + chamado.getLeito().getCodigo()
        );
    }
}