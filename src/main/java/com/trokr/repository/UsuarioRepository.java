package com.trokr.repository;

import com.trokr.model.Usuario;

import java.util.Optional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// Uso direto do Spring Data JPA, sem interface/abstração genérica de
// repositório por cima — não há necessidade disso ainda.
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail (String email);
    List<Usuario> findByNomeContainingIgnoreCase(String trecho);
    List<Usuario> findTop5ByOrderByDataCriacaoDesc();
}
