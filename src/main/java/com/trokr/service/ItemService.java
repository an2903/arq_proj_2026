package com.trokr.service;

import com.trokr.exception.ResourceNotFoundException;
import com.trokr.model.Item;
import com.trokr.model.Usuario;
import com.trokr.repository.ItemRepository;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UsuarioService usuarioService;

    public List<Item> listarTodos() {
        return itemRepository.findAll();
    }

    public Item buscarPorId(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com id " + id));
    }

    public Item criar(Item item, Long usuarioId) {
        Usuario dono = usuarioService.buscarPorId(usuarioId);
        item.setUsuarioProprietario(dono);
        return itemRepository.save(item);
    }

    public Item atualizar(Long id, Item dadosAtualizados, Long usuarioId) {
        Item itemExistente = buscarPorId(id);
        Usuario dono = usuarioService.buscarPorId(usuarioId);
        itemExistente.setTitulo(dadosAtualizados.getTitulo());
        itemExistente.setDescricao(dadosAtualizados.getDescricao());
        itemExistente.setUsuarioProprietario(dono);
        return itemRepository.save(itemExistente);
    }

    public void remover(Long id) {
        Item item = buscarPorId(id);
        itemRepository.delete(item);
    }

    public List<Item> buscarItensDoUsuario(Long usuarioId) {
        return itemRepository.findByUsuarioProprietarioId(usuarioId);
    }

    public List<Item> buscarPorTitulo(String titulo) {
        return itemRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Item> buscarPorDescricao(String descricao) {
        return itemRepository.findByDescricaoContainingIgnoreCase(descricao);
    }

    public List<Item> buscarItensMaisRecentes() {
        return itemRepository.findTop5ByOrderByDataCriacaoDesc();
    }

    public List<Item> buscarItensPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return itemRepository.findByDataCriacaoBetween(inicio, fim);
    }
}
