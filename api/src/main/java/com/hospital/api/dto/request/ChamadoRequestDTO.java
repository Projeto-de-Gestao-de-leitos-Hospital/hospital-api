package com.hospital.api.dto.request;

public record ChamadoRequestDTO(
        Long idPaciente,
        Integer idServico, // Mudamos de TipoChamado para o ID numérico do serviço
        Integer idLeito,   // Adicionamos o leito, que agora é obrigatório no banco
        String observacao
) {
}