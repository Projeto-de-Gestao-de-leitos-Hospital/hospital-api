package com.hospital.api.repository;

import com.hospital.api.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    // O nome mudou aqui!
    Paciente findByUsuarioCpf(String cpf);
}