package br.com.ifba.biblioteca.evento.entity;

/**
 *
 * @author guilhermeAmedrado
 */
public enum StatusEvento {
    ATIVO, // Evento aberto para inscrições ou aguardando acontecer
    REALIZADO, // Evento que já ocorreu na data marcada
    CANCELADO // Evento que foi suspenso pelo administrador
}
