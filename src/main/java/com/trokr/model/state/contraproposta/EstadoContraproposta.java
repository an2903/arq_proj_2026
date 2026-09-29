package com.trokr.model.state.contraproposta;

import com.trokr.model.Proposta;

public interface EstadoContraproposta {
    
    default void enviar(Proposta proposta) {
        throw new IllegalStateException("Ação inválida neste estado");
    }

    default void aceitarParaNegociado(Proposta proposta) {
        throw new IllegalStateException("Ação inválida neste estado");
    }

    default void finalizarAcordo(Proposta proposta) {
        throw new IllegalStateException("Ação inválida neste estado");
    }

    default void recusar(Proposta proposta) {
        throw new IllegalStateException("Ação inválida neste estado");
    }

    default void cancelar(Proposta proposta) {
        throw new IllegalStateException("Ação inválida neste estado");
    }
}
