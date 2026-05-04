package com.hospital.api.service;

import com.hospital.api.dto.request.UsuarioRequestDTO;
import com.hospital.api.dto.response.UsuarioResponseDTO;
import com.hospital.api.entity.Usuario;
import com.hospital.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setCpf(dto.cpf());
        usuario.setEmail(dto.email());
        usuario.setTelefone(dto.telefone());

        // Aqui estamos salvando a senha em texto limpo por enquanto.
        // O próximo grande passo será criptografar essa senha antes de salvar!
        usuario.setSenha(dto.senha());

        usuario.setTipo(dto.tipo());

        usuarioRepository.save(usuario);
        return new UsuarioResponseDTO(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Integer id) { // <-- Troque Long por Integer aqui
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UsuarioResponseDTO(usuario);
    }

    @Transactional
    public void deletar(Integer id) { // <-- Troque Long por Integer aqui também
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para exclusão"));

        usuario.inativar();
        usuarioRepository.save(usuario);
    }
}