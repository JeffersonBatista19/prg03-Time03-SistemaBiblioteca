/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.biblioteca.autor.service;

import br.com.ifba.biblioteca.autor.entity.Autor;
import br.com.ifba.biblioteca.autor.repository.AutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author guilherme
 */

@Service // Indica que essa classe contém a lógica e as regras do sistema
public class AutorService implements AutorIService {

    @Autowired // Conecta automaticamente com o repositório (banco de dados)
    private AutorRepository autorRepository;

    @Override
    public Autor save(Autor autor) {
        // Regra: Verifica se já existe um autor com esse mesmo nome
        if (autorRepository.existsByNome(autor.getNome())) {
            throw new RuntimeException("Erro: Autor já existente no acervo.");
        }
        // Se não existir, salva no banco
        return autorRepository.save(autor);
    }

    @Override
    public Autor update(Autor autor) {
        // Verifica se o autor existe no banco antes de tentar atualizar
        if (autor.getId() == null || !autorRepository.existsById(autor.getId())) {
             throw new EntityNotFoundException("Autor não encontrado.");
        }
        // Atualiza as informações
        return autorRepository.save(autor);
    }

    @Override
    public void delete(Long id) {
        // Busca o autor pelo ID para conferir os dados
        Autor autor = findById(id);

        // Regra: Não deixa apagar se o autor tiver livros cadastrados
        if (autor.getQuantidadeObras() > 0) {
            throw new RuntimeException("Erro: Exclusão não permitida. O autor está vinculado a obras.");
        }

        // Se passou no teste, apaga do banco
        autorRepository.delete(autor);
    }

    @Override
    public List<Autor> findAll() {
        // Retorna a lista completa de autores
        return autorRepository.findAll();
    }

    @Override
    public Autor findById(Long id) {
        // Busca um autor pelo ID, se não achar lança um erro
        return autorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado."));
    }

    @Override
    public List<Autor> findByNome(String nome) {
        // Busca autores que tenham essa palavra no nome (ignorando maiúsculas)
        return autorRepository.findByNomeContainingIgnoreCase(nome);
    }
}