package com.trokr.controller;

import com.trokr.dto.ItemRequestDTO;
import com.trokr.dto.ItemResponseDTO;
import com.trokr.model.Item;
import com.trokr.service.ItemService;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itens")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemResponseDTO> listar() {
        return itemService.listarTodos().stream()
                .map(ItemResponseDTO::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ItemResponseDTO buscarPorId(@PathVariable Long id) {
        return ItemResponseDTO.fromEntity(itemService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ItemResponseDTO> criar(@Valid @RequestBody ItemRequestDTO dto) {
        Item item = new Item();
        item.setTitulo(dto.titulo());
        item.setDescricao(dto.descricao());

        Item salvo = itemService.criar(item, dto.usuarioId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ItemResponseDTO.fromEntity(salvo));
    }

    @PutMapping("/{id}")
    public ItemResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ItemRequestDTO dto) {
        Item dadosAtualizados = new Item();
        dadosAtualizados.setTitulo(dto.titulo());
        dadosAtualizados.setDescricao(dto.descricao());

        return ItemResponseDTO.fromEntity(itemService.atualizar(id, dadosAtualizados, dto.usuarioId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        itemService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Item>> listarItensDoUsuario(@PathVariable Long usuarioId) {
        List<Item> itens = itemService.buscarItensDoUsuario(usuarioId);
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/buscar-titulo")
    public ResponseEntity<List<Item>> buscarPorTitulo(@RequestParam String titulo) {
        List<Item> itens = itemService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/buscar-descricao")
    public ResponseEntity<List<Item>> buscarPorDescricao(@RequestParam String descricao) {
        List<Item> itens = itemService.buscarPorDescricao(descricao);
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/recentes")
    public ResponseEntity<List<Item>> listarRecentes() {
        List<Item> recentes = itemService.buscarItensMaisRecentes();
        return ResponseEntity.ok(recentes);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Item>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        
        List<Item> itens = itemService.buscarItensPorPeriodo(inicio, fim);
        return ResponseEntity.ok(itens);
    }
}
