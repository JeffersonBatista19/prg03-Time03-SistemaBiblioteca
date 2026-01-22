package br.com.ifba.biblioteca.cliente.service;

import br.com.ifba.biblioteca.cliente.entity.Cliente;
import java.util.List;

/**
 *
 * @author misae
 */

// Interface da camada Service para essa entidade Cliente
// define os métodos que implementam as regras de negócio relacionadas ao Cliente
public interface ClienteIService {
    
    //salvar um novo cliente
    Cliente save(Cliente cliente);
    
    //atualizar um cliente existente
    Cliente update(Cliente cliente);
    
    //remover, ou seja, inativar um cliente
    void delete(Cliente cliente);
    
    //listar todos os clientes
    List<Cliente> findAll();
    
    //fazer a buscar do cliente pelo ID
    Cliente findById(Long id);
    
    // filtra clientes com base em nome, tipo e status
    List<Cliente> filtrar(String nome, String tipo, String status);
    
    // verifica se já existe um cliente com o CPF informado
    boolean verificarCpfExistente(String cpf);
}
