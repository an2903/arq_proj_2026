package com.trokr.event;

import com.trokr.model.Item;
import com.trokr.model.Usuario;
import java.time.LocalDateTime;

public class TrocaConcluidaEvent {
    private final Long propostaId;
    private final Usuario usuarioA;
    private final Usuario usuarioB;
    private final Item itemA;
    private final Item itemB;
    private final LocalDateTime dataConclusao;

    public TrocaConcluidaEvent(Long propostaId, Usuario usuarioA, Usuario usuarioB, Item itemA, Item itemB, LocalDateTime dataConclusao) {
        this.propostaId = propostaId;
        this.usuarioA = usuarioA;
        this.usuarioB = usuarioB;
        this.itemA = itemA;
        this.itemB = itemB;
        this.dataConclusao = dataConclusao;
    }

    public Long getPropostaId(){
        return propostaId;
    }

    public Usuario getUsuarioA(){
        return usuarioA;
    }

    public Usuario getUsuarioB(){
        return usuarioB;
    }

    public Item getItemA(){
        return itemA;
    }

    public Item getItemB(){
        return itemB;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }
}