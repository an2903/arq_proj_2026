package com.trokr.listener;


import com.trokr.event.TrocaConcluidaEvent;
import com.trokr.model.Usuario;
import com.trokr.repository.UsuarioRepository;
import com.trokr.service.CalculadoraCreditoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component 
@RequiredArgsConstructor 
public class AtribuirCreditosListener {
     
    private final CalculadoraCreditoService calculadora;
    private final UsuarioRepository usuarioRepository;

    @EventListener 
    public void aoConcluirTroca(TrocaConcluidaEvent evento) {

        int creditosParaA = calculadora.calcular(evento.getItemA());
        Usuario usuarioA = evento.getUsuarioA();
        usuarioA.setSaldoCreditos(usuarioA.getSaldoCreditos() + creditosParaA);

        int creditosParaB = calculadora.calcular(evento.getItemB());
        Usuario usuarioB = evento.getUsuarioB();
        usuarioB.setSaldoCreditos(usuarioB.getSaldoCreditos() + creditosParaB);

        usuarioRepository.save(usuarioA);
        usuarioRepository.save(usuarioB);
    }
}
