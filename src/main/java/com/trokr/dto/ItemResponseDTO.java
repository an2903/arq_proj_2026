package com.trokr.dto;

import com.trokr.model.Item;
import java.time.LocalDateTime;
import com.trokr.model.CategoriaItem;

/**
 * Dados de saída de um Item. Os dados do dono são achatados aqui
 * (usuarioId/usuarioNome) para manter o DTO simples, em vez de criar mais
 * uma classe aninhada só para isso.
 */
public record ItemResponseDTO(
        Long id,
        String titulo,
        String descricao,
        Long usuarioId,
        String usuarioNome,
        LocalDateTime dataCriacao,
        CategoriaItem categoria
) {

    public static ItemResponseDTO fromEntity(Item item) {
        return new ItemResponseDTO(
                item.getId(),
                item.getTitulo(),
                item.getDescricao(),
                item.getUsuarioProprietario().getId(),
                item.getUsuarioProprietario().getNome(),
                item.getDataCriacao(),
                item.getCategoria()
        );
    }
}
