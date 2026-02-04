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

    private JTextField txtNome;
    private JTextField txtNacionalidade;
    private JTextField txtAnoNasc;
    private JTextField txtAnoFalec;
    private JTextArea txtBiografia;
    private JButton btnSalvar, btnVoltar;

    public AutorEditar(AutorIController controller, AutorListar telaPai, Autor autor) {
        super("Editar Autor");
        this.controller = controller;
        this.telaPai = telaPai;
        this.autorAtual = autor;
        initComponents();
        preencherCampos(); // Preenche a tela com os dados do autor
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- PAINEL FORMULÁRIO ---
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Título
        JLabel lblTitulo = new JLabel("EDITAR AUTOR");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlForm.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos
        adicionarCampo(pnlForm, gbc, 1, "Nome:", txtNome = new JTextField(30));
        adicionarCampo(pnlForm, gbc, 2, "Nacionalidade:", txtNacionalidade = new JTextField(30));
        adicionarCampo(pnlForm, gbc, 3, "Ano Nascimento:", txtAnoNasc = new JTextField(10));
        adicionarCampo(pnlForm, gbc, 4, "Ano Falecimento:", txtAnoFalec = new JTextField(10));

        // Biografia
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        pnlForm.add(new JLabel("Biografia:"), gbc);
        
        txtBiografia = new JTextArea(5, 30);
        txtBiografia.setLineWrap(true);
        txtBiografia.setWrapStyleWord(true);
        JScrollPane scrollBio = new JScrollPane(txtBiografia);
        gbc.gridx = 1; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.5; // Expand vertical
        pnlForm.add(scrollBio, gbc);

        // Container Central
        JPanel pnlCenterContainer = new JPanel(new GridBagLayout());
        pnlCenterContainer.setBackground(new Color(240, 242, 245));
        pnlCenterContainer.add(pnlForm);
        add(pnlCenterContainer, BorderLayout.CENTER);

        // --- BOTÕES ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlBotoes.setBackground(Color.WHITE);
        pnlBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(99, 110, 114));
        btnVoltar.addActionListener(e -> dispose());

        btnSalvar = new JButton("Salvar Alterações");
        estilizarBotao(btnSalvar, new Color(46, 204, 113));
        btnSalvar.addActionListener(e -> atualizarAutor());

        pnlBotoes.add(btnVoltar);
        pnlBotoes.add(btnSalvar);
        add(pnlBotoes, BorderLayout.SOUTH);
    }
    
    private void adicionarCampo(JPanel pnl, GridBagConstraints gbc, int row, String label, java.awt.Component comp) {
        gbc.gridx = 0; gbc.gridy = row;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnl.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.gridy = row;
        pnl.add(comp, gbc);
    }
    
    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
            else
                autorAtual.setAnoNascimento(null);
            
            if (!txtAnoFalec.getText().isEmpty()) 
                autorAtual.setAnoFalecimento(Integer.parseInt(txtAnoFalec.getText()));
            else
                 autorAtual.setAnoFalecimento(null);

            controller.update(autorAtual); // Manda atualizar no banco
            
            JOptionPane.showMessageDialog(this, "Autor atualizado com sucesso!");
            if (telaPai != null) telaPai.carregarDados(); // Atualiza a lista
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique os campos numéricos!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage());
        }
    }
}