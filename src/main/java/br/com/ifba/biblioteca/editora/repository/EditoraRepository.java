package br.com.ifba.biblioteca.editora.repository;

import br.com.ifba.biblioteca.editora.entity.Editora;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author misae
 */

@Repository
public interface EditoraRepository extends JpaRepository<Editora, Long> {
    
    
    //método para listar somente as editoras ativas
    List<Editora> findBySituacao(String situacao);
    
    //método para verificar se já existe uma editora com o CNPJ informado
    boolean existsByCnpj(String cnpj);
    
    // método que filtra editoras pelo nome (ignorando maiúsculas/minúsculas) e pela situação
    List<Editora> findByNomeContainingIgnoreCaseAndSituacao(String nome, String situacao);
    
    
}
