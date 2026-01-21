package br.com.ifba.biblioteca.autor.view;

import br.com.ifba.biblioteca.autor.controller.AutorIController;
import br.com.ifba.biblioteca.autor.entity.Autor;
import javax.swing.*;
import java.awt.*;

/**
 * Tela para cadastrar novos autores.
 * @author guilherme
 */
public class AutorAdicionar extends JFrame {

    private AutorIController controller;
    private AutorListar telaPai; // Referência para atualizar a tabela depois

    private JTextField txtNome = new JTextField(20);
    private JTextField txtNacionalidade = new JTextField(20);
    private JTextField txtAnoNasc = new JTextField(10);
    private JTextField txtAnoFalec = new JTextField(10);
    private JTextArea txtBiografia = new JTextArea(5, 20);

    // Construtor recebe o controller e a tela de listagem
    public AutorAdicionar(AutorIController controller, AutorListar telaPai) {
        super("Adicionar Autor");
        this.controller = controller;
        this.telaPai = telaPai;
        initComponents();
    }

    private void initComponents() {
        setSize(400, 500);
        setLayout(new GridLayout(6, 2, 10, 10)); // Layout em grade
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Nome:"));
        add(txtNome);

        add(new JLabel("Nacionalidade:"));
        add(txtNacionalidade);

        add(new JLabel("Ano Nascimento:"));
        add(txtAnoNasc);

        add(new JLabel("Ano Falecimento:"));
        add(txtAnoFalec);

        add(new JLabel("Biografia:"));
        add(new JScrollPane(txtBiografia));

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        add(btnSalvar);
        add(btnCancelar);

        // Ação do botão Salvar
        btnSalvar.addActionListener(e -> salvarAutor());
        
        // Ação do botão Cancelar
        btnCancelar.addActionListener(e -> dispose());
    }

    private void salvarAutor() {
        try {
            Autor autor = new Autor();
            autor.setNome(txtNome.getText());
            autor.setNacionalidade(txtNacionalidade.getText());
            autor.setBiografia(txtBiografia.getText());

            // Converte os números (se estiver vazio, deixa null ou 0)
            if (!txtAnoNasc.getText().isEmpty()) {
                autor.setAnoNascimento(Integer.parseInt(txtAnoNasc.getText()));
            }
            if (!txtAnoFalec.getText().isEmpty()) {
                autor.setAnoFalecimento(Integer.parseInt(txtAnoFalec.getText()));
            }

            controller.save(autor); // Chama o controller
            JOptionPane.showMessageDialog(this, "Autor salvo com sucesso!");
            
            telaPai.carregarDados(); // Atualiza a tabela da outra tela
            dispose(); // Fecha esta janela

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Anos devem ser números válidos!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }
}