package com.trokr.model;

import java.util.ArrayList;

import com.trokr.model.state.*;
import com.trokr.model.state.proposta.EstadoAtiva;
import com.trokr.model.state.proposta.EstadoCancelado;
import com.trokr.model.state.proposta.EstadoFinalizado;
import com.trokr.model.state.proposta.EstadoHomologacao;
import com.trokr.model.state.proposta.EstadoNegociado;
import com.trokr.model.state.proposta.EstadoProposta;
import com.trokr.model.state.proposta.EstadoRascunho;

import com.trokr.model.state.contraproposta.EstadoRascunhoContra;
import com.trokr.model.state.contraproposta.EstadoEmAnalise;
import com.trokr.model.state.contraproposta.EstadoRecusado;
import com.trokr.model.state.contraproposta.EstadoCanceladoContra;



import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "propostas")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proposta_anterior_id")
    private Proposta propostaAnterior;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; 

    @ManyToOne 
    @JoinColumn(name = "item_id", nullable = false)
    private Item item; 

    @OneToMany(mappedBy = "propostaAnterior", cascade = CascadeType.ALL)
    private List<Proposta> contrapropostas = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Transient
    private EstadoProposta estado;
   

    public Proposta() {
        this.status = Status.RASCUNHO;
        this.estado = new EstadoRascunho();
    }

public boolean Contraproposta() {
        return this.propostaAnterior != null;
    }

public Usuario getUsuario() {
    return usuario;
}

public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

public Item getItem() {
    return item;
}

public void setItem(Item item) {
    this.item = item;
}


    @PostLoad
        public void carregarEstado() {
            if (this.status == null) return;

            switch (this.status) {
                case RASCUNHO -> {
                    if (Contraproposta()) {
                        this.estado = new EstadoRascunhoContra();
                    } else {
                        this.estado = new EstadoRascunho();
                    }
                }
                case HOMOLOGACAO -> this.estado = new EstadoHomologacao();
                case ATIVA -> this.estado = new EstadoAtiva();
                case NEGOCIADO -> this.estado = new EstadoNegociado();
                case FINALIZADO -> this.estado = new EstadoFinalizado();
                case CANCELADO -> this.estado = new EstadoCancelado();

            
                case EM_ANALISE -> this.estado = new EstadoEmAnalise();
                case NEGOCIADO_CONTRA -> this.estado = new EstadoNegociado();
                case FINALIZADO_CONTRA -> this.estado = new EstadoFinalizado();
                case RECUSADO -> this.estado = new EstadoRecusado();
                case CANCELADO_CONTRA -> this.estado = new EstadoCanceladoContra();
            }
        }
    
    public void mudarEstadoPara(EstadoProposta novoEstado, Status novoStatus) {
        this.estado = novoEstado;
        this.status = novoStatus;
    }



    public void enviar(){
        this.estado.enviar(this);
    }

    public void solicitarHomologacao() {
        this.estado.solicitarHomologacao(this);
    }

    public void enviarParaHomologacao() {
        this.estado.enviarParaHomologacao(this);
    }

    public void recusarParaHomologacao() {
        this.estado.recusarParaHomologacao(this);
    }

    public void aceitar() {
        this.estado.aceitar(this);
    }

    public void cancelar() {
        this.estado.cancelar(this);
    }

    public void voltar() {
        this.estado.voltar(this);
    }

    public void aceitarContraproposta() {
        this.estado.aceitarContraproposta(this);
    }

    public void negociacaoFalhou() {
        this.estado.negociacaoFalhou(this);
    }

    public void finalizarAcordo() {
        this.estado.finalizarAcordo(this);
    }

    public Long getId() {
        return id;
    }

    public Proposta getPropostaAnterior() {
        return propostaAnterior;
    }

    public void setPropostaAnterior(Proposta propostaAnterior) {
        this.propostaAnterior = propostaAnterior;
    }

    public List<Proposta> getContrapropostas() {
        return contrapropostas;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public EstadoProposta getEstado() {
        return estado;
    }
}