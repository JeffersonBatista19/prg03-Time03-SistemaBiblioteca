package br.com.ifba.biblioteca.editora.controller;

import br.com.ifba.biblioteca.editora.entity.Editora;
import br.com.ifba.biblioteca.editora.service.EditoraIService;
import java.util.List;
import org.springframework.stereotype.Controller;


/**
 *
 * @author misae
 */

//camada Controller da editora
@Controller
public class EditoraController implements EditoraIController{
    
    //service responsavel pelas regras de negocio da editora
    private final EditoraIService editoraService;
    
    //aqui a injeção de dependência via contrutor
    public EditoraController(EditoraIService editoraService) {
        this.editoraService = editoraService;
    }
    
    
     //método para cadastrar nova editora
    @Override
    public Editora save(Editora editora) {
        return editoraService.save(editora);
    }
    
    //atualizar editora existente
    @Override
    public Editora update(Editora editora) {
        return editoraService.update(editora);
    }
    
    //inativa logicamente uma editora pelo ID
    @Override
    public void inativar(Long id) {
        // Busca a editora ou lança exceção se não existir
        Editora editora = findById(id);
    
        // Chama o service para inativação lógica
        editoraService.delete(editora); 
    }
    
     // listar somente editoras ativas
    @Override
    public List<Editora> listarAtivas() {
        return editoraService.listarAtivas();
    }
    
    //buscar editora por ID
    @Override
    public Editora findById(Long id) {
        return editoraService.findById(id);
    }
    

    @Override
    public List<Editora> filtrarPorNome(String nome) {
        return editoraService.filtrarPorNome(nome);
    }

    @Override
    public boolean verificarCnpjExistente(String cnpj) {
        return editoraService.verificarCnpjExistente(cnpj);
    }

    
}
    

