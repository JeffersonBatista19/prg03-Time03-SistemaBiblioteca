package br.com.ifba.biblioteca.usuario.entity;

/**
 *
 * @author guilhermeAmedrado
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bibliotecario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bibliotecario extends Usuario {

    @Column(nullable = false)
    private String turno;

    @Column(name = "matricula_funcionario", nullable = false, unique = true)
    private String matriculaFuncionario;
}
