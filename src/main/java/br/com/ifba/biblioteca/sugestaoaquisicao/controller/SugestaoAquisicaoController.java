package br.com.ifba.biblioteca.sugestaoaquisicao.controller;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.sugestaoaquisicao.entity.SugestaoAquisicao;
import br.com.ifba.biblioteca.sugestaoaquisicao.service.SugestaoAquisicaoIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class SugestaoAquisicaoController {

    @Autowired
    private SugestaoAquisicaoIService service;

    public SugestaoAquisicao save(SugestaoAquisicao sugestao) {
        return service.save(sugestao);
    }

    public SugestaoAquisicao update(SugestaoAquisicao sugestao) {
        return service.update(sugestao);
    }

    public void delete(Long id) {
        service.delete(id);
    }

    public SugestaoAquisicao findById(Long id) {
        return service.findById(id);
    }

    public List<SugestaoAquisicao> findAll() {
        return service.findAll(); // Busca todas as sugestões para a listagem
    }

    public List<SugestaoAquisicao> findByTitulo(String titulo) {
        return service.findByTitulo(titulo);
    }
}
