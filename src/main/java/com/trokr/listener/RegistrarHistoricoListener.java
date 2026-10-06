package com.trokr.listener;

import com.trokr.event.TrocaConcluidaEvent;
import com.trokr.model.Historico;
import com.trokr.repository.HistoricoRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrarHistoricoListener {

    private final HistoricoRepository historicoRepository;

    public RegistrarHistoricoListener(HistoricoRepository historicoRepository) {
        this.historicoRepository = historicoRepository;
    }

    @EventListener
    public void aoConcluirTroca(TrocaConcluidaEvent evento) {
        Historico historico = new Historico();
        historico.setPropostaId(evento.getPropostaId());
        historico.setStatusAnterior("EM_NEGOCIACAO"); 
        historico.setStatusNovo("FINALIZADA");
        historico.setDataAlteracao(evento.getDataConclusao());
        
        historicoRepository.save(historico);
    }
}