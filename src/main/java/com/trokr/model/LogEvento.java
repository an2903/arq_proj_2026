package com.trokr.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "log_eventos")
public class LogEvento {

    @Id
    private String id;
    private String tipo;
    private String nivel; 
    private String origem;
    private String mensagem;
    private Map<String, Object> payload;
    private LocalDateTime timestamp;
    private Long usuarioId;

    
    public LogEvento() {}

    
    public String getId() { 
        return id; }

    public void setId(String id) { 
        this.id = id; }

    public String getTipo() { 
        return tipo; }

    public void setTipo(String tipo) { 
        this.tipo = tipo; }

    public String getNivel() { 
        return nivel; }
        
    public void setNivel(String nivel) { 
        this.nivel = nivel; }

    public String getOrigem() { 
        return origem; }

    public void setOrigem(String origem) { 
        this.origem = origem; }

    public String getMensagem() { 
        return mensagem; }

    public void setMensagem(String mensagem) 
    { this.mensagem = mensagem; }

    public Map<String, Object> getPayload() 
    { return payload; }

    public void setPayload(Map<String, Object> payload) 
    { this.payload = payload; }

    public LocalDateTime getTimestamp() { 
        return timestamp; }

    public void setTimestamp(LocalDateTime timestamp) { 
        this.timestamp = timestamp; }

    public Long getUsuarioId() { 
        return usuarioId; }
        
    public void setUsuarioId(Long usuarioId) { 
        this.usuarioId = usuarioId; }
}