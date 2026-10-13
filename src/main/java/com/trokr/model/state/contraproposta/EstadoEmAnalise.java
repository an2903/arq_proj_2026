package com.trokr.model.state.contraproposta;

import com.trokr.model.Proposta;
import com.trokr.model.state.Status;
import com.trokr.model.state.proposta.EstadoNegociado;
import com.trokr.model.state.proposta.EstadoProposta;

public class EstadoEmAnalise implements EstadoProposta {

    @Override
    public void aceitar (Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoNegociado(), Status.NEGOCIADO_CONTRA);
    }

    @Override
    public void cancelar(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoCanceladoContra(), Status.CANCELADO_CONTRA);
    }


}
