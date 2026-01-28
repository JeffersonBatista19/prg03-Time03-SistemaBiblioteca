package br.com.ifba.biblioteca.emprestimo.controller;

/**
 *
 * @author guilhermeAmedrado
 */
import br.com.ifba.biblioteca.cliente.controller.ClienteIController;
import br.com.ifba.biblioteca.cliente.entity.Cliente;
import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import br.com.ifba.biblioteca.emprestimo.service.EmprestimoIService;
import br.com.ifba.biblioteca.exemplar.controller.ExemplarIController;
import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class EmprestimoController implements EmprestimoIController {

    @Autowired
    private EmprestimoIService service;

    @Autowired
    private ClienteIController clienteController;

    @Autowired
    private ExemplarIController exemplarController;

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

    @Override
    public List<Cliente> findAllClientes() {
        return clienteController.findAll();
    }

    @Override
    public List<Exemplar> findAllExemplares() {
        return exemplarController.findAll();
    }
}