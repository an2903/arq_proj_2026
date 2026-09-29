package com.trokr.controller;

import com.trokr.model.Avaliacao;
import com.trokr.model.StatusAvaliacao;
import com.trokr.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.trokr.dto.AvaliacaoRequestDTO;
import com.trokr.dto.AvaliacaoResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> avaliar(@PathVariable Long id, @Valid @RequestBody AvaliacaoRequestDTO dto) {
        Avaliacao avaliacao = avaliacaoService.buscarPorId(id);

        if (avaliacao.getStatus() != StatusAvaliacao.PENDENTE) {
            throw new IllegalStateException("Esta avaliação já foi preenchida.");
        }

        avaliacao.setNota(dto.nota());
        avaliacao.setDescricao(dto.descricao());
        avaliacao.setStatus(StatusAvaliacao.AVALIADA);

        Avaliacao salva = avaliacaoService.salvar(avaliacao);
        
        return ResponseEntity.ok(AvaliacaoResponseDTO.fromEntity(salva));
    }
}