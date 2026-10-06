package com.trokr.model.state.proposta;

import com.trokr.model.Proposta;
import com.trokr.model.state.Status;


public class EstadoRascunho implements EstadoProposta {

    @Override
    public void solicitarHomologacao(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoHomologacao(), Status.HOMOLOGACAO);
    }

    @Override
    public void cancelar(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoCancelado(), Status.CANCELADO);
    }

    
}