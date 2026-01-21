package br.com.ifba.biblioteca.cliente.service;

import br.com.ifba.biblioteca.cliente.entity.Cliente;
import br.com.ifba.biblioteca.cliente.entity.TipoCliente;
import br.com.ifba.biblioteca.cliente.repository.ClienteRepository;
import br.com.ifba.biblioteca.pessoa.entity.NivelAcesso;
import br.com.ifba.biblioteca.pessoa.entity.StatusPessoa;
import br.com.ifba.biblioteca.pessoa.entity.TipoPerfil;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



/**
 *
 * @author misae
 */


// Classe responsável pelas regras de negócio da entidade Cliente
@Slf4j // Habilita logs automáticos usando SLF4J
@Service // Define esta classe como um Service gerenciado pelo Spring
public class ClienteService implements ClienteIService{
    
    // Repositório responsável pelo acesso aos dados de Cliente
    private final ClienteRepository repository;
    
    //aqui o construtor com injeção de dependência do Repository
    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }
    
    
    // Cria/Cadastra novo cliente
    @Override
    public Cliente save(Cliente cliente){
        
        log.info("Iniciando processo de cadastro de cliente: {}", cliente);

        // Validação: cliente não pode ser nulo
        if (cliente == null) {
          log.error("Tentativa de salvar cliente nulo.");
          throw new RuntimeException("Cliente não pode ser nulo!");
        }

        // Validação: CPF obrigatório
        if (cliente.getCpf() == null || cliente.getCpf().isBlank()) {
            log.warn("Cliente sem CPF.");
            throw new RuntimeException("CPF do cliente é obrigatório!");
        }

        // Validação: nome obrigatório
        if (cliente.getNomeCompleto() == null || cliente.getNomeCompleto().isBlank()) {
            log.warn("Cliente sem nome.");
            throw new RuntimeException("Nome do cliente é obrigatório!");
        }

        // Validação: telefone obrigatório
        if (cliente.getTelefone() == null || cliente.getTelefone().isBlank()) {
            log.warn("Cliente sem telefone.");
            throw new RuntimeException("Telefone do cliente é obrigatório!");
        }

        // Validação: limite de empréstimos obrigatório
        if (cliente.getLimiteEmprestimo() == null || cliente.getLimiteEmprestimo() <= 0) {
            log.warn("Cliente sem limite de empréstimos válido.");
            throw new RuntimeException("Limite de Empréstimos é obrigatório e deve ser maior que zero.");
        }

        // Regras de negócio:
        
        // Define data de cadastro automaticamente
        cliente.setDataCadastro(LocalDate.now());

        // Regra de negócio: cliente inicia como ATIVO
        cliente.setStatusPessoa(StatusPessoa.ATIVO);

        // Cliente tem acesso RESTRITO ao sistema
        cliente.setNivelAcesso(NivelAcesso.RESTRITO);
        
        cliente.setTipoPerfil(TipoPerfil.CLIENTE); // perfil cliente

        
        log.info("Cliente cadastrado com sucesso!");
        
        return repository.save(cliente);
    }
        
    
    // Atualiza um cliente existente, ou seja, edita
    @Override
    public Cliente update(Cliente cliente) {

        log.info("Iniciando atualização do cliente: {}", cliente);

        // Validação: cliente não pode ser nulo
        if (cliente == null) {
            log.error("Tentativa de atualizar cliente nulo.");
            throw new RuntimeException("Cliente não pode ser nulo!");
        }

        // Validação: ID obrigatório
        if (cliente.getId() == null) {
            log.error("ID ausente ao tentar atualizar cliente.");
            throw new RuntimeException("ID do cliente é obrigatório!");
        }

        // Verifica se o cliente existe
        Cliente clienteExistente = repository.findById(cliente.getId())
                .orElseThrow(() -> {
                    log.warn("Cliente não encontrado para atualização. ID: {}", cliente.getId());
                    return new RuntimeException("Cliente não encontrado!");
                });
        
        // Campos permitidos para edição
        clienteExistente.setNomeCompleto(cliente.getNomeCompleto());
        clienteExistente.setTelefone(cliente.getTelefone());
        clienteExistente.setTipoCliente(cliente.getTipoCliente());
        clienteExistente.setLimiteEmprestimo(cliente.getLimiteEmprestimo());
        clienteExistente.setStatusPessoa(cliente.getStatusPessoa());  // Aqui é onde o status deve ser atualizado


        log.info("Cliente atualizado com sucesso!");
        return repository.save(clienteExistente);
    }

        
    // Inativar cliente (delete lógico), obs: não deleta o cliente, apenas inativa, preservando historico
    @Override
    public void delete(Cliente cliente) {

        log.info("Iniciando inativação do cliente: {}", cliente);

        // Validação: cliente não pode ser nulo
        if (cliente == null) {
            log.error("Tentativa de inativar cliente nulo.");
            throw new RuntimeException("Cliente não pode ser nulo!");
        }

        // Validação: ID obrigatório
        if (cliente.getId() == null) {
            log.error("ID ausente ao tentar inativar cliente.");
            throw new RuntimeException("ID do cliente é obrigatório!");
        }

        // Busca cliente no banco
        Cliente clienteExistente = repository.findById(cliente.getId())
                .orElseThrow(() -> {
                    log.warn("Cliente não encontrado para inativação. ID: {}", cliente.getId());
                    return new RuntimeException("Cliente não encontrado!");
                });

        // Regra de negócio: inativação lógica
        clienteExistente.setStatusPessoa(StatusPessoa.INATIVO);
        repository.save(clienteExistente);

        log.info("Cliente inativado com sucesso!");
    }
    
    // faz a listagem de todos os clientes
    @Override
    public List<Cliente> findAll() {

        log.info("Listando todos os clientes.");
        return repository.findAll();
    }
    
    
    // faz a busca do cliente por ID
    @Override
    public Cliente findById(Long id) {

        log.info("Buscando cliente por ID: {}", id);

        // Validação: ID não pode ser nulo
        if (id == null) {
            log.error("ID nulo ao buscar cliente.");
            throw new RuntimeException("ID não pode ser nulo!");
        }

        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cliente não encontrado. ID: {}", id);
                    return new RuntimeException("Cliente não encontrado!");
                });
    }
    
    
    //filtra clientes com base nos parâmetros informados.
    public List<Cliente> filtrar(String nome, String tipo, String status) {
        
    //Se algum parâmetro for nulo ou vazio, ele é ignorado
    List<Cliente> clientes = repository.findAll();

    //nome: busca parcial pelo nome completo (case-insensitive)
    if (nome != null && !nome.isBlank()) {
        clientes = clientes.stream()
                .filter(c -> c.getNomeCompleto() != null &&
                             c.getNomeCompleto().toLowerCase().contains(nome.toLowerCase()))
                .toList();
    }

    //tipo: filtra por TipoCliente (ALUNO, PROFESSOR, VISITANTE)
    if (tipo != null && !tipo.isBlank()) {
        TipoCliente tipoCliente = TipoCliente.valueOf(tipo);
        clientes = clientes.stream()
                .filter(c -> c.getTipoCliente() == tipoCliente)
                .toList();
    }

    //status: filtra por StatusPessoa (ATIVO, INATIVO, BLOQUEADO)
    if (status != null && !status.isBlank()) {
        StatusPessoa statusEnum = StatusPessoa.valueOf(status);
        clientes = clientes.stream()
                .filter(c -> c.getStatusPessoa() == statusEnum)
                .toList();
    }

    return clientes;
    }
    
    //Verifica se já existe um cliente com o CPF informado.
    @Override
    public boolean verificarCpfExistente(String cpf) {
        return repository.existsByCpf(cpf);
    }

    
}
