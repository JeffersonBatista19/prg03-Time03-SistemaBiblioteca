package br.com.ifba.biblioteca.livro.view;

import javax.swing.table.DefaultTableModel;
import br.com.ifba.biblioteca.editora.entity.Editora;
import br.com.ifba.biblioteca.editora.service.EditoraService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


public class BuscarEditora extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BuscarEditora.class.getName());
    private LivroAdicionar telaLivro;
    private LivroEditar telaEditar;
    
    @Autowired
    private EditoraService editoraService;


    // construtor que recebe a tela adicionar e a editora.
    public BuscarEditora(LivroAdicionar telaLivro, EditoraService editoraService) {
       this.telaLivro = telaLivro;
    this.editoraService = editoraService;
    initComponents();
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    carregarEditoras();
    setLocationRelativeTo(null);
    }
    
    // construtor que recebe a tela editar e a editora.
    public BuscarEditora(LivroEditar telaEditar, EditoraService editoraService) {
        this.telaEditar = telaEditar;
    this.editoraService = editoraService;
    initComponents();
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    carregarEditoras();
    setLocationRelativeTo(null);
    }

    // carregar as editoras existentes.
    private void carregarEditoras() {
    DefaultTableModel model = (DefaultTableModel) tblEditoras.getModel();
    model.setRowCount(0);

    List<Editora> editoras = editoraService.findAll();

    for (Editora e : editoras) {
        model.addRow(new Object[]{
            e.getId(),
            e.getCnpj(),
            e.getNome(),
            e.getTelefone(),
            e.getEndereco()
        });
    }
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
        Long id = Long.valueOf(spnID.getValue().toString());

    DefaultTableModel model = (DefaultTableModel) tblEditoras.getModel();
    model.setRowCount(0);

    try {
        Editora e = editoraService.findById(id);
        model.addRow(new Object[]{
            e.getId(),
            e.getCnpj(),
            e.getNome(),
            e.getTelefone(),
            e.getEndereco()
        });
    } catch (RuntimeException ex) {
        javax.swing.JOptionPane.showMessageDialog(this, "Editora não encontrada.");
    }
    }//GEN-LAST:event_btnBuscarId1ActionPerformed

    private void btnLimparIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparIDActionPerformed
        spnID.setValue(0);
        carregarEditoras();
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

    String nomeEditora = tblEditoras.getValueAt(linha, 2).toString();

    if (telaLivro != null) {
        telaLivro.setEditoraSelecionada(idEditora, nomeEditora);
    } else if (telaEditar != null) {
        telaEditar.setEditoraSelecionada(idEditora, nomeEditora);
    }

    dispose();
    }//GEN-LAST:event_btnSelecionarActionPerformed

    // fecha a tela BuscarEditora.
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    // atualiza a tabela das editoras existentes.
    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        carregarEditoras();
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
