package br.com.ifba.biblioteca.livro.view;

import javax.swing.table.DefaultTableModel;

public class BuscarAutor extends javax.swing.JFrame {
    
     // referência da tela que chamou (LivroAdicionar)
    private LivroAdicionar telaLivro;
    private LivroEditar telaEditar;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BuscarAutor.class.getName());
    
    public BuscarAutor(LivroAdicionar telaLivro) {
        this.telaLivro = telaLivro;
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        carregarAutoresFake();
        setLocationRelativeTo(null);
    }
    
     public BuscarAutor(LivroEditar telaEditar) {
        this.telaEditar = telaEditar;
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        carregarAutoresFake();
        setLocationRelativeTo(null);
    }

     private void carregarAutoresFake() {
        DefaultTableModel model = (DefaultTableModel) tblAutores.getModel();
        model.setRowCount(0);

        model.addRow(new Object[]{1, "George Orwell", "Inglês", "Autor de 1984", 6});
        model.addRow(new Object[]{2, "Machado de Assis", "Brasileiro", "Realismo", 15});
        model.addRow(new Object[]{3, "J. K. Rowling", "Britânica", "Harry Potter", 7});
    }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnBuscarLivro = new javax.swing.JButton();
        btnCadastrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spnID = new javax.swing.JSpinner();
        btnBuscarId = new javax.swing.JButton();
        btnLimparID = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAutores = new javax.swing.JTable();
        btnSelecionar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();

        btnBuscarLivro.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscarLivro.setText("Buscar");

        btnCadastrar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCadastrar.setText("Cadastrar");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("BUSCANDO AUTOR");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel4.setText("ID:");

        spnID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        btnBuscarId.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscarId.setText("Buscar");
        btnBuscarId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarIdActionPerformed(evt);
            }
        });

        btnLimparID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLimparID.setText("Limpar");
        btnLimparID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparIDActionPerformed(evt);
            }
        });

        tblAutores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Nacionalidade", "Biografia", "Qtd de obras"
            }
        ));
        jScrollPane1.setViewportView(tblAutores);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnID, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscarId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(btnLimparID))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSelecionar)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAtualizar)
                        .addGap(16, 16, 16))))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(spnID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarId)
                    .addComponent(btnLimparID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelecionar)
                    .addComponent(btnCancelar)
                    .addComponent(btnAtualizar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarActionPerformed
        int linhaSelecionada = tblAutores.getSelectedRow();

    if (linhaSelecionada == -1) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Selecione um autor na tabela.",
            "Aviso",
            javax.swing.JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    Long idAutor = Long.valueOf(
        tblAutores.getValueAt(linhaSelecionada, 0).toString()
    );

    telaLivro.setAutorSelecionado(idAutor);
    dispose();
    }//GEN-LAST:event_btnSelecionarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnBuscarIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarIdActionPerformed
        int id = (int) spnID.getValue();

    DefaultTableModel model = (DefaultTableModel) tblAutores.getModel();
    model.setRowCount(0);

    if (id == 1) {
        model.addRow(new Object[]{1, "George Orwell", "Inglês", "Autor de 1984", 6});
    } else if (id == 2) {
        model.addRow(new Object[]{2, "Machado de Assis", "Brasileiro", "Realismo", 15});
    } else if (id == 3) {
        model.addRow(new Object[]{3, "J. K. Rowling", "Britânica", "Harry Potter", 7});
    }
    }//GEN-LAST:event_btnBuscarIdActionPerformed

    private void btnLimparIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparIDActionPerformed
        spnID.setValue(0);
        carregarAutoresFake();
    }//GEN-LAST:event_btnLimparIDActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        carregarAutoresFake();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnBuscarId;
    private javax.swing.JButton btnBuscarLivro;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnLimparID;
    private javax.swing.JButton btnSelecionar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spnID;
    private javax.swing.JTable tblAutores;
    // End of variables declaration//GEN-END:variables
}
