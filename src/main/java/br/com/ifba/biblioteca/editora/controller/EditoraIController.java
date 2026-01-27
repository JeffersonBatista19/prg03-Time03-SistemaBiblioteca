package br.com.ifba.biblioteca.editora.controller;

import br.com.ifba.biblioteca.editora.entity.Editora;
import java.util.List;

/**
 *
 * @author misae
 */

// interface da camada Controller para a entidade Editora
public interface EditoraIController {
    
    //criar editora
    Editora save(Editora editora);
    
    //atualiza editora
    Editora update(Editora editora);
       
    //esse lista somente as editoras ativas
    List<Editora> listarAtivas();
    
    //faz a busca da editora pelo id
    Editora findById(Long id);
    
    //filtra editoras com base no nome
    List<Editora> filtrarPorNome(String nome);
    
    // verifica se já existe uma editora com o CNPJ informado
    boolean verificarCnpjExistente(String cnpj);
    
    //inativa logicamente uma editora
    void inativar(Long id);
         
}
