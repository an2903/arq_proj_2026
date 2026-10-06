package com.trokr.service;

import com.trokr.model.LogEvento;
import com.trokr.repository.LogEventoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class LogEventoService {

    private final LogEventoRepository logEventoRepository;

    public LogEventoService(LogEventoRepository logEventoRepository) {
        this.logEventoRepository = logEventoRepository;
    }

    public void registrarInfo(String tipo, String origem, String mensagem, Map<String, Object> payload) {
        LogEvento log = new LogEvento();
        log.setTipo(tipo);
        log.setNivel("INFO");
        log.setOrigem(origem);
        log.setMensagem(mensagem);
        log.setPayload(payload);
        log.setTimestamp(LocalDateTime.now());
        
        logEventoRepository.save(log);
    }

    public void registrarErro(String tipo, String origem, String mensagem, Exception excecao) {
        LogEvento log = new LogEvento();
        log.setTipo(tipo);
        log.setNivel("ERROR");
        log.setOrigem(origem);
        log.setMensagem(mensagem);
        
        if (excecao != null) {
            log.setPayload(Map.of("erro_tecnico", excecao.getMessage()));
        }
        
        log.setTimestamp(LocalDateTime.now());
        
        logEventoRepository.save(log);
    }
}