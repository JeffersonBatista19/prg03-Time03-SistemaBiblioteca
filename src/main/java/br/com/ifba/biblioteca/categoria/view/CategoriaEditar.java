/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.ifba.biblioteca.categoria.view;

import br.com.ifba.biblioteca.categoria.controller.CategoriaIController;
import br.com.ifba.biblioteca.categoria.entity.Categoria;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author jeffe
 */
public class CategoriaEditar extends JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CategoriaEditar.class.getName());
    
    private CategoriaListar telaListar;
    private CategoriaIController categoriaController;
    private Categoria categoria;
    
    // UI Components
    private JTextField txtId;
    private JTextField txtNome;
    private JTextArea txtDescricao;
    private JButton btnSalvar;
    private JButton btnCancelar;

    /**
     * Creates new form CategoriaEditar
     */
    public CategoriaEditar(CategoriaListar telaListar, CategoriaIController categoriaController, Categoria categoria) {
        this.telaListar = telaListar;
        this.categoriaController = categoriaController;
        this.categoria = categoria;

        initComponents();
        
        txtId.setEditable(false);
        carregarCampos();
    }
    
    private void initComponents() {
        setTitle("Editar Categoria");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
        JLabel lblTitulo = new JLabel("EDITAR CATEGORIA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlForm.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // ID
        gbc.gridx = 0; gbc.gridy = 1;
        pnlForm.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(10);
        pnlForm.add(txtId, gbc);

        // Nome
        gbc.gridx = 0; gbc.gridy = 2;
        pnlForm.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(30);
        pnlForm.add(txtNome, gbc);

        // Descrição
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        pnlForm.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        txtDescricao = new JTextArea(5, 30);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescricao);
        pnlForm.add(scrollDesc, gbc);

        // Container Central
        JPanel pnlCenterContainer = new JPanel(new GridBagLayout());
        pnlCenterContainer.setBackground(new Color(240, 242, 245));
        pnlCenterContainer.add(pnlForm);
        add(pnlCenterContainer, BorderLayout.CENTER);

        // --- BOTÕES ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlBotoes.setBackground(Color.WHITE);
        pnlBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        btnCancelar = new JButton("Cancelar");
        estilizarBotao(btnCancelar, new Color(99, 110, 114));
        btnCancelar.addActionListener(e -> dispose());
        
        btnSalvar = new JButton("Salvar");
        estilizarBotao(btnSalvar, new Color(46, 204, 113));
        btnSalvar.addActionListener(this::btnSalvarActionPerformed);

        pnlBotoes.add(btnCancelar);
        pnlBotoes.add(btnSalvar);
        add(pnlBotoes, BorderLayout.SOUTH);
    }
    
    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }
    
    private void carregarCampos() {
        if (categoria == null) return;

        txtId.setText(String.valueOf(categoria.getId()));
        txtNome.setText(categoria.getNome());
        txtDescricao.setText(categoria.getDescricao() == null ? "" : categoria.getDescricao());
    }

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String nome = txtNome.getText();
            String descricao = txtDescricao.getText();

            if (nome == null || nome.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o nome da categoria.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Atualiza o objeto categoria
            categoria.setNome(nome.trim());
            categoria.setDescricao(descricao);

            // Chama controller
            categoriaController.update(categoria);

            JOptionPane.showMessageDialog(this, "Categoria atualizada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            // Atualiza a tela listar e fecha
            if (telaListar != null) {
                telaListar.atualizarLista(); 
            }
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao salvar", JOptionPane.ERROR_MESSAGE);
        }
    }
}
