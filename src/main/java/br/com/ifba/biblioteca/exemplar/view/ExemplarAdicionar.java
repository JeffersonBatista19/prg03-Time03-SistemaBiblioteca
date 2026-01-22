
package br.com.ifba.biblioteca.exemplar.view;
import br.com.ifba.biblioteca.exemplar.controller.ExemplarController;
import br.com.ifba.biblioteca.exemplar.entity.EstadoConservacao;
import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import br.com.ifba.biblioteca.exemplar.entity.StatusExemplar;
import javax.swing.SwingUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class ExemplarAdicionar extends javax.swing.JPanel {
    
    @Autowired
    private ExemplarController exemplarController;

    @Autowired
    private ExemplarListar exemplarListar; // para atualizar a tabela
    
    @Autowired
    private ApplicationContext context;



    
    public ExemplarAdicionar() {
        initComponents();
        txtIsbn.setEditable(false); 
        txtIsbn.setBackground(new java.awt.Color(230, 230, 230));
    }
    
    public void setExemplarListar(ExemplarListar exemplarListar) {
    this.exemplarListar = exemplarListar;
}
    
    public void setIsbnLivro(String isbn) {
    txtIsbn.setText(isbn);
}



   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtAutor = new javax.swing.JTextField();
        btnAutor = new javax.swing.JButton();
        btnCriar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        cmbStatus = new javax.swing.JComboBox<>();
        txtLocal = new javax.swing.JTextField();
        spnTombamento = new javax.swing.JSpinner();
        cmbConservacao = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtIsbn = new javax.swing.JTextField();
        btnLivro = new javax.swing.JButton();

        txtAutor.setBackground(new java.awt.Color(255, 255, 255));
        txtAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAutorActionPerformed(evt);
            }
        });

        btnAutor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAutor.setText("Buscar Autor");
        btnAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutorActionPerformed(evt);
            }
        });

        btnCriar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCriar.setText("Criar");
        btnCriar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCriarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel1.setText("ADICIONANDO EXEMPLAR");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Número Tombamento:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Estado Conservação:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Localização:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Status:");

        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        cmbStatus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DISPONIVEL", "EMPRESTADO", "RESERVADO", "INATIVO" }));
        cmbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStatusActionPerformed(evt);
            }
        });

        txtLocal.setBackground(new java.awt.Color(255, 255, 255));

        spnTombamento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        spnTombamento.setModel(new javax.swing.SpinnerNumberModel());

        cmbConservacao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbConservacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NOVO", "BOM", "REGULAR", "RUIM" }));
        cmbConservacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbConservacaoActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Livro ISBN:");

        txtIsbn.setBackground(new java.awt.Color(255, 255, 255));
        txtIsbn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIsbnActionPerformed(evt);
            }
        });

        btnLivro.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLivro.setText("Buscar Livro");
        btnLivro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLivroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLivro))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnTombamento, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbConservacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(btnCriar)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar)))
                .addContainerGap(101, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLivro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(spnTombamento, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbConservacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCriar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCriarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCriarActionPerformed
        try {
        // ler valores do formulário.
        int tombamento = (Integer) spnTombamento.getValue();
        String conservacaoStr = (String) cmbConservacao.getSelectedItem();
        String local = txtLocal.getText();
        String statusStr = (String) cmbStatus.getSelectedItem();
        String isbn = txtIsbn.getText(); // <<< ISBN do livro selecionado

        // validações.
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new RuntimeException("Selecione um livro antes de criar o exemplar.");
        }

        if (local == null || local.trim().isEmpty()) {
            throw new RuntimeException("Localização física é obrigatória.");
        }

        if (conservacaoStr == null || conservacaoStr.trim().isEmpty()) {
            throw new RuntimeException("Estado de conservação é obrigatório.");
        }

        if (statusStr == null || statusStr.trim().isEmpty()) {
            throw new RuntimeException("Status do exemplar é obrigatório.");
        }
        

        // conversões.
        EstadoConservacao conservacao = EstadoConservacao.valueOf(conservacaoStr);
        StatusExemplar status = StatusExemplar.valueOf(statusStr);

        // criar objeto.
        Exemplar exemplar = new Exemplar();
        exemplar.setNumeroTombamento(tombamento);
        exemplar.setConservacao(conservacao);
        exemplar.setLocalizacaoFisica(local);
        exemplar.setStatus(status);
        exemplar.setIsbnLivro(isbn); // vínculo lógico com Livro via ISBN.

  
        exemplarController.save(exemplar);

        // atualizar tabela.
        if (exemplarListar != null) {
            javax.swing.table.DefaultTableModel model =
                (javax.swing.table.DefaultTableModel) exemplarListar.getjTable1().getModel();

            model.addRow(new Object[]{
                tombamento,
                isbn,
                conservacaoStr,
                local,
                statusStr
            });
        }

        javax.swing.JOptionPane.showMessageDialog(this, "Exemplar criado com sucesso!");

        // fechar tela.
        javax.swing.SwingUtilities.getWindowAncestor(this).dispose();

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Erro ao criar exemplar: " + e.getMessage()
        );
    }
    }//GEN-LAST:event_btnCriarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cmbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbStatusActionPerformed

    private void cmbConservacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbConservacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbConservacaoActionPerformed

    private void txtAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAutorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAutorActionPerformed

    private void txtIsbnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIsbnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIsbnActionPerformed

    private void btnAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutorActionPerformed
        
    }//GEN-LAST:event_btnAutorActionPerformed

    private void btnLivroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLivroActionPerformed
        BuscarLivro tela = context.getBean(BuscarLivro.class); // Spring cria
        tela.setTelaExemplar(this); // injeta a tela manualmente
        tela.carregarLivros();      // carrega com service já injetado
        tela.setVisible(true);
    }//GEN-LAST:event_btnLivroActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAutor;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCriar;
    private javax.swing.JButton btnLivro;
    private javax.swing.JComboBox<String> cmbConservacao;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSpinner spnTombamento;
    private javax.swing.JTextField txtAutor;
    private javax.swing.JTextField txtIsbn;
    private javax.swing.JTextField txtLocal;
    // End of variables declaration//GEN-END:variables
}
