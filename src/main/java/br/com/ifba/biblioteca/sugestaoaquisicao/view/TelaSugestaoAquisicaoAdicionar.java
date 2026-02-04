package br.com.ifba.biblioteca.sugestaoaquisicao.view;

import br.com.ifba.biblioteca.sugestaoaquisicao.controller.SugestaoAquisicaoController;
import br.com.ifba.biblioteca.sugestaoaquisicao.entity.SugestaoAquisicao;
import br.com.ifba.biblioteca.usuario.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import javax.swing.*;
import java.awt.*;


@org.springframework.stereotype.Component
public class TelaSugestaoAquisicaoAdicionar extends JFrame {

    @Autowired
    private SugestaoAquisicaoController controller;

    @Autowired
    private br.com.ifba.biblioteca.usuario.controller.UsuarioIController usuarioController;

    @Autowired
    @Lazy
    private TelaSugestaoAquisicaoListar telaListar;

    private JTextField txtTitulo, txtAutor, txtEditora;
    private JTextArea txtJustificativa;
    private JButton btnEnviar, btnVoltar;

    public TelaSugestaoAquisicaoAdicionar() {
        setTitle("Nova Sugestão de Aquisição");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Padronizado
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizado
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
        JLabel lblHeader = new JLabel("NOVA SUGESTÃO DE AQUISIÇÃO");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlForm.add(lblHeader, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos
        adicionarCampo(pnlForm, gbc, 1, "Título (Obrigatório):", txtTitulo = new JTextField(30));
        adicionarCampo(pnlForm, gbc, 2, "Autor (Obrigatório):", txtAutor = new JTextField(30));
        adicionarCampo(pnlForm, gbc, 3, "Editora:", txtEditora = new JTextField(30));

        // Justificativa
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        pnlForm.add(new JLabel("Justificativa:"), gbc);
        
        txtJustificativa = new JTextArea(5, 30);
        txtJustificativa.setLineWrap(true);
        txtJustificativa.setWrapStyleWord(true);
        JScrollPane scrollJust = new JScrollPane(txtJustificativa);
        gbc.gridx = 1; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.5; // Expand vertical
        pnlForm.add(scrollJust, gbc);

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
        
        btnEnviar = new JButton("Enviar Sugestão");
        estilizarBotao(btnEnviar, new Color(46, 204, 113));

        pnlBotoes.add(btnVoltar);
        pnlBotoes.add(btnEnviar);
        add(pnlBotoes, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> {
            setVisible(false);
            if(telaListar != null) {
                telaListar.carregarTabela();
                telaListar.setVisible(true);
            }
        });

        btnEnviar.addActionListener(e -> salvar());
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

    private void salvar() {
        try {
            SugestaoAquisicao s = new SugestaoAquisicao();
            s.setTituloSugerido(txtTitulo.getText());
            s.setAutorSugerido(txtAutor.getText());
            s.setEditoraSugerida(txtEditora.getText());
            s.setJustificativa(txtJustificativa.getText());

            java.util.List<Usuario> usuarios = usuarioController.findAll();
            if (usuarios != null && !usuarios.isEmpty()) {
                s.setUsuario(usuarios.get(0));
            } else {
                System.out.println("Aviso: Nenhum usuário encontrado para vincular à sugestão. Salvando sem usuário.");
            }

            controller.save(s);
            JOptionPane.showMessageDialog(this, "Sugestão enviada para análise com sucesso.");
            limparCampos();
            btnVoltar.doClick();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtEditora.setText("");
        txtJustificativa.setText("");
    }
}
