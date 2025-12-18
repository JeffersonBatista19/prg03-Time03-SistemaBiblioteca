package br.com.ifba.biblioteca.reserva.controller;

import br.com.ifba.biblioteca.reserva.entity.Reserva;
import br.com.ifba.biblioteca.reserva.service.ReservaIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.List;

// Camada Controller, que faz a comunicação entre a camada View e a camada Service
@Controller
public class ReservaController implements ReservaIController {

    // Injeta o Service, onde ficam as regras de negócio.
    @Autowired
    private ReservaIService reservaService;

    // Criar reserva
    @Override
    public Reserva save(Reserva reserva) {
        return reservaService.save(reserva);
    }

    // Atualizar reserva
    @Override
    public Reserva update(Reserva reserva) {
        return reservaService.update(reserva);
    }

    // Cancelar reserva
    @Override
    public void delete(Reserva reserva) {
         reservaService.delete(reserva);
    }

    // Listar todas as reservas
    @Override
    public List<Reserva> findAll() {
        return reservaService.findAll();
    }

    // Buscar reserva por ID
    @Override
    public Reserva findById(Long id) {
         return reservaService.findById(id);
    }
}
