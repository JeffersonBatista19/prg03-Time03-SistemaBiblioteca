package br.com.ifba.biblioteca.multa.view;

import br.com.ifba.biblioteca.multa.controller.MultaIController;
import br.com.ifba.biblioteca.multa.entity.Multa;
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
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class MultaVisualizar extends JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MultaVisualizar.class.getName());

    @Autowired
    private MultaIController multaController;
    private Multa multa;
    private Long idMulta;

    // Componentes - Dados do Empréstimo
    private JSpinner spnId;
    private JTextField txtAluno, txtLivro;
    private JSpinner spnDataEmprestimo, spnDataPrevista, spnDataDevolucao;
    
    // Componentes - Dados da Multa
    private JSpinner spnDias;
    private JFormattedTextField ftdPorDia, ftdTotal;
    private JSpinner spnDataEmissao;
    private JComboBox<String> cmbStatus;
    
    private JButton btnVoltar;
    
    public MultaVisualizar() { 
        initComponents();
        bloquearCampos();
    }
    
    private void initComponents() {
        setTitle("Visualizar Multa");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- PAINEL PRINCIPAL COM SCROLL SE NECESSÁRIO (mas aqui usaremos GridBag direto) ---
        JPanel pnlContent = new JPanel(new GridBagLayout());
        pnlContent.setBackground(new Color(240, 242, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        
        // Título
        JLabel lblTitulo = new JLabel("VISUALIZAR MULTA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0; gbc.gridy = 0;
        pnlContent.add(lblTitulo, gbc);

        // Painel Dados do Empréstimo
        JPanel pnlEmprestimo = criarPainelEmprestimo();
        gbc.gridy = 1;
        pnlContent.add(pnlEmprestimo, gbc);

        // Painel Dados da Multa
        JPanel pnlMulta = criarPainelMulta();
        gbc.gridy = 2;
        pnlContent.add(pnlMulta, gbc);

        add(pnlContent, BorderLayout.CENTER);

        // --- BOTÕES ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlBotoes.setBackground(Color.WHITE);
        pnlBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(99, 110, 114));
        btnVoltar.addActionListener(e -> dispose());
        
        pnlBotoes.add(btnVoltar);
        add(pnlBotoes, BorderLayout.SOUTH);
    }
    
    private JPanel criarPainelEmprestimo() {
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBackground(Color.WHITE);
        pnl.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), "Dados do Empréstimo"
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Linha 1: ID
        gbc.gridx = 0; gbc.gridy = 0;
        pnl.add(new JLabel("ID Empréstimo:"), gbc);
        gbc.gridx = 1;
        spnId = new JSpinner();
        spnId.setPreferredSize(new java.awt.Dimension(100, 25));
        pnl.add(spnId, gbc);

        // Linha 2: Aluno
        gbc.gridx = 0; gbc.gridy = 1;
        pnl.add(new JLabel("Aluno:"), gbc);
        gbc.gridx = 1;
        txtAluno = new JTextField(30);
        pnl.add(txtAluno, gbc);

        // Linha 3: Livro
        gbc.gridx = 0; gbc.gridy = 2;
        pnl.add(new JLabel("Livro:"), gbc);
        gbc.gridx = 1;
        txtLivro = new JTextField(30);
        pnl.add(txtLivro, gbc);

        // Linha 4: Datas
        gbc.gridx = 0; gbc.gridy = 3;
        pnl.add(new JLabel("Data Empréstimo:"), gbc);
        gbc.gridx = 1;
        spnDataEmprestimo = new JSpinner(new SpinnerDateModel());
        spnDataEmprestimo.setEditor(new JSpinner.DateEditor(spnDataEmprestimo, "dd/MM/yyyy"));
        pnl.add(spnDataEmprestimo, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        pnl.add(new JLabel("Data Prevista:"), gbc);
        gbc.gridx = 1;
        spnDataPrevista = new JSpinner(new SpinnerDateModel());
        spnDataPrevista.setEditor(new JSpinner.DateEditor(spnDataPrevista, "dd/MM/yyyy"));
        pnl.add(spnDataPrevista, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        pnl.add(new JLabel("Data Devolução:"), gbc);
        gbc.gridx = 1;
        spnDataDevolucao = new JSpinner(new SpinnerDateModel());
        spnDataDevolucao.setEditor(new JSpinner.DateEditor(spnDataDevolucao, "dd/MM/yyyy"));
        pnl.add(spnDataDevolucao, gbc);

        // Status (do empréstimo ou da multa? No código original era status da multa aqui também, mas vou por no painel da Multa para ficar consistente com Editar)
        // No Visualizar original tinha Status no painel Empréstimo. Mantendo aqui por enquanto se for Status da Multa.
        
        return pnl;
    }

    private JPanel criarPainelMulta() {
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBackground(Color.WHITE);
        pnl.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), "Dados da Multa"
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dias de Atraso
        gbc.gridx = 0; gbc.gridy = 0;
        pnl.add(new JLabel("Dias de Atraso:"), gbc);
        gbc.gridx = 1;
        spnDias = new JSpinner();
        pnl.add(spnDias, gbc);

        // Valor por Dia
        gbc.gridx = 0; gbc.gridy = 1;
        pnl.add(new JLabel("Valor por Dia:"), gbc);
        gbc.gridx = 1;
        ftdPorDia = new JFormattedTextField(java.text.NumberFormat.getCurrencyInstance());
        ftdPorDia.setColumns(10);
        pnl.add(ftdPorDia, gbc);

        // Valor Total
        gbc.gridx = 0; gbc.gridy = 2;
        pnl.add(new JLabel("Valor Total:"), gbc);
        gbc.gridx = 1;
        ftdTotal = new JFormattedTextField(java.text.NumberFormat.getCurrencyInstance());
        ftdTotal.setColumns(10);
        pnl.add(ftdTotal, gbc);
        
        // Data Emissão
        gbc.gridx = 0; gbc.gridy = 3;
        pnl.add(new JLabel("Data Emissão:"), gbc);
        gbc.gridx = 1;
        spnDataEmissao = new JSpinner(new SpinnerDateModel());
        spnDataEmissao.setEditor(new JSpinner.DateEditor(spnDataEmissao, "dd/MM/yyyy"));
        pnl.add(spnDataEmissao, gbc);
        
        // Status
        gbc.gridx = 0; gbc.gridy = 4;
        pnl.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        cmbStatus = new JComboBox<>();
        pnl.add(cmbStatus, gbc);

        return pnl;
    }
    
    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }
    
    public void setMultaId(Long id){
        this.idMulta = id;
        carregarDados();
    }

    private void carregarDados() {
        if (idMulta == null) return;
        multa = multaController.findById(idMulta)
                .orElseThrow(() -> new RuntimeException("Multa não encontrada"));

        // dados do empréstimo.
        spnId.setValue(multa.getEmprestimo().getId());
        txtAluno.setText(multa.getEmprestimo().getCliente().getNomeCompleto());
        txtLivro.setText(multa.getEmprestimo().getExemplar().getIsbnLivro());

        spnDataEmprestimo.setValue(java.sql.Date.valueOf(
                multa.getEmprestimo().getDataEmprestimo()
        ));
        spnDataPrevista.setValue(java.sql.Date.valueOf(
                multa.getEmprestimo().getDataPrevistaDevolucao()
        ));
        if(multa.getEmprestimo().getDataDevolucao() != null){
            spnDataDevolucao.setValue(java.sql.Date.valueOf(
                multa.getEmprestimo().getDataDevolucao()
            ));
        }

        cmbStatus.addItem(multa.getStatus().name());
        cmbStatus.setSelectedItem(multa.getStatus().name());

        // dados da multa.
        spnDias.setValue(multa.getDiasAtraso());
        ftdPorDia.setValue(multa.getValorPorDia());
        ftdTotal.setValue(multa.getValorTotal());
        spnDataEmissao.setValue(java.sql.Date.valueOf(multa.getDataGeracao()));

        // bloqueio edição (visualizar).
        bloquearCampos();
    }

    private void bloquearCampos() {
        spnId.setEnabled(false);
        txtAluno.setEditable(false);
        txtLivro.setEditable(false);

        spnDataEmprestimo.setEnabled(false);
        spnDataPrevista.setEnabled(false);
        spnDataDevolucao.setEnabled(false);

        cmbStatus.setEnabled(false);

        spnDias.setEnabled(false);
        ftdPorDia.setEditable(false);
        ftdTotal.setEditable(false);
        spnDataEmissao.setEnabled(false);
    }
}
