package br.com.ifba.biblioteca.cliente.view;

import br.com.ifba.biblioteca.cliente.controller.ClienteIController;
import br.com.ifba.biblioteca.cliente.entity.Cliente;
import br.com.ifba.biblioteca.cliente.entity.TipoCliente;
import br.com.ifba.biblioteca.pessoa.entity.StatusPessoa;
import javax.swing.JOptionPane;
import org.springframework.stereotype.Component;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@Component
public class ClienteAdicionar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ClienteAdicionar.class.getName());
    
    private final ClienteIController controller;
    
    // Componentes
    private JTextField txtNome, txtCPF, txtTelefone, txtLimiteEmprestimo;
    private JComboBox<String> cmbTipoCliente;
    private JButton btnSalvar, btnCancelar;

    public ClienteAdicionar(ClienteIController controller) {
        this.controller = controller;
        initComponents();
        configurarTela();
    }
    
    private void initComponents() {
        setTitle("Adicionar Cliente");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
        JLabel lblTitulo = new JLabel("ADICIONAR CLIENTE");
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
        adicionarCampo(pnlForm, gbc, 4, "Tipo de Cliente:", cmbTipoCliente);
        
        txtLimiteEmprestimo = new JTextField(5);
        txtLimiteEmprestimo.setEditable(false);
        adicionarCampo(pnlForm, gbc, 5, "Limite de Empréstimos:", txtLimiteEmprestimo);

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
        cmbTipoCliente.addActionListener(e -> atualizarLimiteEmprestimo());
        btnCancelar.addActionListener(e -> dispose());
        btnSalvar.addActionListener(e -> salvarCliente());
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

    private void configurarTela() {
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        atualizarLimiteEmprestimo();
    }
    
    private void atualizarLimiteEmprestimo() {
        String tipoSelecionado = (String) cmbTipoCliente.getSelectedItem();
        if (tipoSelecionado == null) return;

        TipoCliente tipo = TipoCliente.valueOf(tipoSelecionado);
        switch (tipo) {
            case ALUNO: txtLimiteEmprestimo.setText("3"); break;
            case PROFESSOR: txtLimiteEmprestimo.setText("5"); break;
            case VISITANTE: txtLimiteEmprestimo.setText("1"); break;
        }
    }
    
    private void salvarCliente(){
        try{
            if (txtNome.getText().isEmpty()) throw new RuntimeException("Nome Completo é obrigatório.");
            if (txtCPF.getText().isEmpty()) throw new RuntimeException("CPF é obrigatório.");
            if (txtTelefone.getText().isEmpty()) throw new RuntimeException("Telefone é obrigatório.");
            
            String cpf = txtCPF.getText();
            if (controller.verificarCpfExistente(cpf)) {
                JOptionPane.showMessageDialog(this, "CPF já cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            Cliente cliente = new Cliente();
            cliente.setNomeCompleto(txtNome.getText());
            cliente.setCpf(txtCPF.getText());
            cliente.setTelefone(txtTelefone.getText());
            
            String limiteStr = txtLimiteEmprestimo.getText();
            if (limiteStr.isEmpty()) throw new RuntimeException("Limite de Empréstimos é obrigatório");
            cliente.setLimiteEmprestimo(Integer.parseInt(limiteStr));
            
            String tipoSelecionado = (String) cmbTipoCliente.getSelectedItem();
            if (tipoSelecionado == null) throw new RuntimeException("Tipo de Cliente não selecionado");
            cliente.setTipoCliente(TipoCliente.valueOf(tipoSelecionado));
            cliente.setStatusPessoa(StatusPessoa.ATIVO);
            
            controller.save(cliente);
            
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
