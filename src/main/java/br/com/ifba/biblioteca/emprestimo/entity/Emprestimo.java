package br.com.ifba.biblioteca.emprestimo.entity;

/**
 *
 * @author guilhermeAmedrado
 */
import br.com.ifba.biblioteca.cliente.entity.Cliente;
import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import br.com.ifba.biblioteca.multa.entity.Multa;
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
    @JoinColumn(name = "cliente_id", nullable = false) 
    private Cliente cliente; 



    // Campo redundante para satisfazer restrição 
    //Clientes_id e usuario_id eram campos obrigatorios, o codigo envia apenas uma por vez, então sempre dava erro
    //Entao foi criado esse campo para satisfazer a restrição 
    @Column(name = "usuario_id", nullable = true)
    private Long idUsuarioRedundante;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exemplar_id", nullable = false)
    private Exemplar exemplar;

    @Column(name = "data_emprestimo", nullable = false)
    private LocalDate dataEmprestimo;

    @Column(name = "data_devolucao_prevista", nullable = false)
    private LocalDate dataPrevistaDevolucao;

    // Campo redundante para satisfazer restrição 
    //As datas previstas de devolução eram obrigatórias, o codigo envia apenas uma por vez, então sempre dava erro
    //Entao foi criado esse campo para satisfazer a restrição e obrigar a envio de ambas as datas
    @Column(name = "data_prevista_devolucao", nullable = true)
    private LocalDate dataPrevistaDevolucaoRedundante;

    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;

    @OneToOne(mappedBy = "emprestimo", cascade = CascadeType.ALL)
    private Multa multa;


    @Enumerated(EnumType.STRING)
    @Column(name = "status_emprestimo", nullable = false)
    private StatusEmprestimo status;

    public enum StatusEmprestimo {
        ATIVO, CONCLUIDO, ATRASADO
    }
}