/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */




package br.com.ifba.biblioteca.usuario.service;

import br.com.ifba.biblioteca.usuario.entity.Usuario;
import br.com.ifba.biblioteca.usuario.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.stereotype.Service;






/**
 *
 * @author jeffe
 */





@Service
@Slf4j
public class UsuarioService implements UsuarioIService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario save(Usuario usuario) {

        if (repository.findByLogin(usuario.getLogin()) != null) {
            throw new RuntimeException("Já existe um usuário com este login.");
        }

        if (repository.existsByCpf(usuario.getCpf())) {
            throw new RuntimeException("Já existe um usuário com este CPF.");
        }

        Usuario salvo = repository.save(usuario);

        log.info("Usuário salvo com sucesso. Login: {}", usuario.getLogin());

        return salvo;
    }

    @Override
    public Usuario update(Usuario usuario) {

        Usuario existente = repository.findById(usuario.getId()).orElse(null);

        if (existente == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        existente.setLogin(usuario.getLogin());
        existente.setSenha(usuario.getSenha());
        existente.setTelefone(usuario.getTelefone());
        existente.setStatusPessoa(usuario.getStatusPessoa());
        existente.setNivelAcesso(usuario.getNivelAcesso());
        existente.setTipoPerfil(usuario.getTipoPerfil());

        Usuario atualizado = repository.save(existente);

        log.info("Usuário atualizado com sucesso. ID: {}", existente.getId());

        return atualizado;
    }

    @Override
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    @Override
    public Usuario findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Usuario findByLogin(String login) {
        return repository.findByLogin(login);
    }
}
