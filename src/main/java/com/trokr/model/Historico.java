package com.trokr.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_propostas")
@Data
@NoArgsConstructor
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    @Column(name = "proposta_id", nullable = false)
    private Long propostaId;

    @Column(name = "status_anterior")
    private String statusAnterior;

    @Column(name = "status_novo", nullable = false)
    private String statusNovo;

    @Column(name = "data_alteracao", nullable = false)
    private LocalDateTime dataAlteracao;
}