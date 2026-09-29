package com.trokr.model.state.contraproposta;

import com.trokr.model.Proposta;
import com.trokr.model.state.proposta.EstadoNegociado;
import com.trokr.model.state.proposta.EstadoProposta;
import com.trokr.model.state.Status;

public class EstadoEm_Analise implements EstadoProposta {

    @Override
    public void aceitar (Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoNegociado(), Status.NEGOCIADO_CONTRA);
    }

    @Override
    public void recusarParaHomologacao(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoRecusado(), Status.RECUSADO);
    }

    @Override
    public void cancelar(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoCanceladoContra(), Status.CANCELADO_CONTRA);
    }
}
