package com.hospital.api.controller;

import com.hospital.api.dto.request.DadosAutenticacaoDTO;
import com.hospital.api.repository.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacaoDTO dados) {
        // OLHA A DIFERENÇA AQUI: findByUsuarioCpf
        var paciente = repository.findByUsuarioCpf(dados.cpf());

        // OLHA A DIFERENÇA AQUI: paciente.getUsuario().getSenha()
        if (paciente != null && paciente.getUsuario().getSenha().equals(dados.senha())) {
            return ResponseEntity.ok(paciente);
        }

        return ResponseEntity.status(401).body("Usuário ou senha inválidos");
    }
}