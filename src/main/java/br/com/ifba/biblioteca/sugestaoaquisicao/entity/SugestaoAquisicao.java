package br.com.ifba.biblioteca.sugestaoaquisicao.entity;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity 
@Table(name = "sugestoes_aquisicao") 
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class SugestaoAquisicao {

    @Id // Identificador único da sugestão
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gerado automaticamente (1, 2, 3...)
    private Long id;

    @ManyToOne // Uma sugestão pertence a um usuário (muitas sugestões para um usuário)
    @JoinColumn(name = "usuario_id", nullable = true) // Nullable para permitir testes enquanto o login não é integrado
    private Usuario usuario;

    @Column(nullable = false) 
    private String tituloSugerido; // Nome do livro sugerido (Obrigatório)

    @Column(nullable = false) 
    private String autorSugerido; // Nome do autor do livro (Obrigatório)

    private String editoraSugerida; // Editora sugerida 

    @Column(columnDefinition = "TEXT") 
    private String justificativa; // Motivo da sugestão de compra

    @Column(name = "data_sugestao", nullable = false) 
    private LocalDateTime dataSugestao; // Data e hora em que a sugestão foi enviada

    @Enumerated(EnumType.STRING) 
    @Column(nullable = false)
    private StatusSugestao status; // Status da análise (PARA_ANALISE, APROVADA, REJEITADA)
}
