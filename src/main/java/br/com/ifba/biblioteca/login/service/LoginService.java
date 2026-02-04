package br.com.ifba.biblioteca.login.service;

import br.com.ifba.biblioteca.usuario.entity.Usuario;
import br.com.ifba.biblioteca.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UsuarioRepository usuarioRepository;

    public LoginService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario autenticar(String login, String senha) {
        Usuario usuario = usuarioRepository.findByLogin(login);
        
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario;
        }
        
        return null;
    }
}
