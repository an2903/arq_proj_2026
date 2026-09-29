package com.trokr.dto;

import com.trokr.model.Avaliacao;

public record AvaliacaoResponseDTO(
    Long id,
    Long avaliadorId,
    String avaliadorNome,
    Long avaliadoId,
    String avaliadoNome,
    String status,
    Integer nota,
    String descricao
) {
    public static AvaliacaoResponseDTO fromEntity(Avaliacao avaliacao) {
        return new AvaliacaoResponseDTO(
            avaliacao.getId(),
            avaliacao.getAvaliador().getId(),
            avaliacao.getAvaliador().getNome(),
            avaliacao.getAvaliado().getId(),
            avaliacao.getAvaliado().getNome(),
            avaliacao.getStatus().name(),
            avaliacao.getNota(),
            avaliacao.getDescricao()
        );
    }
}