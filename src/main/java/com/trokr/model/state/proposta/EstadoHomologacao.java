package com.trokr.model.state.proposta;

import com.trokr.model.Proposta;
import com.trokr.model.state.Status;

public class EstadoHomologacao implements EstadoProposta {

    @Override
    public void aceitar(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoAtiva(), Status.ATIVA);
    }

    @Override
    public void recusarParaHomologacao(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoRascunho(), Status.RASCUNHO);
    }

    @Override
    public void cancelar(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoCancelado(), Status.CANCELADO);
    }

}