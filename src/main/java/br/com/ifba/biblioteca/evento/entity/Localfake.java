package br.com.ifba.biblioteca.evento.entity;

/**
 *
 * @author guilhermeAmedrado
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "locais")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Localfake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID do local

    @Column(nullable = false)
    private String nome; // Nome do local (ex: Auditório)

    private String endereco; // Endereço físico do local

    @Override
    public String toString() {
        return nome; // Retorna apenas o nome para aparecer corretamente nas caixas de seleção (ComboBox)
    }
}
