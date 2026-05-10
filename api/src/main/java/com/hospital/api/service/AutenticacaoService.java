package com.hospital.api.service;

import com.hospital.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // A sua entidade usa o CPF como "login" e retorna o UserDetails
        UserDetails usuario = repository.findByCpf(username);

        // Contrato estrito do Spring Security: se não achar, obrigatoriamente lança o erro!
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado no sistema");
        }

        // Se achar, retorna o usuário limpo.
        return usuario;
    }
}
