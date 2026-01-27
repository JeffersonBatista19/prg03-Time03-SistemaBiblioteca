package br.com.ifba.biblioteca.evento.entity;
/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.usuario.entity.Usuario;
import br.com.ifba.biblioteca.cliente.entity.Cliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity 
@Table(name = "eventos") 
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class Evento {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false) 
    private String titulo;

    @Column(columnDefinition = "TEXT") // Permite textos longos para a descrição
    private String descricao;

    @Column(name = "data_horario", nullable = false) // Mapeia o nome da coluna e define como obrigatório
    private LocalDateTime dataHorario;
    
    
    // Relacionamentos da classe 

 
    @ManyToOne // Muitos eventos podem ocorrer em um mesmo local
    @JoinColumn(name = "local_id", nullable = true) 
    private Localfake localEntity; // Relacionamento com o local onde o evento ocorre

    // Campo temporário para manter compatibilidade com versões antigas do banco
    @Column(name = "local", nullable = true)
    private String localLegado;

    @ManyToOne // Relacionamento de gerenciamento (Administrador do diagrama)
    @JoinColumn(name = "usuario_gestor_id", nullable = true)
    private Usuario gestor;

    @ManyToMany // Muitos eventos podem ter muitos participantes (Clientes)
    @JoinTable(name = "evento_participantes", // Cria uma tabela intermediária para a relação muitos-para-muitos
        joinColumns = @JoinColumn(name = "evento_id"),
        inverseJoinColumns = @JoinColumn(name = "cliente_id"))
    private List<Cliente> participantes = new ArrayList<>(); // Lista de clientes inscritos no evento

 
    @Column(name = "limite_vagas", nullable = false) // Limite máximo de pessoas no evento
    private Integer limiteVagas; // Capacidade máxima de participantes

    @Column(name = "vagas_preenchidas") // Contador de pessoas inscritas
    private Integer vagasPreenchidas = 0; // Quantidade atual de inscritos

    @Enumerated(EnumType.STRING) // Salva o nome do status (ex: "ATIVO") no banco em vez de números
    @Column(nullable = false)
    private StatusEvento status; // Estado atual do evento (ATIVO, CANCELADO, etc)
}