package br.com.ifba.biblioteca.cliente.repository;

import br.com.ifba.biblioteca.cliente.entity.Cliente;
import br.com.ifba.biblioteca.cliente.entity.TipoCliente;
import br.com.ifba.biblioteca.pessoa.entity.StatusPessoa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author misae
 */

// Repositório responsável pelo acesso aos dados da entidade Cliente
// Utiliza o Spring Data JPA para fornecer automaticamente as operações CRUD básicas.
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    // Método para buscar clientes por nome completo
    List<Cliente> findByNomeCompleto(String nomeCompleto);

    // Método para buscar clientes por tipo
    List<Cliente> findByTipoCliente(TipoCliente tipoCliente);

    // Método para buscar clientes por status
    List<Cliente> findByStatusPessoa(StatusPessoa statusPessoa);
    
    //método que verifica se já existe algum cliente com o CPF informado
    boolean existsByCpf(String cpf);
    

}
