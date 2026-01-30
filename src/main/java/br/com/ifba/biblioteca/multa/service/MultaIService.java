package br.com.ifba.biblioteca.multa.service;

import br.com.ifba.biblioteca.multa.entity.Multa;
import br.com.ifba.biblioteca.multa.entity.StatusMulta;
import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MultaIService {

   
    // geração de multa.
    // Geração automática (por atraso)
    Multa gerarMultaAutomatica(Emprestimo emprestimo);

    // Geração manual (tela MultaGerar)
    Multa gerarMultaManual(
            Emprestimo emprestimo,
            int diasAtraso,
            java.math.BigDecimal valorPorDia
    );

  
    // operações básicas.
    Multa save(Multa multa);

    Multa update(Multa multa);

    void delete(Long id);

    
    // estados da multa.
    Multa pagar(Long idMulta);

    Multa cancelar(Long idMulta);

    // buscas.
    Optional<Multa> findById(Long id);

    Optional<Multa> findByEmprestimo(Emprestimo emprestimo);

    List<Multa> findAll();

    
    // filtros.
    List<Multa> findByStatus(StatusMulta status);

    List<Multa> findByPeriodo(LocalDate inicio, LocalDate fim);

    List<Multa> findByCliente(String nomeCliente);

    List<Multa> findByLivro(String tituloLivro);
}
