package br.com.ifba.biblioteca.multa.controller;

import br.com.ifba.biblioteca.multa.entity.Multa;
import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MultaIController {

    // CRUD básico.

    Multa save(Multa multa);

    Multa update(Multa multa);

    void delete(Long id);

    Optional<Multa> findById(Long id);

    List<Multa> findAll();

    // regras de negócios.
    Multa gerarMultaManual(
            Emprestimo emprestimo,
            int diasAtraso,
            BigDecimal valorPorDia
    );

    Multa gerarMultaAutomatica(Emprestimo emprestimo);

    Multa pagar(Long idMulta);

    Multa cancelar(Long idMulta);
}
