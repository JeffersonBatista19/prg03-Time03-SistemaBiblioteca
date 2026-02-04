package br.com.ifba.biblioteca.autor.view;

import br.com.ifba.biblioteca.autor.controller.AutorIController;
import br.com.ifba.biblioteca.autor.entity.Autor;
import javax.swing.*;
import java.awt.*;

public class AutorAdicionar extends JFrame {

    private AutorIController controller;
    private AutorListar telaPai; 

    private JTextField txtNome;
    private JTextField txtNacionalidade;
    private JTextField txtAnoNasc;
    private JTextField txtAnoFalec;
    private JTextArea txtBiografia;
    private JButton btnSalvar;
    private JButton btnVoltar;

    public AutorAdicionar(AutorIController controller, AutorListar telaPai) {
        super("Adicionar Autor");
        this.controller = controller;
        this.telaPai = telaPai;
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizado
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- FORMULÁRIO CENTRAL ---
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

        // Header
        JLabel lblHeader = new JLabel("NOVO AUTOR");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlForm.add(lblHeader, gbc);

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

        // --- BOTOES SUL ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlBotoes.setBackground(Color.WHITE);
        pnlBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(99, 110, 114));
        
        btnSalvar = new JButton("Salvar");
        estilizarBotao(btnSalvar, new Color(46, 204, 113));

        pnlBotoes.add(btnVoltar);
        pnlBotoes.add(btnSalvar);
        add(pnlBotoes, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> dispose());
        btnSalvar.addActionListener(e -> salvarAutor());
    }

    private void adicionarCampo(JPanel pnl, GridBagConstraints gbc, int row, String label, Component comp) {
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

    private void salvarAutor() {
        try {
            Autor autor = new Autor();
            autor.setNome(txtNome.getText());
            autor.setNacionalidade(txtNacionalidade.getText());
            autor.setBiografia(txtBiografia.getText());

            // Validação Ano Nascimento
            String anoNascStr = txtAnoNasc.getText();
            if (anoNascStr != null && !anoNascStr.trim().isEmpty()) {
                try {
                    autor.setAnoNascimento(Integer.parseInt(anoNascStr.trim()));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Ano de Nascimento inválido! Digite apenas números.");
                }
            } else {
                 throw new RuntimeException("O Ano de Nascimento é obrigatório.");
            }

            // Validação Ano Falecimento (Opcional)
            String anoFalecStr = txtAnoFalec.getText();
            if (anoFalecStr != null && !anoFalecStr.trim().isEmpty()) {
                try {
                     autor.setAnoFalecimento(Integer.parseInt(anoFalecStr.trim()));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Ano de Falecimento inválido! Digite apenas números (ou deixe vazio).");
                }
            } else {
                autor.setAnoFalecimento(null); // Explicitamente null se estiver vazio
            }

            controller.save(autor); 
            JOptionPane.showMessageDialog(this, "Autor salvo com sucesso!");
            
            if(telaPai != null) telaPai.carregarDados(); 
            dispose(); 

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Validação", JOptionPane.WARNING_MESSAGE);
        }
    }
}