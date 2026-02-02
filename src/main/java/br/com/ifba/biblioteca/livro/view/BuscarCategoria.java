package br.com.ifba.biblioteca.livro.view;

import br.com.ifba.biblioteca.categoria.entity.Categoria;
import br.com.ifba.biblioteca.categoria.service.CategoriaService;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class BuscarCategoria extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BuscarCategoria.class.getName());
    private LivroAdicionar telaLivro;
    private LivroEditar telaEditar;
    private CategoriaService categoriaService;

    
    public BuscarCategoria(LivroAdicionar telaLivro, CategoriaService categoriaService) {
        this.telaLivro = telaLivro;
        this.categoriaService = categoriaService;
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        carregarCategorias();
        setLocationRelativeTo(null);
    }
    
    public BuscarCategoria(LivroEditar telaEditar, CategoriaService categoriaService) {
        this.telaEditar = telaEditar;
        this.categoriaService = categoriaService;
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        carregarCategorias();
        setLocationRelativeTo(null);
    }
    
    private void carregarCategorias() {
    try {
        DefaultTableModel model =
            (DefaultTableModel) tblCategoria.getModel();
        model.setRowCount(0);

        List<Categoria> categorias = categoriaService.findAllAtivas();

        for (Categoria c : categorias) {
            model.addRow(new Object[]{
                c.getId(),
                c.getNome(),
                c.getDescricao()
            });
        }

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Erro ao carregar categorias: " + e.getMessage(),
            "Erro",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
}




    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spnID = new javax.swing.JSpinner();
        btnBuscarId = new javax.swing.JButton();
        btnLimparID = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCategoria = new javax.swing.JTable();
        btnSelecionar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("BUSCANDO CATEGORIA");

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

        tblCategoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Descrição"
            }
        ));
        jScrollPane1.setViewportView(tblCategoria);

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnID, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscarId)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLimparID)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSelecionar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAtualizar)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(139, 139, 139))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelecionar)
                    .addComponent(btnCancelar)
                    .addComponent(btnAtualizar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarIdActionPerformed
        int id = (int) spnID.getValue();

    DefaultTableModel model =
        (DefaultTableModel) tblCategoria.getModel();
    model.setRowCount(0);

    try {
        Categoria c = categoriaService.findByIdAtiva((long) id);
        model.addRow(new Object[]{
            c.getId(),
            c.getNome(),
            c.getDescricao()
        });
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Categoria não encontrada.",
            "Aviso",
            javax.swing.JOptionPane.WARNING_MESSAGE
        );
    }

    }//GEN-LAST:event_btnBuscarIdActionPerformed

    private void btnLimparIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparIDActionPerformed
        spnID.setValue(0);
        carregarCategorias();
    }//GEN-LAST:event_btnLimparIDActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        carregarCategorias();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarActionPerformed
         int linhaSelecionada = tblCategoria.getSelectedRow();

    if (linhaSelecionada == -1) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Selecione uma categoria na tabela.",
            "Aviso",
            javax.swing.JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    Long idCategoria = Long.valueOf(
        tblCategoria.getValueAt(linhaSelecionada, 0).toString()
    );

    String nomeCategoria =
        tblCategoria.getValueAt(linhaSelecionada, 1).toString();

    if (telaLivro != null) {
        telaLivro.setCategoriaSelecionada(idCategoria, nomeCategoria);
    } else if (telaEditar != null) {
        telaEditar.setCategoriaSelecionada(idCategoria, nomeCategoria);
    }

    dispose();
    }//GEN-LAST:event_btnSelecionarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnBuscarId;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnLimparID;
    private javax.swing.JButton btnSelecionar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spnID;
    private javax.swing.JTable tblCategoria;
    // End of variables declaration//GEN-END:variables
}
