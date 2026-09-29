package com.trokr.service;

import com.trokr.model.Avaliacao;
import com.trokr.repository.AvaliacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public Avaliacao buscarPorId(Long id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Avaliação não encontrada com o ID: " + id));
    }

    @Transactional
    public Avaliacao salvar(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }
}