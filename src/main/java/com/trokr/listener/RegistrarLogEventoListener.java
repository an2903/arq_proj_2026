package com.trokr.listener; 

import com.trokr.service.LogEventoService; 
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.trokr.event.TrocaConcluidaEvent;

import java.util.HashMap;
import java.util.Map;

@Component
public class RegistrarLogEventoListener {

    private final LogEventoService logEventoService;

    public RegistrarLogEventoListener(LogEventoService logEventoService) {
        this.logEventoService = logEventoService;
    }

    @EventListener
    public void registrarLog(TrocaConcluidaEvent event) {
        
        Map<String, Object> payload = new HashMap<>();
        payload.put("propostaId", event.getPropostaId());
        payload.put("usuarioA_id", event.getUsuarioA().getId());
        payload.put("usuarioB_id", event.getUsuarioB().getId());
        payload.put("dataConclusao", event.getDataConclusao());

        
        logEventoService.registrarInfo(
            "TROCA_CONCLUIDA",            
            "PropostaService",            
            "Acordo de troca finalizado", 
            payload                       
        );
    }
}