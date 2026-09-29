package com.trokr.model.state.proposta;

import com.trokr.model.Proposta;
import com.trokr.model.state.Status;

public class EstadoNegociado implements EstadoProposta {

    @Override
    public void finalizarAcordo(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoFinalizado(),Status.FINALIZADO);
    }

    @Override
    public void recusarParaHomologacao(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoAtiva(), Status.ATIVA);
    }

}