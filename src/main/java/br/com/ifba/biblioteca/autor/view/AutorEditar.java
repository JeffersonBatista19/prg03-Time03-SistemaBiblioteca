package br.com.ifba.biblioteca.autor.view;

import br.com.ifba.biblioteca.autor.controller.AutorIController;
import br.com.ifba.biblioteca.autor.entity.Autor;
import javax.swing.*;
import java.awt.*;

/**
 * Tela para editar um autor existente.
 * @author guilherme
 */
public class AutorEditar extends JFrame {

    private AutorIController controller;
    private AutorListar telaPai;
    private Autor autorAtual; // O autor que estamos editando

    private JTextField txtNome = new JTextField(20);
    private JTextField txtNacionalidade = new JTextField(20);
    private JTextField txtAnoNasc = new JTextField(10);
    private JTextField txtAnoFalec = new JTextField(10);
    private JTextArea txtBiografia = new JTextArea(5, 20);

    public AutorEditar(AutorIController controller, AutorListar telaPai, Autor autor) {
        super("Editar Autor");
        this.controller = controller;
        this.telaPai = telaPai;
        this.autorAtual = autor;
        initComponents();
        preencherCampos(); // Preenche a tela com os dados do autor
    }

    private void initComponents() {
        setSize(400, 500);
        setLayout(new GridLayout(6, 2, 10, 10));
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

        JButton btnSalvar = new JButton("Salvar Alterações");
        JButton btnCancelar = new JButton("Cancelar");

        add(btnSalvar);
        add(btnCancelar);

        btnSalvar.addActionListener(e -> atualizarAutor());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void preencherCampos() {
        txtNome.setText(autorAtual.getNome());
        txtNacionalidade.setText(autorAtual.getNacionalidade());
        txtBiografia.setText(autorAtual.getBiografia());
        
        if (autorAtual.getAnoNascimento() != null) 
            txtAnoNasc.setText(String.valueOf(autorAtual.getAnoNascimento()));
            
        if (autorAtual.getAnoFalecimento() != null) 
            txtAnoFalec.setText(String.valueOf(autorAtual.getAnoFalecimento()));
    }

    private void atualizarAutor() {
        try {
            // Atualiza o objeto existente com os novos dados da tela
            autorAtual.setNome(txtNome.getText());
            autorAtual.setNacionalidade(txtNacionalidade.getText());
            autorAtual.setBiografia(txtBiografia.getText());

            if (!txtAnoNasc.getText().isEmpty()) 
                autorAtual.setAnoNascimento(Integer.parseInt(txtAnoNasc.getText()));
            
            if (!txtAnoFalec.getText().isEmpty()) 
                autorAtual.setAnoFalecimento(Integer.parseInt(txtAnoFalec.getText()));

            controller.update(autorAtual); // Manda atualizar no banco
            
            JOptionPane.showMessageDialog(this, "Autor atualizado com sucesso!");
            telaPai.carregarDados(); // Atualiza a lista
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique os campos numéricos!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage());
        }
    }
}