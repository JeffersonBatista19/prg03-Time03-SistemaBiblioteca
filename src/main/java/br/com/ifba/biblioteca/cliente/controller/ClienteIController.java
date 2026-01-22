package br.com.ifba.biblioteca.cliente.controller;

import br.com.ifba.biblioteca.cliente.entity.Cliente;
import java.util.List;

/**
 *
 * @author misae
 */

// Interface da camada Controller para a entidade Cliente
public interface ClienteIController {
    
    // criar cliente
    Cliente save(Cliente cliente);

    // atualizar cliente
    Cliente update(Cliente cliente);

    // remover (ou seja, Inativar) cliente
    void delete(Cliente cliente);

    // listar todos os clientes
    List<Cliente> findAll();

    // buscar cliente por ID
    Cliente findById(Long id);
    
    // filtra clientes com base em nome, tipo e status
    List<Cliente> filtrar(String nome, String tipo, String status);
    
    // verifica se já existe um cliente com o CPF informado
    public boolean verificarCpfExistente(String cpf);
    
    
}
