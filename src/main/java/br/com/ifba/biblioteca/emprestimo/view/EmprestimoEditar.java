package br.com.ifba.biblioteca.emprestimo.view;

import br.com.ifba.biblioteca.emprestimo.controller.EmprestimoIController;
import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EmprestimoEditar extends JFrame {
    
    private final EmprestimoIController controller;
    private final EmprestimoListar telaListagem;
    private final Emprestimo emprestimoAtual;

    // Componentes
    private JTextField txtDataDevolucao;
    private JComboBox<String> cmbStatus;
    private JButton btnSalvar;
    private JButton btnCancelar;

    public EmprestimoEditar(EmprestimoIController controller, EmprestimoListar telaListagem, Emprestimo emprestimo) {
        this.controller = controller;
        this.telaListagem = telaListagem;
        this.emprestimoAtual = emprestimo;
        
        initComponents();
        preencherDados();
    }

    private void initComponents() {
        setTitle("Editar Empréstimo #" + emprestimoAtual.getId());
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
        JLabel lblTitulo = new JLabel("EDITAR EMPRÉSTIMO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlForm.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos
        adicionarCampo(pnlForm, gbc, 1, "Nova Data Devolução (dd/MM/yyyy):", txtDataDevolucao = new JTextField(15));
        
        cmbStatus = new JComboBox<>(new String[] { "ATIVO", "CONCLUIDO", "ATRASADO" });
        adicionarCampo(pnlForm, gbc, 2, "Status:", cmbStatus);

        // Container Central
        JPanel pnlCenterContainer = new JPanel(new GridBagLayout());
        pnlCenterContainer.setBackground(new Color(240, 242, 245));
        pnlCenterContainer.add(pnlForm);
        add(pnlCenterContainer, BorderLayout.CENTER);

        // --- BOTÕES ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlBotoes.setBackground(Color.WHITE);
        pnlBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        btnCancelar = new JButton("Voltar");
        estilizarBotao(btnCancelar, new Color(99, 110, 114));
        btnCancelar.addActionListener(e -> dispose());
        
        btnSalvar = new JButton("Confirmar");
        estilizarBotao(btnSalvar, new Color(46, 204, 113));
        btnSalvar.addActionListener(e -> salvarAlteracoes());

        pnlBotoes.add(btnCancelar);
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

    private void preencherDados() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            txtDataDevolucao.setText(emprestimoAtual.getDataPrevistaDevolucao() != null ? 
                    emprestimoAtual.getDataPrevistaDevolucao().format(formatter) : "");
            
            if (emprestimoAtual.getStatus() != null) {
                cmbStatus.setSelectedItem(emprestimoAtual.getStatus().toString());
            }
        } catch (Exception e) {
            System.err.println("Erro ao preencher dados: " + e.getMessage());
        }
    }

    private void salvarAlteracoes() {
        try {
            String dataStr = txtDataDevolucao.getText();
            String statusStr = (String) cmbStatus.getSelectedItem();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate novaData = LocalDate.parse(dataStr, formatter);
            Emprestimo.StatusEmprestimo novoStatus = Emprestimo.StatusEmprestimo.valueOf(statusStr);
            
            emprestimoAtual.setDataPrevistaDevolucao(novaData);
            emprestimoAtual.setStatus(novoStatus);
            
            controller.update(emprestimoAtual);
            
            JOptionPane.showMessageDialog(this, "Empréstimo atualizado com sucesso!");
            
            if (telaListagem != null) {
                telaListagem.carregarDados();
            }
            dispose();
            
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data inválida! Use o formato DD/MM/AAAA (ex: 31/12/2025).", "Erro de Data", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
