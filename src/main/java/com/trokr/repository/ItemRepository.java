package com.trokr.repository;

import com.trokr.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByDescricaoContaining (String Descricao);
    Optional<Item> findByTituloContaining (String Titulo);

    List<Item> findByUsuarioProprietarioId(Long usuarioId);
    List<Item> findByTituloContainingIgnoreCase(String titulo);
    List<Item> findByDescricaoContainingIgnoreCase(String Descricao);
    List<Item> findTop5ByOrderByDataCriacaoDesc();
    List<Item> findByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim);

}
