package br.com.ifba.biblioteca;

import br.com.ifba.biblioteca.evento.view.TelaEventosListar;
import javax.swing.SwingUtilities;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BibliotecaApplication {

    public static void main(String[] args) {

        // Inicia o Spring Boot
        var context = new SpringApplicationBuilder(BibliotecaApplication.class)
                .headless(false) 
                .run(args);

        SwingUtilities.invokeLater(() -> {
            //tela de Eventos
            TelaEventosListar tela = context.getBean(TelaEventosListar.class);

            // Carrega os dados na tabela
            tela.carregarTabela();

            // Mostra a janela
            tela.setVisible(true);
        });
    }
}
