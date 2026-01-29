package br.com.ifba.biblioteca.sugestaoaquisicao.service;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.sugestaoaquisicao.entity.SugestaoAquisicao;
import java.util.List;

public interface SugestaoAquisicaoIService {
    // Define as operações obrigatórias para o módulo de sugestões
    SugestaoAquisicao save(SugestaoAquisicao sugestao);
    SugestaoAquisicao update(SugestaoAquisicao sugestao);
    void delete(Long id);
    SugestaoAquisicao findById(Long id);
    List<SugestaoAquisicao> findAll();
    List<SugestaoAquisicao> findByTitulo(String titulo);
}
