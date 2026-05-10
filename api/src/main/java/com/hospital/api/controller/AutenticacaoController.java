package com.hospital.api.controller;

import com.hospital.api.dto.request.DadosAutenticacaoDTO;
import com.hospital.api.dto.response.TokenDTO;
import com.hospital.api.entity.Usuario;
import com.hospital.api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> efetuarLogin(@RequestBody @Valid DadosAutenticacaoDTO dados) {
        try {
            var cpf = dados.cpf().replaceAll("\\D", "");
            if (cpf.isBlank()) {
                throw new AuthenticationCredentialsNotFoundException("CPF vazio");
            }

            var authenticationToken = new UsernamePasswordAuthenticationToken(cpf, dados.senha());
            var authentication = manager.authenticate(authenticationToken);

            var usuario = (Usuario) authentication.getPrincipal();
            var tokenJWT = tokenService.gerarToken(usuario.getCpf());

            return ResponseEntity.ok(new TokenDTO(tokenJWT));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
