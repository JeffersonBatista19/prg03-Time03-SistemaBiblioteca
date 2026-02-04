package br.com.ifba.biblioteca.evento.controller;

/**
 *
 * @author guilhermeAmedrado
 */
import br.com.ifba.biblioteca.evento.entity.Evento;
import br.com.ifba.biblioteca.evento.entity.Localfake;
import br.com.ifba.biblioteca.evento.repository.LocalfakeRepository;
import br.com.ifba.biblioteca.evento.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class EventoController {

    @Autowired
    private EventoService service; 

    @Autowired
    private LocalfakeRepository localRepository;

    public List<Localfake> findAllLocais() {
        return localRepository.findAll(); // Busca todos os locais cadastrados para exibir na tela
    }

    public List<Evento> findAll() {
        return service.findAll();
    }

    public List<Evento> findByTitulo(String titulo) {
        return service.findByTitulo(titulo);
    }

    public List<Evento> findByStatus(br.com.ifba.biblioteca.evento.entity.StatusEvento status) {
        return service.findByStatus(status);
    }

    public List<Evento> findByStatusNot(br.com.ifba.biblioteca.evento.entity.StatusEvento status) {
        return service.findByStatusNot(status);
    }

    public Evento findById(Long id) {
        return service.findById(id);
    }

    public Evento save(Evento evento) {
        return service.save(evento); // Encaminha o novo evento para o serviço validar e salvar
    }

    public Evento update(Evento evento) {
        return service.update(evento);
    }

    public void cancelar(Long id) {
        service.cancelar(id);
    }
}