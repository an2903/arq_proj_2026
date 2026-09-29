package com.trokr.model.state.contraproposta;

import com.trokr.model.Proposta;
import com.trokr.model.state.proposta.EstadoFinalizado;
import com.trokr.model.state.proposta.EstadoProposta;
import com.trokr.model.state.Status;

public class EstadoNegociadoContra implements EstadoProposta {
    @Override
    public void finalizarAcordo(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoFinalizado(), Status.FINALIZADO_CONTRA);
    }

    @Override
    public void negociacaoFalhou(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoRecusado(), Status.RECUSADO);
    }
}
