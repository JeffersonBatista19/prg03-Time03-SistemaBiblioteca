/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.biblioteca.categoria.service;

import br.com.ifba.biblioteca.categoria.entity.Categoria;
import br.com.ifba.biblioteca.categoria.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author jeffe
 */

 @Service
public class CategoriaService implements CategoriaIService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Categoria save(Categoria categoria) {
        validar(categoria);

        // Verifica duplicidade globalmente (ativas e inativas)
        if (!repository.findByNomeIgnoreCase(categoria.getNome().trim()).isEmpty()) {
            throw new RuntimeException("Erro: Categoria '" + categoria.getNome() + "' já existe (pode estar inativa).");
        }

        categoria.setNome(categoria.getNome().trim());
        categoria.setAtivo(true);
        return repository.save(categoria);
    }

    @Override
    public Categoria update(Categoria categoria) {
        if (categoria.getId() == null) {
            throw new RuntimeException("Categoria sem ID para atualizar.");
        }

        validar(categoria);

        Categoria atual = findByIdAtiva(categoria.getId());
        String nomeNovo = categoria.getNome().trim();

        // VALIDACAO ROBUSTA: Verifica se existe QUALQUER categoria (ativa ou inativa) com mesmo nome e ID diferente
        List<Categoria> duplicadas = repository.findByNomeIgnoreCase(nomeNovo);
        
        for (Categoria c : duplicadas) {
            if (!c.getId().equals(categoria.getId())) {
                 throw new RuntimeException("Erro: Já existe outra categoria com o nome '" + nomeNovo + "' (pode estar inativa).");
            }
        }

        atual.setNome(nomeNovo);
        atual.setDescricao(categoria.getDescricao());
        return repository.save(atual);
    }

    @Override
    public void inativar(Long id) {
        Categoria cat = findByIdAtiva(id);
        cat.setAtivo(false);
        repository.save(cat);
    }

    @Override
    public List<Categoria> findAllAtivas() {
        return repository.findByAtivoTrue();
    }

    @Override
    public Categoria findByIdAtiva(Long id) {
        return repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada (ou inativa)."));
    }

    @Override
    public List<Categoria> findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return findAllAtivas();
        }
        return repository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome.trim());
    }

    private void validar(Categoria categoria) {
        if (categoria == null) throw new RuntimeException("Categoria inválida.");
        if (categoria.getNome() == null || categoria.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome da categoria é obrigatório.");
        }
    }
}
