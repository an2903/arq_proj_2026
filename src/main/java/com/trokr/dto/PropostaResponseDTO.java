
package com.trokr.dto;
import com.trokr.model.Proposta;
import com.trokr.model.state.Status;

public record PropostaResponseDTO(
    Long id,
    Long usuarioId,
    String usuarioNome,
    Long itemId,
    String itemTitulo,
    Status status,
    Long propostaAnteriorId,
    boolean ehContraproposta
) {
    public static PropostaResponseDTO fromEntity(Proposta proposta) {
        return new PropostaResponseDTO(
            proposta.getId(),
            proposta.getUsuario() != null ? proposta.getUsuario().getId() : null,
            proposta.getUsuario() != null ? proposta.getUsuario().getNome() : null,
            proposta.getItem() != null ? proposta.getItem().getId() : null,
            proposta.getItem() != null ? proposta.getItem().getTitulo() : null,
            proposta.getStatus(),
            proposta.getPropostaAnterior() != null ? proposta.getPropostaAnterior().getId() : null,
            proposta.Contraproposta()
        );
    }
}