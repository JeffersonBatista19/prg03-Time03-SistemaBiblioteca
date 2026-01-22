package br.com.ifba.biblioteca.emprestimo.entity;

/**
 *
 * @author guilhermeAmedrado
 */
import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import br.com.ifba.biblioteca.usuario.entity.Usuario;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "emprestimos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Emprestimo extends PersistenceEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false) 
    private Usuario usuario; 

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exemplar_id", nullable = false)
    private Exemplar exemplar;

    @Column(name = "data_emprestimo", nullable = false)
    private LocalDate dataEmprestimo;

    @Column(name = "data_devolucao_prevista", nullable = false)
    private LocalDate dataDevolucaoPrevista;

    @Column(name = "data_devolucao_real")
    private LocalDate dataDevolucaoReal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_emprestimo", nullable = false)
    private StatusEmprestimo status;

    public enum StatusEmprestimo {
        ATIVO, CONCLUIDO, ATRASADO
    }
}