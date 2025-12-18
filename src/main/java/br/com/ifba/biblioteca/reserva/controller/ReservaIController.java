package br.com.ifba.biblioteca.reserva.controller;

import br.com.ifba.biblioteca.reserva.entity.Reserva;
import java.util.List;

public interface ReservaIController {

    // Criar reserva
    Reserva save(Reserva reserva);

    // Atender (confirmar) reserva
    Reserva update(Reserva reserva);

    // Cancelar reserva
    void delete(Reserva reserva);

    // Listar todas as reservas
    List<Reserva> findAll();

    // Consultar reserva por ID
    Reserva findById(Long id);
}
