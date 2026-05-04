package com.hospital.api.controller;

import com.hospital.api.dto.request.ChamadoRequestDTO;
import com.hospital.api.dto.request.ChamadoAtualizacaoDTO;
import com.hospital.api.dto.response.ChamadoResponseDTO;
import com.hospital.api.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService chamadoService;

    // Repare que nem precisamos mais do Repository aqui! O Controller ficou puríssimo.
    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @PostMapping
    public ResponseEntity<ChamadoResponseDTO> abrirChamado(@RequestBody @Valid ChamadoRequestDTO dados) {
        ChamadoResponseDTO response = chamadoService.abrirChamado(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ChamadoResponseDTO>> listarTodos() {
        List<ChamadoResponseDTO> chamados = chamadoService.listarTodos();
        return ResponseEntity.ok(chamados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamadoResponseDTO> buscarPorId(@PathVariable Long id) {
        ChamadoResponseDTO response = chamadoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    // --- ROTAS NOVAS REFATORADAS ---

    @PutMapping("/{id}")
    public ResponseEntity<ChamadoResponseDTO> atualizarChamado(@PathVariable Long id, @RequestBody ChamadoAtualizacaoDTO dados) {
        ChamadoResponseDTO response = chamadoService.atualizarChamado(id, dados);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarChamado(@PathVariable Long id) {
        chamadoService.deletarChamado(id);
        return ResponseEntity.noContent().build(); // Retorna 204 (sem conteúdo), que é o padrão para DELETE
    }

    // Rota para o colaborador INICIAR o atendimento (Ex: PUT /chamados/1/iniciar/2)
    // Onde '1' é o ID do chamado e '2' é o ID do colaborador Roberto
    // Rota para o colaborador INICIAR o atendimento (Ex: PUT /chamados/1/iniciar/2)
    // Onde '1' é o ID do chamado e '2' é o ID do colaborador Roberto
    @PutMapping("/{idChamado}/iniciar/{idColaborador}")
    public ResponseEntity<ChamadoResponseDTO> iniciarChamado(
            @PathVariable Long idChamado,
            @PathVariable Integer idColaborador) {
        // Tem que ser chamadoService aqui!
        return ResponseEntity.ok(chamadoService.iniciarAtendimento(idChamado, idColaborador));
    }

    @PutMapping("/{idChamado}/finalizar")
    public ResponseEntity<ChamadoResponseDTO> finalizarChamado(@PathVariable Long idChamado) {
        // Tem que ser chamadoService aqui!
        return ResponseEntity.ok(chamadoService.finalizarAtendimento(idChamado));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ChamadoResponseDTO>> listarPorStatus(@PathVariable com.hospital.api.enums.StatusChamado status) {
        return ResponseEntity.ok(chamadoService.listarPorStatus(status));
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<ChamadoResponseDTO>> listarPorCategoria(@PathVariable Integer idCategoria) {
        return ResponseEntity.ok(chamadoService.listarPorCategoria(idCategoria));
    }

}