/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.biblioteca.local.service;

import br.com.ifba.biblioteca.local.entity.Local;
import br.com.ifba.biblioteca.local.repository.LocalRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author jeffe
 */

@Service

public class LocalService implements LocalIService {

    private final LocalRepository repository;

    public LocalService(LocalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Local save(Local local) {
        validar(local);

        String cidade = local.getCidade().trim();
        String rua = local.getRua().trim();

        if (repository.existsByCidadeIgnoreCaseAndRuaIgnoreCaseAndAtivoTrue(cidade, rua)) {
            throw new RuntimeException("Erro: Local já existente (cidade e rua).");
        }

        local.setCidade(cidade);
        local.setRua(rua);
        local.setAtivo(true);

        return repository.save(local);
    }

    @Override
    public Local update(Local local) {
        if (local.getId() == null) {
            throw new RuntimeException("Local sem ID para atualizar.");
        }

        validar(local);

        Local atual = findByIdAtivo(local.getId());

        String cidadeNova = local.getCidade().trim();
        String ruaNova = local.getRua().trim();

        boolean mudouCidade = !atual.getCidade().equalsIgnoreCase(cidadeNova);
        boolean mudouRua = !atual.getRua().equalsIgnoreCase(ruaNova);

        if ((mudouCidade || mudouRua)
                && repository.existsByCidadeIgnoreCaseAndRuaIgnoreCaseAndAtivoTrue(cidadeNova, ruaNova)) {
            throw new RuntimeException("Erro: Já existe outro local com essa cidade e rua.");
        }

        atual.setCidade(cidadeNova);
        atual.setRua(ruaNova);
        atual.setNumero(local.getNumero());
        atual.setComplemento(local.getComplemento());

        return repository.save(atual);
    }

    @Override
    public void inativar(Long id) {
        Local loc = findByIdAtivo(id);
        loc.setAtivo(false);
        repository.save(loc);
    }

    @Override
    public List<Local> findAllAtivos() {
        return repository.findByAtivoTrue();
    }

    @Override
    public Local findByIdAtivo(Long id) {
        return repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Local não encontrado (ou inativo)."));
    }

    @Override
    public List<Local> findByCidadeAndRua(String cidade, String rua) {
        boolean cidadeVazia = (cidade == null || cidade.trim().isEmpty());
        boolean ruaVazia = (rua == null || rua.trim().isEmpty());

        if (cidadeVazia && ruaVazia) {
            return findAllAtivos();
        }

        String cidadeFiltro = cidadeVazia ? "" : cidade.trim();
        String ruaFiltro = ruaVazia ? "" : rua.trim();

        return repository.findByCidadeContainingIgnoreCaseAndRuaContainingIgnoreCaseAndAtivoTrue(
                cidadeFiltro, ruaFiltro
        );
    }

    private void validar(Local local) {
        if (local == null) {
            throw new RuntimeException("Local inválido.");
        }
        if (local.getCidade() == null || local.getCidade().trim().isEmpty()) {
            throw new RuntimeException("Cidade é obrigatória.");
        }
        if (local.getRua() == null || local.getRua().trim().isEmpty()) {
            throw new RuntimeException("Rua é obrigatória.");
        }
        if (local.getNumero() == null) {
            throw new RuntimeException("Número é obrigatório.");
        }
        if (local.getNumero() <= 0) {
            throw new RuntimeException("Número deve ser maior que zero.");
        }
        // complemento pode ser nulo/vazio, então não valida
    }
}
