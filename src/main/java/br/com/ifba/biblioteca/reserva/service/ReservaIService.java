package br.com.ifba.biblioteca.reserva.service;

import br.com.ifba.biblioteca.reserva.entity.Reserva;
import java.util.List;

/**
 *
 * @author misae
 */

// Interface de Service: define as regras de negócio da Reserva
public interface ReservaIService {
    
    // Criar reserva
    Reserva save(Reserva reserva);

    // Atender (atualizar) reserva
    Reserva update(Reserva reserva);

    // Cancelar reserva
    void delete(Reserva reserva);

    // Listar todas as reservas
    List<Reserva> findAll();

    // Consultar reserva por ID
    Reserva findById(Long id);
}
