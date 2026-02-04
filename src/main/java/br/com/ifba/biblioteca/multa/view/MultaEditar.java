package br.com.ifba.biblioteca.multa.view;

import br.com.ifba.biblioteca.multa.controller.MultaIController;
import br.com.ifba.biblioteca.multa.entity.Multa;
import br.com.ifba.biblioteca.multa.entity.StatusMulta;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class MultaEditar extends JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MultaEditar.class.getName());

    @Autowired
    private MultaIController multaController;
    private Multa multaAtual;

    // Componentes - Dados do Empréstimo
    private JSpinner spnId;
    private JTextField txtAluno, txtLivro;
    private JSpinner spnDataEmprestimo, spnDataPrevista, spnDataDevolucao;
    
    // Componentes - Cálculo da Multa
    private JSpinner spnDias;
    private JFormattedTextField ftdPorDia, ftdTotal;
    private JComboBox<String> cmbStatus;
    
    private JButton btnSalvar, btnVoltar;
    
    public MultaEditar() {
        initComponents();
        configurarCombos();
        configurarTela();
    }
    
    private void initComponents() {
        setTitle("Editar Multa");
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
        JLabel lblTitulo = new JLabel("EDITAR MULTA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0; gbc.gridy = 0;
        pnlContent.add(lblTitulo, gbc);

        // Painel Dados do Empréstimo
        JPanel pnlEmprestimo = criarPainelEmprestimo();
        gbc.gridy = 1;
        pnlContent.add(pnlEmprestimo, gbc);

        // Painel Cálculo
        JPanel pnlCalculo = criarPainelCalculo();
        gbc.gridy = 2;
        pnlContent.add(pnlCalculo, gbc);

        add(pnlContent, BorderLayout.CENTER);

        // --- BOTÕES ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlBotoes.setBackground(Color.WHITE);
        pnlBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(99, 110, 114));
        btnVoltar.addActionListener(e -> dispose());
        
        btnSalvar = new JButton("Salvar");
        estilizarBotao(btnSalvar, new Color(46, 204, 113));
        btnSalvar.addActionListener(e -> salvarMulta());

        pnlBotoes.add(btnVoltar);
        pnlBotoes.add(btnSalvar);
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

        return pnl;
    }

    private JPanel criarPainelCalculo() {
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBackground(Color.WHITE);
        pnl.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), "Cálculo da Multa"
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dias de Atraso
        gbc.gridx = 0; gbc.gridy = 0;
        pnl.add(new JLabel("Dias de Atraso:"), gbc);
        gbc.gridx = 1;
        spnDias = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
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
        ftdTotal.setEditable(false);
        pnl.add(ftdTotal, gbc);
        
        // Status
        gbc.gridx = 0; gbc.gridy = 3;
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

    public void setMultaId(Long id) {
        carregarMulta(id);
    }
    
    private void configurarCombos() {
        String[] nomesStatus = Arrays.stream(StatusMulta.values())
                                     .map(Enum::name)
                                     .toArray(String[]::new);
    
        cmbStatus.setModel(new DefaultComboBoxModel<>(nomesStatus));
        spnDias.addChangeListener(e -> calcularTotal());
        ftdPorDia.addPropertyChangeListener("value", evt -> calcularTotal());
    }

    private void configurarTela() {
        spnId.setEnabled(false);           
        txtAluno.setEditable(false);       
        txtLivro.setEditable(false);      
        spnDataEmprestimo.setEnabled(false);
        spnDataPrevista.setEnabled(false);
        spnDataDevolucao.setEnabled(false);
        ftdTotal.setEditable(false); // Read-only, calculado automático
        cmbStatus.setEnabled(true);
    }
     
    private void calcularTotal() {
        try {
            int dias = (int) spnDias.getValue();
            Number valorNum = (Number) ftdPorDia.getValue();
            if (valorNum == null) return;

            double valorDia = valorNum.doubleValue();
            double total = dias * valorDia;
            ftdTotal.setValue(total);

        } catch (Exception e) {
            // silencioso
        }
    }

    private void carregarMulta(Long id) {
        try {
            multaAtual = multaController.findById(id).orElse(null);
    
            if (multaAtual == null) {
                JOptionPane.showMessageDialog(this, "Multa não encontrada!");
                this.dispose();
                return;
            }
    
            // dados da multa.
            spnId.setValue(multaAtual.getId());
            spnDias.setValue(multaAtual.getDiasAtraso());
            ftdPorDia.setValue(multaAtual.getValorPorDia());
            ftdTotal.setValue(multaAtual.getValorTotal());
            cmbStatus.setSelectedItem(multaAtual.getStatus().name());
    
            // dados do empréstimo.
            if (multaAtual.getEmprestimo() != null) {
    
                // aluno.
                if (multaAtual.getEmprestimo().getCliente() != null) {
                    txtAluno.setText(
                        multaAtual.getEmprestimo().getCliente().getNomeCompleto()
                    );
                }
    
                // Livro (ISBN, porque não existe objeto Livro no Exemplar).
                if (multaAtual.getEmprestimo().getExemplar() != null) {
                    txtLivro.setText(
                        multaAtual.getEmprestimo().getExemplar().getIsbnLivro()
                    );
                }
    
                // Datas.
                if (multaAtual.getEmprestimo().getDataEmprestimo() != null) {
                    spnDataEmprestimo.setValue(
                        java.sql.Date.valueOf(
                            multaAtual.getEmprestimo().getDataEmprestimo()
                        )
                    );
                }
    
                if (multaAtual.getEmprestimo().getDataPrevistaDevolucao() != null) {
                    spnDataPrevista.setValue(
                        java.sql.Date.valueOf(
                            multaAtual.getEmprestimo().getDataPrevistaDevolucao()
                        )
                    );
                }
    
                if (multaAtual.getEmprestimo().getDataDevolucao() != null) {
                    spnDataDevolucao.setValue(
                        java.sql.Date.valueOf(
                            multaAtual.getEmprestimo().getDataDevolucao()
                        )
                    );
                }
            }
    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar multa: " + e.getMessage());
            e.printStackTrace();
            this.dispose();
        }
    }
    
    private void salvarMulta() {
        try {
            if (multaAtual != null) {
                multaAtual.setDiasAtraso((int) spnDias.getValue());
                Number valorNum = (Number) ftdPorDia.getValue();
                multaAtual.setValorPorDia(BigDecimal.valueOf(valorNum.doubleValue()));
                multaAtual.setValorTotal(BigDecimal.valueOf(((Number) ftdTotal.getValue()).doubleValue()));
    
                // Aqui salvamos o status.
                String selecionado = (String) cmbStatus.getSelectedItem();
                multaAtual.setStatus(StatusMulta.valueOf(selecionado));
    
                multaController.update(multaAtual);
    
                JOptionPane.showMessageDialog(this, "Multa atualizada com sucesso!");
                this.dispose();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar multa: " + e.getMessage());
        }
    }
}
