package com.trokr.model.state.contraproposta;

import com.trokr.model.Proposta;
import com.trokr.model.state.Status;
import com.trokr.model.state.proposta.EstadoProposta;

public class EstadoRascunhoContra implements EstadoProposta {
    
@Override 
    public void enviar(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoEmAnalise(), Status.EM_ANALISE);
        
    }

@Override 
    public void cancelar(Proposta proposta) {
        proposta.mudarEstadoPara(new EstadoCanceladoContra(), Status.CANCELADO_CONTRA);
        
    }


}
