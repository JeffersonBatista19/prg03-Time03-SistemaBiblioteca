/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package br.com.ifba.biblioteca.usuario.service;

import br.com.ifba.biblioteca.usuario.entity.Usuario;
import java.util.List;

/**
 *
 * @author jeffe
 */

public interface UsuarioIService {

    Usuario save(Usuario usuario);

    Usuario update(Usuario usuario);

    List<Usuario> findAll();

    Usuario findById(Long id);

    Usuario findByLogin(String login);
    
    boolean existsByCpf(String cpf);
}
