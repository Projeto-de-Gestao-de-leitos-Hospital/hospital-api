package com.hospital.api.service;

import com.hospital.api.dto.request.PacienteAtualizacaoDTO;
import com.hospital.api.dto.request.PacienteRequestDTO;
import com.hospital.api.dto.response.PacienteResponseDTO;
import com.hospital.api.entity.Paciente;
import com.hospital.api.entity.Usuario;
import com.hospital.api.enums.TipoUsuario;
import com.hospital.api.repository.PacienteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PasswordEncoder passwordEncoder;

    public PacienteService(PacienteRepository pacienteRepository, PasswordEncoder passwordEncoder) {
        this.pacienteRepository = pacienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public PacienteResponseDTO cadastrar(PacienteRequestDTO dto) {
        var cpfLimpo = dto.cpf().replaceAll("\\D", "");
        if (cpfLimpo.length() < 6) {
            throw new IllegalArgumentException("CPF invalido para gerar senha inicial");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setCpf(cpfLimpo);
        usuario.setTelefone(dto.telefoneContato());
        usuario.setSenha(passwordEncoder.encode(cpfLimpo.substring(0, 6)));
        usuario.setEmail(cpfLimpo + "@paciente.hospital.com");
        usuario.setTipo(TipoUsuario.PACIENTE);
        usuario.setAtivo(true);
        usuario.setCriadoEm(LocalDateTime.now());

        Paciente paciente = new Paciente();
        paciente.setUsuario(usuario);
        paciente.setNumeroProntuario(dto.numeroProntuario());
        paciente.setDataNascimento(dto.dataNascimento());
        paciente.setSexo(dto.sexo());
        paciente.setNomeResponsavel(dto.nomeResponsavel());
        paciente.setTelefoneResponsavel(dto.telefoneResponsavel());
        paciente.setAtivo(true);

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
                .orElseThrow(() -> new RuntimeException("Paciente nao encontrado"));
        return new PacienteResponseDTO(paciente);
    }

    @Transactional
    public PacienteResponseDTO atualizar(Long id, PacienteAtualizacaoDTO dto) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente nao encontrado para atualizacao"));

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
                .orElseThrow(() -> new RuntimeException("Paciente nao encontrado para exclusao"));

        paciente.inativar();
        paciente.getUsuario().inativar();

        pacienteRepository.save(paciente);
    }
}
