package com.trokr.controller;

import com.trokr.model.LogEvento;
import com.trokr.repository.LogEventoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogEventoController {

    private final LogEventoRepository logEventoRepository;

    public LogEventoController(LogEventoRepository logEventoRepository) {
        this.logEventoRepository = logEventoRepository;
    }

    @GetMapping
    public List<LogEvento> listarPorTipo(@RequestParam String tipo) {
        return logEventoRepository.findByTipo(tipo);
    }
}