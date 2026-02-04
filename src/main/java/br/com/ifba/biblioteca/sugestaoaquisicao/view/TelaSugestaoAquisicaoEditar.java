package br.com.ifba.biblioteca.sugestaoaquisicao.view;

import br.com.ifba.biblioteca.sugestaoaquisicao.controller.SugestaoAquisicaoController;
import br.com.ifba.biblioteca.sugestaoaquisicao.entity.SugestaoAquisicao;
import br.com.ifba.biblioteca.sugestaoaquisicao.entity.StatusSugestao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

@Component
public class TelaSugestaoAquisicaoEditar extends JFrame {

    @Autowired
    private SugestaoAquisicaoController controller;

    @Autowired
    @Lazy
    private TelaSugestaoAquisicaoListar telaListar;

    private SugestaoAquisicao sugestaoAtual;
    
    // Componentes
    private JLabel lblTituloLivro;
    private JLabel lblAutorLivro;
    private JLabel lblUsuario;
    private JLabel lblData;
    private JComboBox<StatusSugestao> cbStatus;
    private JButton btnSalvar, btnVoltar;

    public TelaSugestaoAquisicaoEditar() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Analisar Sugestão de Aquisição");
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
        JLabel lblHeader = new JLabel("ANALISAR SUGESTÃO");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlForm.add(lblHeader, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos - Exibição de Info (Read-only labels for context)
        lblTituloLivro = new JLabel(); lblTituloLivro.setFont(new Font("Segoe UI", Font.BOLD, 14));
        adicionarCampo(pnlForm, gbc, 1, "Livro Sugerido:", lblTituloLivro);
        
        lblAutorLivro = new JLabel();
        adicionarCampo(pnlForm, gbc, 2, "Autor:", lblAutorLivro);
        
        lblUsuario = new JLabel();
        adicionarCampo(pnlForm, gbc, 3, "Sugerido por:", lblUsuario);
        
        lblData = new JLabel();
        adicionarCampo(pnlForm, gbc, 4, "Data da Sugestão:", lblData);
        
        // Campo Editável - Status
        cbStatus = new JComboBox<>(StatusSugestao.values());
        adicionarCampo(pnlForm, gbc, 5, "Alterar Status:", cbStatus);

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
        btnVoltar.addActionListener(e -> voltar());

        btnSalvar = new JButton("Salvar Alteração");
        estilizarBotao(btnSalvar, new Color(46, 204, 113));
        btnSalvar.addActionListener(e -> atualizar());

        pnlBotoes.add(btnVoltar);
        pnlBotoes.add(btnSalvar);
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

    public void preencherDados(SugestaoAquisicao s) {
        this.sugestaoAtual = s;
        
        lblTituloLivro.setText(s.getTituloSugerido());
        lblAutorLivro.setText(s.getAutorSugerido());
        lblUsuario.setText(s.getUsuario() != null ? s.getUsuario().getNomeCompleto() : "Anônimo/Deletado");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lblData.setText(s.getDataSugestao() != null ? s.getDataSugestao().format(formatter) : "-");
        
        cbStatus.setSelectedItem(s.getStatus());
    }

    private void atualizar() {
        try {
            sugestaoAtual.setStatus((StatusSugestao) cbStatus.getSelectedItem());
            controller.update(sugestaoAtual);
            JOptionPane.showMessageDialog(this, "Status atualizado com sucesso!");
            voltar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }
    
    private void voltar() {
        if(telaListar != null) {
            telaListar.carregarTabela();
            telaListar.setVisible(true);
        }
        this.dispose();
    }
}
