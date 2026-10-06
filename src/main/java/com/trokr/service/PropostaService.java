package com.trokr.service;

import com.trokr.dto.PropostaRequestDTO;
import com.trokr.model.Item;
import com.trokr.model.Proposta;
import com.trokr.model.Usuario;
import com.trokr.repository.PropostaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import com.trokr.event.TrocaConcluidaEvent;
import org.springframework.context.ApplicationEventPublisher;
import lombok.RequiredArgsConstructor;
import java.util.List;

/**
 * <<Facade>>
 * Este serviço atua como uma Facade no sistema Trokr.
 * Ele coordena o fluxo de finalizar a negociação conectando 3 peças principais:
 * 1. O Repositório (JPA)
 * 2. As Entidades e o State Pattern (para validar regras e mudar estados)
 * 3. O Event Publisher (Observer Pattern) para disparar notificações pós-troca.
 * 
 * Deliberadamente, nenhuma regra de transição de estado ou cálculo de créditos
 * foi escrita dentro deste serviço, mantendo-o apenas como um orquestrador.
 */
@Service
@RequiredArgsConstructor
public class PropostaService {

    private final PropostaRepository propostaRepository;
    private final UsuarioService usuarioService;
    private final ItemService itemService;
    private final ApplicationEventPublisher eventPublisher;

    public Proposta buscarPorId(Long id) {
        return propostaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proposta não encontrada com o ID: " + id));
    }

    public List<Proposta> listarTodas() {
        return propostaRepository.findAll();
    }

    @Transactional
    public Proposta criar(PropostaRequestDTO dto) {
        Usuario usuario = usuarioService.buscarPorId(dto.usuarioId());
        Item item = itemService.buscarPorId(dto.itemId());

        Proposta proposta = new Proposta();
        proposta.setUsuario(usuario);
        proposta.setItem(item);

        return propostaRepository.save(proposta);
    }

    @Transactional
    public Proposta criarContraproposta(Long propostaPaiId, PropostaRequestDTO dto) {
        Proposta propostaPai = buscarPorId(propostaPaiId);
        Usuario usuario = usuarioService.buscarPorId(dto.usuarioId());
        Item item = itemService.buscarPorId(dto.itemId());

        Proposta contraproposta = new Proposta();
        contraproposta.setPropostaAnterior(propostaPai);
        contraproposta.setUsuario(usuario);
        contraproposta.setItem(item);

        return propostaRepository.save(contraproposta);
    }

    @Transactional
    public Proposta solicitarHomologacao(Long id) {
        Proposta proposta = buscarPorId(id);
        proposta.solicitarHomologacao();
        return propostaRepository.save(proposta);
    }

    @Transactional
    public Proposta enviarParaHomologacao(Long id) {
        Proposta proposta = buscarPorId(id);
        proposta.enviarParaHomologacao();
        return propostaRepository.save(proposta);
    }

    @Transactional
    public Proposta enviar(Long id) {
        Proposta proposta = buscarPorId(id);
        proposta.enviar();
        return propostaRepository.save(proposta);
    }

    @Transactional
    public Proposta aceitar(Long id) {
        Proposta proposta = buscarPorId(id);
        proposta.aceitar();
        return propostaRepository.save(proposta);
    }

    @Transactional
    public Proposta aceitarContraproposta(Long id) {
        Proposta proposta = buscarPorId(id);
        proposta.aceitarContraproposta();
        return propostaRepository.save(proposta);
    }

    @Transactional
    public Proposta recusarParaHomologacao(Long id) {
        Proposta proposta = buscarPorId(id);
        proposta.recusarParaHomologacao();
        return propostaRepository.save(proposta);
    }

    @Transactional
    public Proposta cancelar(Long id) {
        Proposta proposta = buscarPorId(id);
        proposta.cancelar();
        return propostaRepository.save(proposta);
    }

    @Transactional
    public Proposta finalizarAcordo(Long id) {
        Proposta proposta = buscarPorId(id);
        proposta.finalizarAcordo();
        Proposta salva = propostaRepository.save(proposta);
        
        TrocaConcluidaEvent evento = construirEventoDeTrocaConcluida(salva);
        eventPublisher.publishEvent(evento);

        return salva;
    }

    private TrocaConcluidaEvent construirEventoDeTrocaConcluida(Proposta proposta) {
        if (proposta.getPropostaAnterior() != null) {
            Proposta raiz = proposta.getPropostaAnterior();
            return new TrocaConcluidaEvent(
                raiz.getId(), 
                raiz.getUsuario(), proposta.getUsuario(), 
                raiz.getItem(), proposta.getItem(), 
                LocalDateTime.now()
            );
        } else {
            Proposta contra = proposta.getContrapropostas().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Nenhuma contraproposta associada."));
            
            return new TrocaConcluidaEvent(
                proposta.getId(), 
                proposta.getUsuario(), contra.getUsuario(), 
                proposta.getItem(), contra.getItem(), 
                LocalDateTime.now()
            );
        }
    }
}