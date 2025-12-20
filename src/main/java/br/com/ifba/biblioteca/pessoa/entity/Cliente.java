/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.biblioteca.pessoa.entity;

/**
 *
 * @author guilhermeAmedrado
 */

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "Clientes") // Essa será a tabela real no banco
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Pessoa {
    //pode ficar vazia, pois herda tudo (nome, cpf, perfil) de Pessoa
}