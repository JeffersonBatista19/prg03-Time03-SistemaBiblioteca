package br.com.ifba.biblioteca.multa.view;

import br.com.ifba.biblioteca.multa.controller.MultaIController;
import br.com.ifba.biblioteca.multa.entity.Multa;
import br.com.ifba.biblioteca.multa.entity.StatusMulta;
import br.com.ifba.biblioteca.BibliotecaApplication;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class MultaListar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MultaListar.class.getName());
    
    @Autowired
    private MultaIController multaController;
    
    @Autowired
    private ApplicationContext context;


    private DefaultTableModel tableModel;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    
    public MultaListar() {
        initComponents();
    }
    
    public void carregarTela() {
    configurarCombos();
    configurarTabela();
    carregarMultas();
}

    public void setMultaController(MultaIController multaController) {
    this.multaController = multaController;
}

      private void configurarCombos() {
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

    model.addElement("TODOS"); // opção para não filtrar.

    for (StatusMulta status : StatusMulta.values()) {
        model.addElement(status.name()); // enum para String
    }

    cmbStatus.setModel(model);
    cmbStatus.setSelectedIndex(0);
}
    
   private void configurarTabela() {
        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Aluno", "Livro", "Empréstimo", "Dias Atraso", "Valor", "Data", "Status"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabela só leitura
            }
        };
        jTable1.setModel(tableModel);
    }
   
   private void carregarMultas() {
    tableModel.setRowCount(0);

    List<Multa> todas = multaController.findAll();

    if (todas == null) return;

    for (Multa m : todas) {

        if (m.getEmprestimo() == null) continue;
        if (m.getEmprestimo().getCliente() == null) continue;
        if (m.getEmprestimo().getExemplar() == null) continue;

        tableModel.addRow(new Object[]{
            m.getId(),
            m.getEmprestimo().getCliente().getNomeCompleto(),
            m.getEmprestimo().getExemplar().getIsbnLivro(),
            m.getEmprestimo().getId(),
            m.getDiasAtraso(),
            m.getValorTotal(),
            m.getDataGeracao().format(dtf),
            m.getStatus()
        });
    }
}



    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtAluno = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtLivro = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        spnDataInicio = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        spnDataFim = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        ftdValorMin = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        ftdValorMax = new javax.swing.JFormattedTextField();
        btnBuscar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnVisualizar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();

        jLabel4.setText("Livro:");

        jLabel8.setText("Status:");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("GESTÃO DE MULTAS");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtrar"));

        jLabel3.setText("Aluno:");

        txtAluno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlunoActionPerformed(evt);
            }
        });

        jLabel5.setText("Livro:");

        txtLivro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLivroActionPerformed(evt);
            }
        });

        jLabel9.setText("Status:");

        cmbStatus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PENDENTE", "PAGA", "CANCELADA" }));
        cmbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStatusActionPerformed(evt);
            }
        });

        jLabel2.setText("Data:");

        spnDataInicio.setModel(new javax.swing.SpinnerDateModel());
        spnDataInicio.setEditor(new javax.swing.JSpinner.DateEditor(spnDataInicio, "dd/MM/yyyy"));

        jLabel6.setText("até");

        spnDataFim.setModel(new javax.swing.SpinnerDateModel());
        spnDataFim.setEditor(new javax.swing.JSpinner.DateEditor(spnDataFim, "dd/MM/yyyy"));

        jLabel7.setText("Valor:");

        ftdValorMin.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        ftdValorMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftdValorMinActionPerformed(evt);
            }
        });

        jLabel10.setText("até");

        ftdValorMax.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        ftdValorMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftdValorMaxActionPerformed(evt);
            }
        });

        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnLimpar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ftdValorMin)
                                    .addComponent(spnDataInicio))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(spnDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ftdValorMax)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtLivro))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAluno, javax.swing.GroupLayout.DEFAULT_SIZE, 757, Short.MAX_VALUE)))
                        .addGap(9, 9, 9))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtAluno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtLivro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(spnDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(spnDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(ftdValorMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(ftdValorMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar)
                    .addComponent(btnLimpar))
                .addContainerGap())
        );

        jTable1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Aluno", "Livro", "Empréstimo", "Dias Atraso", "Valor", "Data", "Status"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnVisualizar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnVisualizar.setText("Visualizar");
        btnVisualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualizarActionPerformed(evt);
            }
        });

        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnVisualizar)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(352, 352, 352)
                                .addComponent(jLabel1)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVisualizar)
                    .addComponent(btnEditar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAlunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlunoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlunoActionPerformed

    private void txtLivroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLivroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLivroActionPerformed

    private void cmbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbStatusActionPerformed

    private void ftdValorMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftdValorMinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftdValorMinActionPerformed

    private void ftdValorMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftdValorMaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftdValorMaxActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
        tableModel.setRowCount(0); // limpa tabela

        String alunoFiltro = txtAluno.getText().trim();
        String livroFiltro = txtLivro.getText().trim();

        // status.
        String statusSelecionado = (String) cmbStatus.getSelectedItem();
        StatusMulta statusFiltro = null;

        if (statusSelecionado != null && !statusSelecionado.equals("TODOS")) {
            statusFiltro = StatusMulta.valueOf(statusSelecionado);
        }

        List<Multa> todas = multaController.findAll();

        for (Multa m : todas) {
            boolean passa = true;

            String nomeAluno = m.getEmprestimo().getCliente().getNomeCompleto();
            String isbnLivro = m.getEmprestimo().getExemplar().getIsbnLivro();

            // filtro aluno.
            if (!alunoFiltro.isEmpty() &&
                !nomeAluno.toLowerCase().contains(alunoFiltro.toLowerCase())) {
                passa = false;
            }

            // filtro livro.
            if (!livroFiltro.isEmpty() &&
                !isbnLivro.toLowerCase().contains(livroFiltro.toLowerCase())) {
                passa = false;
            }

            // filtro status.
            if (statusFiltro != null && m.getStatus() != statusFiltro) {
                passa = false;
            }

            // passou nos filtros.
            if (passa) {
                tableModel.addRow(new Object[]{
                    m.getId(),
                    m.getEmprestimo().getCliente().getNomeCompleto(),
                    m.getEmprestimo().getExemplar().getIsbnLivro(),
                    m.getEmprestimo().getId(),
                    m.getDiasAtraso(),
                    m.getValorTotal(),
                    m.getDataGeracao().format(dtf),
                    m.getStatus()
                });
            }
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao filtrar multas: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnBuscarActionPerformed

    // botão pra zerar os campos.
    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        txtAluno.setText("");
    txtLivro.setText("");
    cmbStatus.setSelectedIndex(0);
    spnDataInicio.setValue(new java.util.Date());
    spnDataFim.setValue(new java.util.Date());
    ftdValorMin.setValue(null);
    ftdValorMax.setValue(null);
    carregarMultas();
    }//GEN-LAST:event_btnLimparActionPerformed

    // botão pra apenas vizualizar a multa completa.
    private void btnVisualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarActionPerformed
        int linha = jTable1.getSelectedRow();
    if (linha < 0) {
        JOptionPane.showMessageDialog(this, "Selecione uma multa para visualizar!");
        return;
    }
    Long id = (Long) jTable1.getValueAt(linha, 0);
   

MultaVisualizar tela = context.getBean(MultaVisualizar.class);
tela.setMultaId(id);
tela.setLocationRelativeTo(null);
tela.setVisible(true);

    }//GEN-LAST:event_btnVisualizarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int linha = jTable1.getSelectedRow();
    if (linha < 0) {
        JOptionPane.showMessageDialog(this, "Selecione uma multa para editar!");
        return;
    }

    Long id = (Long) jTable1.getValueAt(linha, 0);

    MultaEditar tela = context.getBean(MultaEditar.class);
    tela.setMultaId(id);   // método setter.
    tela.setLocationRelativeTo(null);
    tela.setVisible(true);
    }//GEN-LAST:event_btnEditarActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnVisualizar;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JFormattedTextField ftdValorMax;
    private javax.swing.JFormattedTextField ftdValorMin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JSpinner spnDataFim;
    private javax.swing.JSpinner spnDataInicio;
    private javax.swing.JTextField txtAluno;
    private javax.swing.JTextField txtLivro;
    // End of variables declaration//GEN-END:variables
}
