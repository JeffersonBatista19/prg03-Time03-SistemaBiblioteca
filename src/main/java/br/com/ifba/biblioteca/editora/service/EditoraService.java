package br.com.ifba.biblioteca.editora.service;

import br.com.ifba.biblioteca.editora.entity.Editora;
import br.com.ifba.biblioteca.editora.repository.EditoraRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author misae
 */


//Classe responsavel pelas regras de negócio da entidade Editora

@Slf4j //logs automáticos usando SLF4J
@Service //aqui define a classe como um service gerenciado pelo Spring
public class EditoraService implements EditoraIService {
    
    //responsável pelo acesso aos dados de editora
    private final EditoraRepository repository;
    
    // injeção de dependência pelo construtor
    public EditoraService(EditoraRepository repository) {
        this.repository = repository;
    }
    
    
    //cadastra nova editora
    @Override
    public Editora save(Editora editora){
        
        log.info("Iniciando cadastro de editora: {}", editora); 

        //validações:
        
        //editora não pode ser nula
        if (editora == null) {
            log.error("Tentativa de salvar editora nula.");
            throw new RuntimeException("Editora não pode ser nula!");
        }

        //CNPJ é obrigatório
        if (editora.getCnpj() == null || editora.getCnpj().isBlank()) {
            log.warn("Editora sem CNPJ.");
            throw new RuntimeException("CNPJ da editora é obrigatório!");
        }

        //nome obrigatório
        if (editora.getNome() == null || editora.getNome().isBlank()) {
            log.warn("Editora sem nome.");
            throw new RuntimeException("Nome da editora é obrigatório!");
        }

        //telefone obrigatório
        if (editora.getTelefone() == null || editora.getTelefone().isBlank()) {
            log.warn("Editora sem telefone.");
            throw new RuntimeException("Telefone da editora é obrigatório!");
        }

        //endereço obrigatório
        if (editora.getEndereco() == null || editora.getEndereco().isBlank()) {
            log.warn("Editora sem endereço.");
            throw new RuntimeException("Endereço da editora é obrigatório!");
        }

        // aqui regra de negócio: não permitir CNPJ duplicado (bastante importante)
        if (repository.existsByCnpj(editora.getCnpj())) {
            log.warn("Tentativa de cadastro com CNPJ já existente: {}", editora.getCnpj());
            throw new RuntimeException("Já existe uma editora cadastrada com este CNPJ!");
        }

        //outra regra de negócio: editora já inicia como ativa
        editora.setSituacao("ATIVO");

        log.info("Editora cadastrada com sucesso!");
        return repository.save(editora);
    }
    
    // atualiza os dados permitidos de uma editora existente
    @Override
    public Editora update(Editora editora){
        log.info("Iniciando atualização da editora: {}", editora);

        // Validação: editora não pode ser nula
        if (editora == null) {
            log.error("Tentativa de atualizar editora nula.");
            throw new RuntimeException("Editora não pode ser nula!");
        }

        //ID obrigatório
        if (editora.getId() == null) {
            log.error("ID ausente ao tentar atualizar editora.");
            throw new RuntimeException("ID da editora é obrigatório!");
        }

        // faz a busca da editora no banco
        Editora editoraExistente = repository.findById(editora.getId())
                .orElseThrow(() -> {
                    log.warn("Editora não encontrada para atualização. ID: {}", editora.getId());
                    return new RuntimeException("Editora não encontrada!");
                });

        // atualiza campos permitidos
        editoraExistente.setNome(editora.getNome());
        editoraExistente.setTelefone(editora.getTelefone());
        editoraExistente.setEndereco(editora.getEndereco());

        log.info("Editora atualizada com sucesso!");
            return repository.save(editoraExistente);
        }
    
    
    
    //inativação lógica da editora
    @Override
    public void delete(Editora editora){
        
        log.info("Iniciando inativação da editora: {}", editora);

        //editora não pode ser nula
        if (editora == null) {
            log.error("Tentativa de inativar editora nula.");
            throw new RuntimeException("Editora não pode ser nula!");
        }

        //ID obrigatório
        if (editora.getId() == null) {
            log.error("ID ausente ao tentar inativar editora.");
            throw new RuntimeException("ID da editora é obrigatório!");
        }

        //faz a busca da editora no banco
        Editora editoraExistente = repository.findById(editora.getId())
                .orElseThrow(() -> {
                    log.warn("Editora não encontrada para inativação. ID: {}", editora.getId());
                    return new RuntimeException("Editora não encontrada!");
                });

        // aqui a inativação lógica (não deletado banco, apenas inativa)
        editoraExistente.setSituacao("INATIVO");
        repository.save(editoraExistente);

        log.info("Editora inativada com sucesso!");
    }
    
    //lista todas as editoras ativas
    @Override
    public List<Editora> findAll(){
        log.info("Listando todas as editoras ativas.");
        return repository.findBySituacao("ATIVO");
    }

    
    //busca editora pelo id
    @Override
    public Editora findById(Long id){
        log.info("Buscando editora por ID: {}", id);

        //ID não pode ser nulo
        if (id == null) {
            log.error("ID nulo ao buscar editora.");
            throw new RuntimeException("ID não pode ser nulo!");
        }

        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Editora não encontrada. ID: {}", id);
                    return new RuntimeException("Editora não encontrada!");
                });
    }
        
    //filtra editoras pelo nome
    @Override
    public List<Editora> filtrarPorNome(String nome){
        log.info("Filtrando editoras. Nome: {}", nome);

         if (nome == null || nome.isBlank()) {
            return findAll();
        }

        // Isso garante que vai buscar qualquer ocorrência, ignorando maiúsculas/minúsculas
        return repository.findByNomeContainingIgnoreCaseAndSituacao(nome, "ATIVO");
    }
    
    //aqui verifica se já existe uam editora com o CNPJ informado
    @Override
    public boolean verificarCnpjExistente(String cnpj){
        return repository.existsByCnpj(cnpj);
    }
    
    // lista todas as editoras ativas do sistema
    @Override
    public List<Editora> listarAtivas() {
        return repository.findBySituacao("ATIVO");
    }
        
    
}
