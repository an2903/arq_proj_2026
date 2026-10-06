package com.trokr.dto;

import com.trokr.model.Usuario;
import java.time.LocalDateTime;


public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        LocalDateTime dataCriacao,
        Integer saldoCreditos
) {

    public static UsuarioResponseDTO fromEntity(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataCriacao(),
                usuario.getSaldoCreditos()
                
        );
    }
}
