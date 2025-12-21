package br.com.ifba.biblioteca;

import br.com.ifba.biblioteca.emprestimo.view.EmprestimoListar;
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
            // 2. MUDOU AQUI: Pedimos a tela EmprestimoListar ao Spring
            EmprestimoListar tela = context.getBean(EmprestimoListar.class);
            
            tela.setVisible(true);
            
            // 3. MUDOU AQUI: Chamamos o método de carregar dessa tela
            tela.carregarDados(); 
        });
    }
}
