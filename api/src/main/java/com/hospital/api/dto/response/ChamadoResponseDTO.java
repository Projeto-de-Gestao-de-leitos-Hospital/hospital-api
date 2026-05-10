package com.hospital.api.dto.response;

import com.hospital.api.entity.Chamado;
import java.time.LocalDateTime;

public record ChamadoResponseDTO(
        Integer idChamado,        // Alterado de Long para Integer para bater com a Entity
        Integer idServico,
        String observacao,
        String status,
        LocalDateTime dataAbertura,
        LocalDateTime dataInicioAtend,
        LocalDateTime dataConclusao,
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
                chamado.getDataInicioAtend(),
                chamado.getDataConclusao(),
                chamado.getPaciente().getNome(),
                "Leito: " + chamado.getLeito().getNumero()
        );
    }
}