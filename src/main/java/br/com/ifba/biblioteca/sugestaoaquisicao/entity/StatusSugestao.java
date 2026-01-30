package br.com.ifba.biblioteca.sugestaoaquisicao.entity;

/**
 *
 * @author guilhermeAmedrado
 */

public enum StatusSugestao {
    PARA_ANALISE, // Sugestão recebida, aguardando parecer do bibliotecário
    APROVADA, // Sugestão aceita para futura compra
    REJEITADA // Sugestão negada (ex: livro já existe no acervo)
}
