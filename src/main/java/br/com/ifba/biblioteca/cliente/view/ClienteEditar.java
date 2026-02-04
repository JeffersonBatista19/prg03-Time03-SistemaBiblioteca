package br.com.ifba.biblioteca.cliente.view;

import br.com.ifba.biblioteca.cliente.controller.ClienteIController;
import br.com.ifba.biblioteca.cliente.entity.Cliente;
import br.com.ifba.biblioteca.cliente.entity.TipoCliente;
import br.com.ifba.biblioteca.pessoa.entity.StatusPessoa;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClienteEditar extends JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ClienteEditar.class.getName());

    private final ClienteIController controller;
    private Cliente cliente; 

    // Componentes
    private JTextField txtNome, txtCPF, txtTelefone, txtLimiteEmprestimos;
    private JComboBox<String> cmbTipoCliente;
    private JComboBox<String> cmbStatus;
    private JButton btnSalvar, btnCancelar;

    public ClienteEditar(ClienteIController controller, Cliente cliente) {
        this.controller = controller;
        this.cliente = cliente;
        
        initComponents();
        preencherCampos();
        atualizarLimitePorTipo();
        bloquearCampos();
    }

    private void initComponents() {
        setTitle("Editar Cliente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha só a janela
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
        JLabel lblTitulo = new JLabel("EDITAR CLIENTE");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlForm.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos
        adicionarCampo(pnlForm, gbc, 1, "Nome Completo:", txtNome = new JTextField(30));
        adicionarCampo(pnlForm, gbc, 2, "CPF:", txtCPF = new JTextField(15));
        adicionarCampo(pnlForm, gbc, 3, "Telefone:", txtTelefone = new JTextField(15));
        
        cmbTipoCliente = new JComboBox<>(new String[] { "ALUNO", "PROFESSOR", "VISITANTE" });
        cmbTipoCliente.addActionListener(e -> atualizarLimitePorTipo());
        adicionarCampo(pnlForm, gbc, 4, "Tipo de Cliente:", cmbTipoCliente);
        
        adicionarCampo(pnlForm, gbc, 5, "Limite de Empréstimos:", txtLimiteEmprestimos = new JTextField(5));
        
        cmbStatus = new JComboBox<>(new String[] { "ATIVO", "INATIVO", "BLOQUEADO" });
        adicionarCampo(pnlForm, gbc, 6, "Status:", cmbStatus);

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
        
        btnSalvar = new JButton("Salvar Alterações");
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

    private void preencherCampos() {
        txtNome.setText(cliente.getNomeCompleto());
        txtCPF.setText(cliente.getCpf());
        txtTelefone.setText(cliente.getTelefone());
        cmbTipoCliente.setSelectedItem(cliente.getTipoCliente().name());
        txtLimiteEmprestimos.setText(cliente.getLimiteEmprestimo().toString());
        cmbStatus.setSelectedItem(cliente.getStatusPessoa().name());
    }
    
    private void atualizarLimitePorTipo() {
        String tipoSelecionado = (String) cmbTipoCliente.getSelectedItem();
        if (tipoSelecionado == null) return;
        
        try {
            TipoCliente tipo = TipoCliente.valueOf(tipoSelecionado); 
            switch (tipo) {
                case ALUNO -> txtLimiteEmprestimos.setText("3");
                case PROFESSOR -> txtLimiteEmprestimos.setText("5");
                case VISITANTE -> txtLimiteEmprestimos.setText("1");
            }
        } catch(Exception e) {
            // ignore
        }
    }

    private void bloquearCampos() {
        txtCPF.setEnabled(false);
        txtLimiteEmprestimos.setEnabled(false); // limite automático
    }

    private void salvarAlteracoes() {
        String nome = txtNome.getText();
        String telefone = txtTelefone.getText();
        String limiteStr = txtLimiteEmprestimos.getText();

        if (nome.isEmpty()) throw new RuntimeException("Nome é obrigatório.");
        if (telefone.isEmpty()) throw new RuntimeException("Telefone é obrigatório.");
        if (limiteStr.isEmpty()) throw new RuntimeException("Limite de empréstimos é obrigatório.");
        
        try {
            cliente.setNomeCompleto(txtNome.getText());
            cliente.setTelefone(txtTelefone.getText());
            cliente.setTipoCliente(TipoCliente.valueOf((String) cmbTipoCliente.getSelectedItem()));
            cliente.setLimiteEmprestimo(Integer.parseInt(txtLimiteEmprestimos.getText()));
            cliente.setStatusPessoa(StatusPessoa.valueOf((String) cmbStatus.getSelectedItem()));

            controller.update(cliente);

            JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
