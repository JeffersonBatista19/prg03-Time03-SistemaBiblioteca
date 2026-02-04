package br.com.ifba.biblioteca.usuario.entity;

/**
 *
 * @author guilhermeAmedrado
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "administrador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Administrador extends Usuario {

    @Column(name = "ultimo_acesso")
    private LocalDateTime ultimoAcesso;
}
