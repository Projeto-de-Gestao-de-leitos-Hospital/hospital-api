package com.hospital.api.repository;

import com.hospital.api.entity.Chamado;
import com.hospital.api.enums.StatusChamado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    // Filtra chamados pelo Status (Ex: Busca só os ABERTOS)
    List<Chamado> findByStatus(StatusChamado status);

    // Navega do Chamado -> Servico -> Categoria para filtrar!
    List<Chamado> findByServicoCategoriaIdCategoria(Integer idCategoria);
}