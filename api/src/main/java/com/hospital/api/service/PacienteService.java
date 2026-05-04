package com.hospital.api.service;

import com.hospital.api.dto.request.PacienteAtualizacaoDTO;
import com.hospital.api.dto.request.PacienteRequestDTO;
import com.hospital.api.dto.response.PacienteResponseDTO;
import com.hospital.api.entity.Paciente;
import com.hospital.api.entity.Usuario;
import com.hospital.api.enums.TipoUsuario;
import com.hospital.api.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public PacienteResponseDTO cadastrar(PacienteRequestDTO dto) {
        // 1. Criação do login do paciente (Regra US01)
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setCpf(dto.cpf());
        usuario.setTelefone(dto.telefoneContato());
        usuario.setSenha(dto.cpf().substring(0, 6)); // Senha inicial padrão
        usuario.setEmail(dto.cpf() + "@paciente.hospital.com"); // E-mail gerado automaticamente
        usuario.setTipo(TipoUsuario.PACIENTE);

        // 2. Criação dos dados clínicos vinculados ao usuário
        Paciente paciente = new Paciente();
        paciente.setUsuario(usuario); // <-- A ponte entre as duas tabelas!
        paciente.setNumeroProntuario(dto.numeroProntuario());
        paciente.setDataNascimento(dto.dataNascimento());
        paciente.setSexo(dto.sexo());
        paciente.setNomeResponsavel(dto.nomeResponsavel());
        paciente.setTelefoneResponsavel(dto.telefoneResponsavel());

        // 3. Salva no banco (O CascadeType.ALL que colocamos na Entidade vai salvar o usuário junto automaticamente)
        pacienteRepository.save(paciente);

        return new PacienteResponseDTO(paciente);
    }

    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> listarTodos() {
        return pacienteRepository.findAll()
                .stream()
                .map(PacienteResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public PacienteResponseDTO buscarPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        return new PacienteResponseDTO(paciente);
    }

    @Transactional
    public PacienteResponseDTO atualizar(Long id, PacienteAtualizacaoDTO dto) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado para atualização"));

        // Atualização manual no Service
        if (dto.telefoneContato() != null) {
            paciente.getUsuario().setTelefone(dto.telefoneContato());
        }
        if (dto.nomeResponsavel() != null) {
            paciente.setNomeResponsavel(dto.nomeResponsavel());
        }
        if (dto.telefoneResponsavel() != null) {
            paciente.setTelefoneResponsavel(dto.telefoneResponsavel());
        }

        pacienteRepository.save(paciente);
        return new PacienteResponseDTO(paciente);
    }

    @Transactional
    public void deletar(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado para exclusão"));

        // Inativa tanto os dados clínicos quanto o acesso ao aplicativo!
        paciente.inativar();
        paciente.getUsuario().inativar();

        pacienteRepository.save(paciente);
    }
}