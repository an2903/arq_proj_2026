package com.trokr.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "avaliacoes")
@Data
@NoArgsConstructor
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "avaliador_id", nullable = false)
    private Usuario avaliador;

    @ManyToOne
    @JoinColumn(name = "avaliado_id", nullable = false)
    private Usuario avaliado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAvaliacao status = StatusAvaliacao.PENDENTE;


    private Integer nota;
    private String descricao;
}