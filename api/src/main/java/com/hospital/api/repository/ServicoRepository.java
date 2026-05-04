package com.hospital.api.repository;

import com.hospital.api.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    // O Spring faz a query automaticamente por causa do nome do método!
    List<Servico> findByCategoriaIdCategoria(Integer idCategoria);
}