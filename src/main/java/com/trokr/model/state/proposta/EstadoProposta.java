package com.trokr.model.state.proposta;

import com.trokr.model.Proposta;

public interface EstadoProposta {
    
    default void solicitarHomologacao(Proposta proposta) { throw new IllegalStateException("Ação inválida neste estado."); }
    default void enviarParaHomologacao(Proposta proposta) { throw new IllegalStateException("Ação inválida neste estado."); }
    default void recusarParaHomologacao(Proposta proposta) { throw new IllegalStateException("Ação inválida neste estado."); }
    default void aceitar(Proposta proposta) { throw new IllegalStateException("Ação inválida neste estado."); }
    default void cancelar(Proposta proposta) { throw new IllegalStateException("Ação inválida neste estado."); }
    default void voltar(Proposta proposta) { throw new IllegalStateException("Ação inválida neste estado."); }
    default void enviar(Proposta proposta) {throw new IllegalStateException("Ação 'enviar' não é permitida no estado atual.");} 

    default void aceitarContraproposta(Proposta proposta) { throw new IllegalStateException("Ação inválida neste estado."); }
    default void negociacaoFalhou(Proposta proposta) { throw new IllegalStateException("Ação inválida neste estado."); }
    default void finalizarAcordo(Proposta proposta) { throw new IllegalStateException("Ação inválida neste estado."); }


}




