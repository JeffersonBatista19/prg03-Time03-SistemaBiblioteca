package br.com.ifba.biblioteca.emprestimo.controller;

/**
 *
 * @author guilhermeAmedrado
 */
import br.com.ifba.biblioteca.cliente.entity.Cliente;
import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import java.util.List;

public interface EmprestimoIController {
    Emprestimo save(Emprestimo emprestimo);
    Emprestimo update(Emprestimo emprestimo);
    void delete(Emprestimo emprestimo);
    List<Emprestimo> findAll();
    Emprestimo findById(Long id);
    List<Cliente> findAllClientes();
    List<Exemplar> findAllExemplares();
}
