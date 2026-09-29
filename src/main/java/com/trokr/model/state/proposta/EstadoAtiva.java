package com.trokr.model.state.proposta;

import com.trokr.model.Proposta;
import com.trokr.model.state.Status;

public class EstadoAtiva implements EstadoProposta {

    @Override
    public void solicitarHomologacao(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoNegociado(), Status.NEGOCIADO);
    }

    @Override
    public void voltar(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoRascunho(), Status.RASCUNHO);
    }

    @Override
    public void cancelar(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoRascunho(), Status.CANCELADO);
    }
    
}