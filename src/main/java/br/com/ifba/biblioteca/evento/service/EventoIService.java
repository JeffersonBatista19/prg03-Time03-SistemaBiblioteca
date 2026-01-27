package br.com.ifba.biblioteca.evento.service;

/**
 *
 * @author guilhermeAmedrado
 */
import br.com.ifba.biblioteca.evento.entity.Evento;
import java.util.List;

public interface EventoIService {
    
    // Contrato de métodos que o serviço de eventos deve implementar
    List<Evento> findAll();
    List<Evento> findByTitulo(String titulo);
    Evento findById(Long id);
    Evento save(Evento evento);
    Evento update(Evento evento);
    void cancelar(Long id);
}