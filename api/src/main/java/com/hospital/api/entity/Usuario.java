package com.hospital.api.entity;

import com.hospital.api.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    // Adicionado nullable = false para refletir o NOT NULL do banco
    @Column(length = 100, nullable = false)
    private String nome;

    @Column(unique = true, length = 11)
    private String cpf;

    // Adicionado nullable = false
    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(length = 20)
    private String telefone;

    // Adicionado nullable = false
    @Column(length = 255, nullable = false)
    private String senha;

    // Adicionado length = 50 e nullable = false. O EnumType.STRING salva o texto puro!
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 50, nullable = false)
    private TipoUsuario tipo;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    public Usuario() {
    }

    public void inativar() {
        this.ativo = false;
    }
}