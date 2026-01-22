package br.com.ifba.biblioteca.livro.view;

import javax.swing.table.DefaultTableModel;

public class BuscarEditora extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BuscarEditora.class.getName());
    private LivroAdicionar telaLivro;
    private LivroEditar telaEditar;

    
    public BuscarEditora(LivroAdicionar telaLivro) {
        this.telaLivro = telaLivro;
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        carregarEditorasFake();
        setLocationRelativeTo(null);
    }
    
    public BuscarEditora(LivroEditar telaEditar) {
        this.telaEditar = telaEditar;
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        carregarEditorasFake();
        setLocationRelativeTo(null);
    }

    private void carregarEditorasFake() {
    DefaultTableModel model = (DefaultTableModel) tblEditoras.getModel();
    model.setRowCount(0);

    model.addRow(new Object[]{1, "12.345.678/0001-00", "Companhia das Letras", "(11) 3333-4444", "São Paulo"});
    model.addRow(new Object[]{2, "98.765.432/0001-99", "Editora Abril", "(11) 9999-8888", "São Paulo"});
    model.addRow(new Object[]{3, "11.222.333/0001-55", "Rocco", "(21) 2222-3333", "Rio de Janeiro"});
}

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnBuscarId = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spnID = new javax.swing.JSpinner();
        btnBuscarId1 = new javax.swing.JButton();
        btnLimparID = new javax.swing.JButton();
        btnSelecionar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEditoras = new javax.swing.JTable();

        btnBuscarId.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscarId.setText("Buscar");
        btnBuscarId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarIdActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("BUSCANDO EDITORA");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel4.setText("ID:");

        spnID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        btnBuscarId1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscarId1.setText("Buscar");
        btnBuscarId1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarId1ActionPerformed(evt);
            }
        });

        btnLimparID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLimparID.setText("Limpar");
        btnLimparID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparIDActionPerformed(evt);
            }
        });

        btnSelecionar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSelecionar.setText("Selecionar");
        btnSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnAtualizar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAtualizar.setText("Atualizar");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        tblEditoras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CNPJ", "Nome", "Telefone", "Endereço"
            }
        ));
        jScrollPane1.setViewportView(tblEditoras);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(112, 112, 112))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnID, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscarId1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLimparID))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSelecionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAtualizar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(spnID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarId1)
                    .addComponent(btnLimparID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelecionar)
                    .addComponent(btnCancelar)
                    .addComponent(btnAtualizar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarIdActionPerformed
        
    }//GEN-LAST:event_btnBuscarIdActionPerformed

    private void btnBuscarId1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarId1ActionPerformed
        int id = (int) spnID.getValue();

        DefaultTableModel model = (DefaultTableModel) tblEditoras.getModel();
        model.setRowCount(0);

        if (id == 1) {
            model.addRow(new Object[]{1, "George Orwell", "Inglês", "Autor de 1984", 6});
        } else if (id == 2) {
            model.addRow(new Object[]{2, "Machado de Assis", "Brasileiro", "Realismo", 15});
        } else if (id == 3) {
            model.addRow(new Object[]{3, "J. K. Rowling", "Britânica", "Harry Potter", 7});
        }
    }//GEN-LAST:event_btnBuscarId1ActionPerformed

    private void btnLimparIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparIDActionPerformed
        spnID.setValue(0);
        carregarEditorasFake();
    }//GEN-LAST:event_btnLimparIDActionPerformed

    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarActionPerformed
        int linha = tblEditoras.getSelectedRow();

    if (linha == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Selecione uma editora.");
        return;
    }

    Long idEditora = Long.valueOf(
        tblEditoras.getValueAt(linha, 0).toString()
    );

    telaLivro.setEditoraSelecionada(idEditora);
    dispose();
    }//GEN-LAST:event_btnSelecionarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        carregarEditorasFake();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnBuscarId;
    private javax.swing.JButton btnBuscarId1;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnLimparID;
    private javax.swing.JButton btnSelecionar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spnID;
    private javax.swing.JTable tblEditoras;
    // End of variables declaration//GEN-END:variables
}
