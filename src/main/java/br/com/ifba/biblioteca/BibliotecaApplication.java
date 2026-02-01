package br.com.ifba.biblioteca;


import br.com.ifba.biblioteca.menu.view.TelaPrincipal;
import javax.swing.SwingUtilities;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BibliotecaApplication {

    public static void main(String[] args) {

        var context = new SpringApplicationBuilder(BibliotecaApplication.class)
                .headless(false) 
                .run(args);

        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela =
                context.getBean(TelaPrincipal.class);
            tela.setVisible(true);
        });
    }
}
