package br.com.ifba.biblioteca.editora.view;

import br.com.ifba.biblioteca.editora.controller.EditoraIController;
import br.com.ifba.biblioteca.editora.entity.Editora;
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
import javax.swing.JTextField;

public class EditoraEditar extends JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EditoraEditar.class.getName());
    
    private final EditoraIController controller;
    private final Long id;

    // Componentes
    private JTextField txtNome, txtCNPJ, txtTelefone, txtEndereco;
    private JButton btnAtualizar, btnCancelar;

    // inicializa a tela de edição da editora selecionada
    public EditoraEditar(EditoraIController controller, Long id) {
        this.controller = controller;
        this.id = id;
        
        initComponents();
        
        // Bloquear edição do CNPJ
        txtCNPJ.setEditable(false);
        txtCNPJ.setBackground(new Color(230, 230, 230));
        
        carregarEditora();
    }
    
    private void initComponents() {
        setTitle("Editar Editora");
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
        JLabel lblTitulo = new JLabel("EDITAR EDITORA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlForm.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos
        adicionarCampo(pnlForm, gbc, 1, "Nome:", txtNome = new JTextField(30));
        adicionarCampo(pnlForm, gbc, 2, "CNPJ:", txtCNPJ = new JTextField(20));
        adicionarCampo(pnlForm, gbc, 3, "Telefone:", txtTelefone = new JTextField(20));
        adicionarCampo(pnlForm, gbc, 4, "Endereço:", txtEndereco = new JTextField(30));

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
        
        btnAtualizar = new JButton("Salvar Alterações");
        estilizarBotao(btnAtualizar, new Color(46, 204, 113));
        btnAtualizar.addActionListener(e -> salvarAlteracoes());

        pnlBotoes.add(btnCancelar);
        pnlBotoes.add(btnAtualizar);
        add(pnlBotoes, BorderLayout.SOUTH);
    }
    
    private void adicionarCampo(JPanel pnl, GridBagConstraints gbc, int row, String label, java.awt.Component comp) {
        gbc.gridx = 0; gbc.gridy = row;
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

    private void carregarEditora() {
        try {
            Editora editora = controller.findById(id);
            if (editora != null) {
                txtNome.setText(editora.getNome());
                txtCNPJ.setText(editora.getCnpj());
                txtTelefone.setText(editora.getTelefone());
                txtEndereco.setText(editora.getEndereco());
            } else {
                JOptionPane.showMessageDialog(this, "Editora não encontrada!");
                dispose();
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Erro ao carregar editora: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
             dispose();
        }
    }
    
    private void salvarAlteracoes() {
        try {
            Editora editora = controller.findById(id);

            if (editora != null) {
                editora.setNome(txtNome.getText());
                // CNPJ não é atualizado pois é chave ou fixo nesta tela
                editora.setTelefone(txtTelefone.getText());
                editora.setEndereco(txtEndereco.getText());

                controller.update(editora); 

                JOptionPane.showMessageDialog(this, "Editora atualizada com sucesso!");
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Editora não encontrada!");
                dispose();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar editora: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
