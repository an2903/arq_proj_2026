package com.trokr.listener;

import com.trokr.event.TrocaConcluidaEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificarUsuarios {

    @EventListener
    public void aoConcluirTroca(TrocaConcluidaEvent evento) {
        System.out.println("=============================================");
        System.out.println("[NOTIFICAÇÃO] Troca concluída com sucesso!");
        System.out.println("De: " + evento.getUsuarioA().getNome() + " (Item: " + evento.getItemA().getTitulo() + ")");
        System.out.println("Para: " + evento.getUsuarioB().getNome() + " (Item: " + evento.getItemB().getTitulo() + ")");
        System.out.println("=============================================");
    }
}