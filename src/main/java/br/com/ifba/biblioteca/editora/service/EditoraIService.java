package br.com.ifba.biblioteca.editora.service;

import br.com.ifba.biblioteca.editora.entity.Editora;
import java.util.List;

/**
 *
 * @author misae
 */

public interface EditoraIService {
      
    Editora save(Editora editora);
    
    Editora update(Editora editora);
    
    //remove (inativa) uma editora, delete logico
    void delete(Editora editora);
    
    //lista todas as editoras ativas 
    List<Editora> findAll();
    
    Editora findById(Long id);
    
    //filtra editoras com base no nome
    List<Editora> filtrarPorNome(String nome);
    
    //verifica se já existe uma editora co o CNPJ informado
    boolean verificarCnpjExistente(String cnpj);
    
    // lista todas as editoras ativas do sistema
    List<Editora> listarAtivas();
    
}
