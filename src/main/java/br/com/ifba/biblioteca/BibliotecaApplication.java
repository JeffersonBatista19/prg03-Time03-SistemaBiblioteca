package br.com.ifba.biblioteca;

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
    br.com.ifba.biblioteca.categoria.view.CategoriaListar tela =
        context.getBean(br.com.ifba.biblioteca.categoria.view.CategoriaListar.class);

    tela.setVisible(true);
});
    }
}
