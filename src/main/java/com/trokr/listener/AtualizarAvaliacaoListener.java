package com.trokr.listener;

import com.trokr.event.TrocaConcluidaEvent;
import com.trokr.model.Avaliacao;
import com.trokr.model.Proposta;
import com.trokr.model.StatusAvaliacao;
import com.trokr.repository.AvaliacaoRepository;
import com.trokr.repository.PropostaRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AtualizarAvaliacaoListener {

    private final AvaliacaoRepository avaliacaoRepository;
    private final PropostaRepository propostaRepository;

    public AtualizarAvaliacaoListener(AvaliacaoRepository avaliacaoRepository, PropostaRepository propostaRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.propostaRepository = propostaRepository;
    }
    
    @EventListener
    @Transactional
    public void onTrocaConcluida(TrocaConcluidaEvent event) {
       
        Proposta proposta = propostaRepository.findById(event.getPropostaId())
                .orElseThrow(() -> new IllegalStateException("Proposta não encontrada para gerar avaliação."));

        
        Avaliacao avaliacao1 = new Avaliacao();
        avaliacao1.setAvaliador(event.getUsuarioA());
        avaliacao1.setAvaliado(event.getUsuarioB());
        avaliacao1.setStatus(StatusAvaliacao.PENDENTE);
        avaliacaoRepository.save(avaliacao1);

        
        Avaliacao avaliacao2 = new Avaliacao();
        avaliacao2.setAvaliador(event.getUsuarioB());
        avaliacao2.setAvaliado(event.getUsuarioA());
        avaliacao2.setStatus(StatusAvaliacao.PENDENTE);
        avaliacaoRepository.save(avaliacao2);
    }
}