/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.ifba.biblioteca.usuario.entity;

import br.com.ifba.biblioteca.pessoa.entity.Pessoa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author jeffe
 */

 // Classe abstrata Usuario
// Representa usuários do sistema (Administrador, Bibliotecário)
@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public abstract class Usuario extends Pessoa {

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;
}
