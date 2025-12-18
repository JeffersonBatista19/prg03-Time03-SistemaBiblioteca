package br.com.ifba.biblioteca.emprestimo.controller;

/**
 *
 * @author guilhermeAmedrado
 */
import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import br.com.ifba.biblioteca.emprestimo.service.EmprestimoIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class EmprestimoController implements EmprestimoIController {

    @Autowired
    private EmprestimoIService service;

    @Override
    public Emprestimo save(Emprestimo emprestimo) {
        return service.save(emprestimo);
    }

    @Override
    public Emprestimo update(Emprestimo emprestimo) {
        return service.update(emprestimo);
    }

    @Override
    public void delete(Emprestimo emprestimo) {
        service.delete(emprestimo);
    }

    @Override
    public List<Emprestimo> findAll() {
        return service.findAll();
    }

    @Override
    public Emprestimo findById(Long id) {
        return service.findById(id);
    }
}