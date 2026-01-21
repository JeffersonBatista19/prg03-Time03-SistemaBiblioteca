package br.com.ifba.biblioteca.cliente.controller;

import br.com.ifba.biblioteca.cliente.entity.Cliente;
import br.com.ifba.biblioteca.cliente.service.ClienteIService;
import br.com.ifba.biblioteca.pessoa.entity.StatusPessoa;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author misae
 */

//camada Controller, responsável pela comunicação entre a View e a camada Service do Cliente
@Controller
public class ClienteController implements ClienteIController{
    
    // Service responsável pelas regras de negócio do Cliente
    private final ClienteIService clienteService;
    
    // Construtor com injeção de dependência do Service
    public ClienteController(ClienteIService clienteService) {
        this.clienteService = clienteService;
    }

    // criar/cadastra novo cliente
    @Override
    public Cliente save(Cliente cliente) {
        return clienteService.save(cliente);
    }

    // atualizar um cliente existente
    @Override
    public Cliente update(Cliente cliente) {
        return clienteService.update(cliente);
    }

    // remover (inativar) cliente , obs: Não remove do banco, apenas altera o status para INATIVO.
    @Override
    public void delete(Cliente cliente) {
        cliente.setStatusPessoa(StatusPessoa.INATIVO);  // Inativa o cliente ao invés de excluir
        clienteService.update(cliente);
    }

    // listar todos os clientes cadastrados
    @Override
    public List<Cliente> findAll() {
        return clienteService.findAll();
    }

    // busca cliente por ID
    @Override
    public Cliente findById(Long id) {
        return clienteService.findById(id);
    }
    
    //filtra clientes com base em nome, tipo e status
    @Override
    public List<Cliente> filtrar(String nome, String tipo, String status) {
        System.out.println("Filtrando por status: " + status);  // Ou use logger.info
        return clienteService.filtrar(nome, tipo, status);
    }

    // Verifica se o CPF já está cadastrado no banco, evitando duplicidade
    @Override
    public boolean verificarCpfExistente(String cpf) {
        return clienteService.verificarCpfExistente(cpf);  // Chama o método no Service
    }
    
    
    
    
    
}
