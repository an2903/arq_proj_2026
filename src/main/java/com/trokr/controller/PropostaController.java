package com.trokr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trokr.dto.MensagemAcaoDTO;
import com.trokr.dto.PropostaRequestDTO;
import com.trokr.dto.PropostaResponseDTO;
import com.trokr.model.Proposta;
import com.trokr.service.PropostaService;

import jakarta.validation.Valid;

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

    @PatchMapping("/{id}/solicitar-homologacao")
    public ResponseEntity<Void> solicitarHomologacao(@PathVariable Long id) {
        propostaService.solicitarHomologacao(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/enviar")
    public ResponseEntity<Void> enviar(@PathVariable Long id) {
        propostaService.enviar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/aceitar")
    public ResponseEntity<Void> aceitar(@PathVariable Long id) {
        propostaService.aceitar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/aceitar-contraproposta")
    public ResponseEntity<Proposta> aceitarContraproposta(
            @PathVariable Long id, 
            @RequestBody(required = false) MensagemAcaoDTO dto) {
        
        String texto = (dto != null) ? dto.mensagem() : null;
        Proposta proposta = propostaService.aceitarContraproposta(id, texto);
        return ResponseEntity.ok(proposta);
    }

    @PatchMapping("/{id}/recusar")
    public ResponseEntity<Proposta> recusar(
            @PathVariable Long id, 
            @RequestBody(required = false) MensagemAcaoDTO dto) {
        
        String texto = (dto != null) ? dto.mensagem() : null;
        Proposta proposta = propostaService.recusar(id, texto); // Lembre de ajustar no Service também!
        return ResponseEntity.ok(proposta);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        propostaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/finalizar-acordo")
    public ResponseEntity<Void> finalizarAcordo(@PathVariable Long id) {
        propostaService.finalizarAcordo(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping ("/{id}/recusar-para-homologacao")
    public ResponseEntity<Void> recusarParaHomologacao(@PathVariable Long id) {
        propostaService.recusarParaHomologacao(id);
        return ResponseEntity.noContent().build();
    }

    
}