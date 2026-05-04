package com.hospital.api.service;

import com.hospital.api.entity.Chamado;
import com.hospital.api.entity.Leito;
import com.hospital.api.entity.Paciente;
import com.hospital.api.entity.Servico;
import com.hospital.api.dto.request.ChamadoRequestDTO;
import com.hospital.api.dto.request.ChamadoAtualizacaoDTO;
import com.hospital.api.dto.response.ChamadoResponseDTO;
import com.hospital.api.repository.ChamadoRepository;
import com.hospital.api.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final PacienteRepository pacienteRepository;

    public ChamadoService(ChamadoRepository chamadoRepository,
                          PacienteRepository pacienteRepository) {
        this.chamadoRepository = chamadoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public ChamadoResponseDTO abrirChamado(ChamadoRequestDTO dto) {
        Paciente paciente = pacienteRepository.findById(dto.idPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Chamado chamado = new Chamado();
        chamado.setPaciente(paciente);

        // --- TRUQUE DO HIBERNATE ---
        // Instanciamos o Leito só com o ID para salvar a chave estrangeira
        Leito leito = new Leito();
        leito.setIdLeito(dto.idLeito());
        chamado.setLeito(leito);

        // Instanciamos o Servico só com o ID para salvar a chave estrangeira
        Servico servico = new Servico();
        servico.setIdServico(dto.idServico());
        chamado.setServico(servico);
        // ---------------------------

        chamado.setObservacao(dto.observacao());

        chamadoRepository.save(chamado);

        return new ChamadoResponseDTO(chamado);
    }

    @Transactional(readOnly = true)
    public List<ChamadoResponseDTO> listarTodos() {
        return chamadoRepository.findAll()
                .stream()
                .map(ChamadoResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ChamadoResponseDTO buscarPorId(Long id) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
        return new ChamadoResponseDTO(chamado);
    }

    @Transactional
    public ChamadoResponseDTO atualizarChamado(Long id, ChamadoAtualizacaoDTO dto) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado para atualização"));

        if (dto.status() != null) {
            chamado.setStatus(dto.status());
        }
        if (dto.observacao() != null) {
            chamado.setObservacao(dto.observacao());
        }

        chamadoRepository.save(chamado);
        return new ChamadoResponseDTO(chamado);
    }

    @Transactional
    public void deletarChamado(Long id) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado para exclusão"));
        chamadoRepository.delete(chamado);
    }
    @Transactional
    public ChamadoResponseDTO iniciarAtendimento(Long idChamado, Integer idColaborador) {
        Chamado chamado = chamadoRepository.findById(idChamado)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));

        // Proteção: Só inicia se estiver ABERTO
        if (!chamado.getStatus().name().equals("ABERTO")) {
            throw new RuntimeException("Apenas chamados ABERTOS podem ser iniciados.");
        }

        // Muda o status e grava a hora exata do clique (US18 e RN05)
        chamado.setStatus(com.hospital.api.enums.StatusChamado.EM_ATENDIMENTO);
        chamado.setDataInicioAtend(java.time.LocalDateTime.now());

        // Vincula qual colaborador da equipe "puxou" o chamado
        com.hospital.api.entity.Usuario colaborador = new com.hospital.api.entity.Usuario();
        colaborador.setIdUsuario(idColaborador);
        chamado.setColaborador(colaborador);

        chamadoRepository.save(chamado);
        return new ChamadoResponseDTO(chamado);
    }

    @Transactional
    public ChamadoResponseDTO finalizarAtendimento(Long idChamado) {
        Chamado chamado = chamadoRepository.findById(idChamado)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));

        // Proteção: Só finaliza se estiver EM_ATENDIMENTO
        if (!chamado.getStatus().name().equals("EM_ATENDIMENTO")) {
            throw new RuntimeException("Apenas chamados EM ATENDIMENTO podem ser finalizados.");
        }

        // Muda o status e grava a hora exata do fim para o cálculo do SLA (RN05)
        chamado.setStatus(com.hospital.api.enums.StatusChamado.CONCLUIDO);
        chamado.setDataConclusao(java.time.LocalDateTime.now());

        chamadoRepository.save(chamado);
        return new ChamadoResponseDTO(chamado);
    }

    @Transactional(readOnly = true)
    public List<ChamadoResponseDTO> listarPorStatus(com.hospital.api.enums.StatusChamado status) {
        return chamadoRepository.findByStatus(status)
                .stream()
                .map(ChamadoResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ChamadoResponseDTO> listarPorCategoria(Integer idCategoria) {
        return chamadoRepository.findByServicoCategoriaIdCategoria(idCategoria)
                .stream()
                .map(ChamadoResponseDTO::new)
                .toList();
    }

}