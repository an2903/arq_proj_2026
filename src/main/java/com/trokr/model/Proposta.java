package com.trokr.model;

import java.util.ArrayList;
import java.util.List;

import com.trokr.model.state.Status;
import com.trokr.model.state.contraproposta.EstadoCanceladoContra;
import com.trokr.model.state.contraproposta.EstadoEmAnalise;
import com.trokr.model.state.contraproposta.EstadoFinalizadoContra;
import com.trokr.model.state.contraproposta.EstadoNegociadoContra;
import com.trokr.model.state.contraproposta.EstadoRascunhoContra;
import com.trokr.model.state.contraproposta.EstadoRecusado;
import com.trokr.model.state.proposta.EstadoAtiva;
import com.trokr.model.state.proposta.EstadoCancelado;
import com.trokr.model.state.proposta.EstadoFinalizado;
import com.trokr.model.state.proposta.EstadoHomologacao;
import com.trokr.model.state.proposta.EstadoNegociado;
import com.trokr.model.state.proposta.EstadoProposta;
import com.trokr.model.state.proposta.EstadoRascunho;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


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

    @Column(length=  500)
    private String mensagem;

   
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

public String getMensagem() {
        return mensagem;
    }

public void setMensagem(String mensagem) {
    this.mensagem =mensagem;
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
                case NEGOCIADO_CONTRA -> this.estado = new EstadoNegociadoContra();
                case FINALIZADO_CONTRA -> this.estado = new EstadoFinalizadoContra();
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

    public void recusar() {
        this.estado.recusar(this);

        if(this.contrapropostas != null) {
            for (Proposta contra : this.contrapropostas) {
                contra.recusar(); 
                if (contra.getStatus() != Status.RECUSADO) {
                    contra.recusar();
                }
            }
        }
         if (this.propostaAnterior != null) {
            if (this.propostaAnterior.getStatus() != Status.ATIVA) {
                this.propostaAnterior.recusar();
            }
         }
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

        if (this.contrapropostas != null) {
            for (Proposta contra : this.contrapropostas) {
                contra.aceitar(); 
            }
        }
        
        if (this.propostaAnterior != null) {
            this.propostaAnterior.aceitarContraproposta();
        }
    }

    public void negociacaoFalhou() {
        this.estado.negociacaoFalhou(this);
    }

    public void finalizarAcordo() {
        this.estado.finalizarAcordo(this);

        if (this.contrapropostas != null) {
            for (Proposta contra : this.contrapropostas) {
                contra.finalizarAcordo();
            }
        }
        
        if (this.propostaAnterior != null) {
            this.propostaAnterior.finalizarAcordo();
        }
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