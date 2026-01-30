package br.com.ifba.biblioteca.multa.controller;

import br.com.ifba.biblioteca.multa.entity.Multa;
import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import br.com.ifba.biblioteca.multa.service.MultaIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@Scope("prototype")
public class MultaController implements MultaIController {

    @Autowired
    private MultaIService multaService;

    // CRUD básico.

    @Override
    public Multa save(Multa multa) {
        return multaService.save(multa);
    }

    @Override
    public Multa update(Multa multa) {
        return multaService.update(multa);
    }

    @Override
    public void delete(Long id) {
        multaService.delete(id);
    }

    @Override
    public Optional<Multa> findById(Long id) {
        return multaService.findById(id);
    }

    @Override
    public List<Multa> findAll() {
        return multaService.findAll();
    }

    // regras de negócios.

    @Override
    public Multa gerarMultaManual(Emprestimo emprestimo, int diasAtraso, BigDecimal valorPorDia) {
        return multaService.gerarMultaManual(emprestimo, diasAtraso, valorPorDia);
    }

    @Override
    public Multa gerarMultaAutomatica(Emprestimo emprestimo) {
        return multaService.gerarMultaAutomatica(emprestimo);
    }

    @Override
    public Multa pagar(Long idMulta) {
        return multaService.pagar(idMulta);
    }

    @Override
    public Multa cancelar(Long idMulta) {
        return multaService.cancelar(idMulta);
    }
}
