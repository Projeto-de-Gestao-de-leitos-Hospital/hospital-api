package com.hospital.api.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    // Captura as exceções que nós jogamos manualmente nos Services
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> tratarErroRegraDeNegocio(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // (Opcional) Podemos adicionar mais tratadores aqui no futuro,
    // como por exemplo para tratar o erro 404 quando buscar um ID que não existe.
}