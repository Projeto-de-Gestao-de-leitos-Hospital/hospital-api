package com.hospital.api.infra.seeder;

import com.hospital.api.entity.Usuario;
import com.hospital.api.enums.TipoUsuario;
import com.hospital.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DadosIniciaisSeeder implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String cpfMestre = "99988877766";

        if (usuarioRepository.findByCpf(cpfMestre) == null) {
            Usuario admin = new Usuario();
            admin.setNome("Guilherme Mestre");
            admin.setCpf(cpfMestre);
            admin.setEmail("mestre@hospital.com"); // <-- campo adicionado
            admin.setSenha(passwordEncoder.encode("123"));
            admin.setTipo(TipoUsuario.ADMIN);
            admin.setAtivo(true);
            admin.setCriadoEm(LocalDateTime.now());

            usuarioRepository.save(admin);
            System.out.println(">>> SEEDER: Usuário Administrador Mestre criado com sucesso! <<<");
        } else {
            System.out.println(">>> SEEDER: Usuário Administrador Mestre já existe no banco. <<<");
        }
    }
}