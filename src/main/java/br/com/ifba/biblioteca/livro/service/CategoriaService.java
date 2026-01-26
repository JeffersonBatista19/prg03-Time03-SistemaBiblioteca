/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.biblioteca.livro.service;

import br.com.ifba.biblioteca.livro.entity.Categoria;
import br.com.ifba.biblioteca.livro.repository.CategoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jeffe
 */

@Service
public class CategoriaService implements CategoriaIService {

    @Autowired
    private CategoriaRepository repository;

    @Override
    public Categoria save(Categoria categoria) {
        validarCategoria(categoria);

        if (repository.existsByNomeIgnoreCase(categoria.getNome())) {
            throw new RuntimeException("Já existe uma categoria com esse nome.");
        }

        categoria.setAtivo(true);
        return repository.save(categoria);
    }

    @Override
    public Categoria update(Categoria categoria) {
        if (categoria.getId() == null) {
            throw new RuntimeException("Categoria não identificada para atualização.");
        }

        validarCategoria(categoria);

        Categoria atual = findByIdAtivo(categoria.getId());

        // Se mudou o nome, impedir colisão com outro registro
        String nomeNovo = categoria.getNome().trim();
        if (!atual.getNome().equalsIgnoreCase(nomeNovo) && repository.existsByNomeIgnoreCase(nomeNovo)) {
            throw new RuntimeException("Já existe uma categoria com esse nome.");
        }

        atual.setNome(nomeNovo);
        atual.setDescricao(categoria.getDescricao());
        return repository.save(atual);
    }

    @Override
    public void inativar(Long id) {
        Categoria cat = findByIdAtivo(id);
        cat.setAtivo(false);
        repository.save(cat);
    }

    @Override
    public Categoria findByIdAtivo(Long id) {
        return repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada (ou está inativa)."));
    }

    @Override
    public List<Categoria> findAllAtivas() {
        return repository.findByAtivoTrue();
    }

    @Override
    public List<Categoria> findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return findAllAtivas();
        }
        return repository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome.trim());
    }

    private void validarCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new RuntimeException("Categoria inválida.");
        }
        if (categoria.getNome() == null || categoria.getNome().trim().isEmpty()) {
            throw new RuntimeException("O nome da categoria é obrigatório.");
        }
    }
}
