package br.com.ifba.biblioteca.multa.view;

import br.com.ifba.biblioteca.BibliotecaApplication;
import br.com.ifba.biblioteca.multa.controller.MultaIController;
import br.com.ifba.biblioteca.multa.entity.Multa;
import br.com.ifba.biblioteca.multa.entity.StatusMulta;
import java.math.BigDecimal;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MultaEditar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MultaEditar.class.getName());

    private MultaIController multaController;
    private Multa multaAtual;
    
    public MultaEditar(Long idMulta) {
        initComponents();
        
        AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(BibliotecaApplication.class);
    this.multaController = context.getBean(MultaIController.class);

    configurarCombos();
    configurarTela();
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


    // campos que não vão poder ser editados.
     private void configurarTela() {
    spnId.setEnabled(false);           
    txtAluno.setEditable(false);       
    txtLivro.setEditable(false);      
    spnDataEmprestimo.setEnabled(false);
    spnDataPrevista.setEnabled(false);
    spnDataDevolucao.setEnabled(false);
    ftdTotal.setEditable(false);       
    cmbStatus.setEnabled(false);      
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
            javax.swing.JOptionPane.showMessageDialog(this, "Multa não encontrada!");
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
        javax.swing.JOptionPane.showMessageDialog(this, "Erro ao carregar multa: " + e.getMessage());
        e.printStackTrace();
        this.dispose();
    }
}


   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        spnId = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        txtAluno = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtLivro = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        spnDataEmprestimo = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        spnDataPrevista = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        spnDataDevolucao = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        spnDias = new javax.swing.JSpinner();
        jLabel17 = new javax.swing.JLabel();
        ftdPorDia = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        ftdTotal = new javax.swing.JFormattedTextField();
        btnVoltar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("EDITANDO MULTA");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Empréstimo"));
        jPanel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel9.setText("Empréstimo:");

        jLabel10.setText("Aluno:");

        txtAluno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlunoActionPerformed(evt);
            }
        });

        jLabel11.setText("Livro:");

        txtLivro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLivroActionPerformed(evt);
            }
        });

        jLabel12.setText("Data Empréstimo:");

        spnDataEmprestimo.setModel(new javax.swing.SpinnerDateModel());
        spnDataEmprestimo.setEditor(new javax.swing.JSpinner.DateEditor(spnDataEmprestimo, "dd/MM/yyyy"));

        jLabel13.setText("Data Prevista:");

        spnDataPrevista.setModel(new javax.swing.SpinnerDateModel());
        spnDataPrevista.setEditor(new javax.swing.JSpinner.DateEditor(spnDataPrevista, "dd/MM/yyyy"));

        jLabel14.setText("Data Devolução:");

        spnDataDevolucao.setModel(new javax.swing.SpinnerDateModel());
        spnDataDevolucao.setEditor(new javax.swing.JSpinner.DateEditor(spnDataDevolucao, "dd/MM/yyyy"));

        jLabel15.setText("Status:");

        cmbStatus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PENDENTE", "PAGA", "CANCELADA" }));
        cmbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAluno))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtLivro))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnId, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnDataEmprestimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnDataPrevista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(spnDataDevolucao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 166, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(spnId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtAluno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtLivro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(spnDataEmprestimo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(spnDataPrevista, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(spnDataDevolucao, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Cálculo da Multa"));

        jLabel16.setText("Dias de atraso:");

        spnDias.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel17.setText("Valor por Dia:");

        ftdPorDia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        ftdPorDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftdPorDiaActionPerformed(evt);
            }
        });

        jLabel18.setText("Valor por Dia:");

        ftdTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        ftdTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftdTotalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ftdPorDia))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnDias, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ftdTotal)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(spnDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(ftdPorDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(ftdTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(133, 133, 133))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnSalvar)
                                .addGap(67, 67, 67)
                                .addComponent(btnVoltar)
                                .addGap(97, 97, 97))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar)
                    .addComponent(btnSalvar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ftdPorDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftdPorDiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftdPorDiaActionPerformed

    private void ftdTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftdTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftdTotalActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
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

            javax.swing.JOptionPane.showMessageDialog(this, "Multa atualizada com sucesso!");
            this.dispose();
        }
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Erro ao salvar multa: " + e.getMessage());
    }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void cmbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbStatusActionPerformed

    private void txtLivroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLivroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLivroActionPerformed

    private void txtAlunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlunoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlunoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JFormattedTextField ftdPorDia;
    private javax.swing.JFormattedTextField ftdTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSpinner spnDataDevolucao;
    private javax.swing.JSpinner spnDataEmprestimo;
    private javax.swing.JSpinner spnDataPrevista;
    private javax.swing.JSpinner spnDias;
    private javax.swing.JSpinner spnId;
    private javax.swing.JTextField txtAluno;
    private javax.swing.JTextField txtLivro;
    // End of variables declaration//GEN-END:variables
}
