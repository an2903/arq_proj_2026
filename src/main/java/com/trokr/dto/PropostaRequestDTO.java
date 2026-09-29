package com.trokr.dto;

import jakarta.validation.constraints.NotNull;


public record PropostaRequestDTO(
    @NotNull(message = "O ID do usuário é obrigatório")
    Long usuarioId,

    @NotNull(message = "O ID do item principal é obrigatório")
    Long itemId,


    Long propostaAnteriorId ) {
        
    }
