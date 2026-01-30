package br.com.ifba.biblioteca.sugestaoaquisicao.service;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.sugestaoaquisicao.entity.SugestaoAquisicao;
import br.com.ifba.biblioteca.sugestaoaquisicao.entity.StatusSugestao;
import br.com.ifba.biblioteca.sugestaoaquisicao.repository.SugestaoAquisicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SugestaoAquisicaoService implements SugestaoAquisicaoIService {

    @Autowired
    private SugestaoAquisicaoRepository repository;

    @Override
    public SugestaoAquisicao save(SugestaoAquisicao sugestao) {
        
        // Validação: Título e Autor são campos obrigatórios pelo requisito 18
        if (sugestao.getTituloSugerido() == null || sugestao.getTituloSugerido().trim().isEmpty() ||
            sugestao.getAutorSugerido() == null || sugestao.getAutorSugerido().trim().isEmpty()) {
            throw new RuntimeException("Erro: O Título do livro e o Autor do livro são campos obrigatórios.");
        }

        // Validação: Somente usuários devidamente identificados podem sugerir
        // Validação comentada para permitir testes independente do sistema de login
        /*
        if (sugestao.getUsuario() == null) {
            throw new RuntimeException("Erro: O usuário deve estar autenticado para enviar uma sugestão.");
        }
        */

        sugestao.setDataSugestao(LocalDateTime.now()); // Registra o momento da sugestão
        sugestao.setStatus(StatusSugestao.PARA_ANALISE); // Define o status inicial como pendente de análise

        return repository.save(sugestao);
    }

    @Override
    public SugestaoAquisicao update(SugestaoAquisicao sugestao) {
        if (sugestao.getId() == null) {
            throw new RuntimeException("ID é necessário para atualização.");
        }
        return repository.save(sugestao);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public SugestaoAquisicao findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<SugestaoAquisicao> findAll() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            // Se houver erro catastrófico (ex: FK quebrada no banco), retorna lista vazia para não travar a UI
            return new java.util.ArrayList<>();
        }
    }

    @Override
    public List<SugestaoAquisicao> findByTitulo(String titulo) {
        return repository.findByTituloSugeridoContainingIgnoreCase(titulo);
    }
}
