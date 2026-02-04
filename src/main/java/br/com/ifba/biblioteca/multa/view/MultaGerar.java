package br.com.ifba.biblioteca.multa.view;

import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import br.com.ifba.biblioteca.multa.controller.MultaIController;
import br.com.ifba.biblioteca.multa.entity.Multa;
import br.com.ifba.biblioteca.multa.entity.StatusMulta;
import java.math.BigDecimal;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;

@Component
@Scope("prototype")
public class MultaGerar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MultaGerar.class.getName());

    @Autowired
    private MultaIController multaController;
    
    @Autowired
    private ApplicationContext context;
    
    private Emprestimo emprestimo;

    // Components
    private JTextField txtAluno, txtLivro;
    private JSpinner spnId, spnDataEmprestimo, spnDataPrevista, spnDataDevolucao, spnDias;
    private JFormattedTextField ftdPorDia, ftdTotal;
    private JComboBox<StatusMulta> cmbStatus;
    private JButton btnConfirmar, btnCancelar;

    public MultaGerar() {
        initComponents();
        configurarTela();
        configurarCombos();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }
    
    public void setMultaController(MultaIController controller){
        this.multaController = controller;
    }

    public void setEmprestimo(Emprestimo emp){
        this.emprestimo = emp;
        carregarEmprestimoTela(emp);
    }
    
    private void initComponents() {
        setTitle("Gerar Multa");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- PAINEL CENTRAL ---
        JPanel pnlCenter = new JPanel(new GridBagLayout());
        pnlCenter.setBackground(Color.WHITE);
        pnlCenter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Title
        JLabel lblTitulo = new JLabel("GERAR MULTA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlCenter.add(lblTitulo, gbc);
        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // --- DADOS DO EMPRESTIMO ---
        JLabel lblSecao1 = new JLabel("DADOS DO EMPRÉSTIMO");
        lblSecao1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSecao1.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        pnlCenter.add(lblSecao1, gbc);
        gbc.gridwidth = 1;
        
        adicionarCampo(pnlCenter, gbc, 2, "ID Empréstimo:", spnId = new JSpinner());
        adicionarCampo(pnlCenter, gbc, 3, "Aluno:", txtAluno = new JTextField(30));
        adicionarCampo(pnlCenter, gbc, 4, "Livro:", txtLivro = new JTextField(30));
        
        // Dates
        spnDataEmprestimo = criarSpinnerData();
        adicionarCampo(pnlCenter, gbc, 5, "Data Empréstimo:", spnDataEmprestimo);
        
        spnDataPrevista = criarSpinnerData();
        adicionarCampo(pnlCenter, gbc, 6, "Data Prevista:", spnDataPrevista);
        
        spnDataDevolucao = criarSpinnerData();
        adicionarCampo(pnlCenter, gbc, 7, "Data Devolução:", spnDataDevolucao);
        
        cmbStatus = new JComboBox<>();
        adicionarCampo(pnlCenter, gbc, 8, "Status:", cmbStatus);

        // --- CALCULO DA MULTA ---
        JLabel lblSecao2 = new JLabel("CÁLCULO DA MULTA");
        lblSecao2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSecao2.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10); // More space above
        pnlCenter.add(lblSecao2, gbc);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        spnDias = new JSpinner();
        adicionarCampo(pnlCenter, gbc, 10, "Dias de Atraso:", spnDias);
        
        ftdPorDia = new JFormattedTextField(new java.text.DecimalFormat("#,##0.00"));
        ftdPorDia.setColumns(10);
        adicionarCampo(pnlCenter, gbc, 11, "Valor por Dia (R$):", ftdPorDia);
        
        ftdTotal = new JFormattedTextField(new java.text.DecimalFormat("#,##0.00"));
        ftdTotal.setColumns(10);
        ftdTotal.setEditable(false);
        ftdTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        adicionarCampo(pnlCenter, gbc, 12, "Valor Total (R$):", ftdTotal);

        // Container
        JPanel pnlCenterContainer = new JPanel(new GridBagLayout());
        pnlCenterContainer.setBackground(new Color(240, 242, 245));
        pnlCenterContainer.add(pnlCenter);
        add(pnlCenterContainer, BorderLayout.CENTER);

        // --- BOTOES ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlBotoes.setBackground(Color.WHITE);
        pnlBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        btnCancelar = new JButton("Cancelar");
        estilizarBotao(btnCancelar, new Color(99, 110, 114));
        
        btnConfirmar = new JButton("Gerar Multa");
        estilizarBotao(btnConfirmar, new Color(46, 204, 113));

        pnlBotoes.add(btnCancelar);
        pnlBotoes.add(btnConfirmar);
        add(pnlBotoes, BorderLayout.SOUTH);
        
        // Listeners
        spnDias.addChangeListener(e -> calcularTotal());
        ftdPorDia.addPropertyChangeListener("value", e -> calcularTotal()); 
        // Note: PropertyChangeListener is better for formatted text fields
        
        btnCancelar.addActionListener(e -> dispose());
        btnConfirmar.addActionListener(e -> confirmarMulta());
    }
    
    private JSpinner criarSpinnerData() {
        JSpinner spinner = new JSpinner(new javax.swing.SpinnerDateModel());
        spinner.setEditor(new JSpinner.DateEditor(spinner, "dd/MM/yyyy"));
        return spinner;
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
        txtAluno.setEditable(false);
        txtLivro.setEditable(false);
        spnId.setEnabled(false);
        spnDataEmprestimo.setEnabled(false);
        spnDataPrevista.setEnabled(false);
        spnDataDevolucao.setEnabled(false);
        cmbStatus.setEnabled(false);
    }
    
    private void configurarCombos() {
       cmbStatus.setModel(new DefaultComboBoxModel<>(StatusMulta.values()));
    }
    
    private void calcularTotal() {
        try {
            int dias = (int) spnDias.getValue();
            Number n = (Number) ftdPorDia.getValue();
            if (n == null) return;

            BigDecimal valorDia = new BigDecimal(n.toString());
            BigDecimal total = valorDia.multiply(BigDecimal.valueOf(dias));

            ftdTotal.setValue(total);
        } catch (Exception e) {
             // System.out.println("Erro calc: " + e.getMessage());
        }
    }
    
    public void carregarEmprestimoTela(Emprestimo emp) {
        this.emprestimo = emp;

        spnId.setValue(emp.getId());
        txtAluno.setText(emp.getCliente() != null ? emp.getCliente().getNomeCompleto() : "N/A");
        txtLivro.setText((emp.getExemplar() != null) ? emp.getExemplar().getIsbnLivro() : "N/A"); // Ajuste para evitar NPE

        if(emp.getDataEmprestimo() != null)
             spnDataEmprestimo.setValue(java.sql.Date.valueOf(emp.getDataEmprestimo()));
        
        if(emp.getDataPrevistaDevolucao() != null)
            spnDataPrevista.setValue(java.sql.Date.valueOf(emp.getDataPrevistaDevolucao()));

        if (emp.getDataDevolucao() != null) {
            spnDataDevolucao.setValue(java.sql.Date.valueOf(emp.getDataDevolucao()));
        }

        // valores da multa.
        int diasAtraso = calcularDiasAtraso(emp);
        spnDias.setValue(diasAtraso);

        ftdPorDia.setValue(10.00); // R$ 10 padrão.

        calcularTotal(); 

        cmbStatus.setSelectedItem(StatusMulta.PENDENTE);
    }
    
    private int calcularDiasAtraso(Emprestimo emp) {
        if (emp.getDataDevolucao() == null || emp.getDataPrevistaDevolucao() == null) {
            return 0;
        }

        long diff = java.time.temporal.ChronoUnit.DAYS.between(
            emp.getDataPrevistaDevolucao(),
            emp.getDataDevolucao()
        );

        return (int) Math.max(diff, 0);
    }

    private void confirmarMulta() {
        try {
            int dias = (int) spnDias.getValue();
            Number n = (Number) ftdPorDia.getValue();
            BigDecimal valorPorDia = new BigDecimal(n.toString());

            Multa multa = multaController.gerarMultaManual(
                emprestimo, dias, valorPorDia
            );

            JOptionPane.showMessageDialog(this,
                "Multa gerada com sucesso!\nID: " + multa.getId());
            
            //  abre a tela de listar multas
            MultaListar tela = context.getBean(MultaListar.class);
            tela.carregarTela();
            tela.setLocationRelativeTo(null);
            tela.setVisible(true);

            this.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao gerar multa: " + e.getMessage());
        }
    }
}
