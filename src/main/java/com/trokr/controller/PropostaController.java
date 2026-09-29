package com.trokr.controller;
import com.trokr.dto.PropostaRequestDTO;
import com.trokr.dto.PropostaResponseDTO;

import com.trokr.model.Proposta;
import com.trokr.service.PropostaService;


import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/propostas")
public class PropostaController {

    @Autowired
    private PropostaService propostaService;

    @PostMapping
    public ResponseEntity<PropostaResponseDTO> criar(@Valid @RequestBody PropostaRequestDTO dto) {
        Proposta salva = propostaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(PropostaResponseDTO.fromEntity(salva));
    }

@PostMapping("/{id}/contraproposta")
    public ResponseEntity<PropostaResponseDTO> criarContraproposta(
            @PathVariable Long id, 
            @Valid @RequestBody PropostaRequestDTO dto) {
        Proposta contraproposta = propostaService.criarContraproposta(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(PropostaResponseDTO.fromEntity(contraproposta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropostaResponseDTO> buscarPorId(@PathVariable Long id) {
        Proposta proposta = propostaService.buscarPorId(id);
        return ResponseEntity.ok(PropostaResponseDTO.fromEntity(proposta));
    }

    @GetMapping
    public ResponseEntity<List<PropostaResponseDTO>> listarTodas() {
        List<PropostaResponseDTO> propostas = propostaService.listarTodas()
                .stream()
                .map(PropostaResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(propostas);
    }

@PutMapping("/{id}/solicitar-homologacao")
    public ResponseEntity<Void> solicitarHomologacao(@PathVariable Long id) {
        propostaService.solicitarHomologacao(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/enviar-homologacao")
    public ResponseEntity<Void> enviarParaHomologacao(@PathVariable Long id) {
        propostaService.enviarParaHomologacao(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/enviar")
    public ResponseEntity<Void> enviar(@PathVariable Long id) {
        propostaService.enviar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/aceitar")
    public ResponseEntity<Void> aceitar(@PathVariable Long id) {
        propostaService.aceitar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/aceitar-contraproposta")
    public ResponseEntity<Void> aceitarContraproposta(@PathVariable Long id) {
        propostaService.aceitarContraproposta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/recusar")
    public ResponseEntity<Void> recusar(@PathVariable Long id) {
        propostaService.recusarParaHomologacao(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        propostaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/finalizar-acordo")
    public ResponseEntity<Void> finalizarAcordo(@PathVariable Long id) {
        propostaService.finalizarAcordo(id);
        return ResponseEntity.noContent().build();
    }
    
}
