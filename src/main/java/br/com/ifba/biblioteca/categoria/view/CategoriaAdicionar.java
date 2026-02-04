package br.com.ifba.biblioteca.categoria.view;

import br.com.ifba.biblioteca.categoria.controller.CategoriaIController;
import br.com.ifba.biblioteca.categoria.entity.Categoria;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CategoriaAdicionar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CategoriaAdicionar.class.getName());
    private CategoriaListar telaListar;
    private CategoriaIController categoriaController;

    private JTextField txtNome;
    private JTextArea txtDescricao;
    private JButton btnSalvar, btnCancelar;

    public CategoriaAdicionar(CategoriaListar telaListar, CategoriaIController categoriaController) {
        this.telaListar = telaListar;
        this.categoriaController = categoriaController;
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Adicionar Categoria");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

        // Header
        JLabel lblTitulo = new JLabel("ADICIONAR CATEGORIA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlForm.add(lblTitulo, gbc);
        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Nome
        gbc.gridx = 0; gbc.gridy = 1;
        pnlForm.add(new JLabel("Nome:"), gbc);
        txtNome = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 1;
        pnlForm.add(txtNome, gbc);

        // Descrição
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        pnlForm.add(new JLabel("Descrição:"), gbc);
        txtDescricao = new JTextArea(5, 20);
        txtDescricao.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(txtDescricao);
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.5;
        pnlForm.add(scroll, gbc);

        // Container Central
        JPanel pnlCenterContainer = new JPanel(new GridBagLayout());
        pnlCenterContainer.setBackground(new Color(240, 242, 245));
        pnlCenterContainer.add(pnlForm);
        add(pnlCenterContainer, BorderLayout.CENTER);

        // --- BOTOES SUL ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlBotoes.setBackground(Color.WHITE);
        pnlBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        btnCancelar = new JButton("Voltar");
        estilizarBotao(btnCancelar, new Color(99, 110, 114));
        
        btnSalvar = new JButton("Salvar");
        estilizarBotao(btnSalvar, new Color(46, 204, 113));

        pnlBotoes.add(btnCancelar);
        pnlBotoes.add(btnSalvar);
        add(pnlBotoes, BorderLayout.SOUTH);
        
        // Listeners
        btnCancelar.addActionListener(e -> dispose());
        btnSalvar.addActionListener(e -> salvarCategoria());
    }

    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void salvarCategoria() {
        try {
            String nome = txtNome.getText();
            String descricao = txtDescricao.getText();

            if (nome == null || nome.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o nome da categoria.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Categoria categoria = new Categoria();
            categoria.setNome(nome.trim());
            categoria.setDescricao(descricao);
            categoria.setAtivo(true);

            categoriaController.save(categoria);

            JOptionPane.showMessageDialog(this, "Categoria cadastrada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            if (telaListar != null) {
                telaListar.atualizarLista();
            }

            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
        }
    }
}
