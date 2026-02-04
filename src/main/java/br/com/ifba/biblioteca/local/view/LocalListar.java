/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.ifba.biblioteca.local.view;

import br.com.ifba.biblioteca.local.controller.LocalIController;
import br.com.ifba.biblioteca.local.entity.Local;
import jakarta.annotation.PostConstruct;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author jeffe
 */
public class LocalListar extends javax.swing.JFrame {
    
    @Autowired
    private LocalIController localController;

    private DefaultTableModel modeloTabela;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LocalListar.class.getName());

    /**
     * Creates new form LocalListar
     */
    public LocalListar() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        modeloTabela = (DefaultTableModel) tblLocais.getModel();
        tblLocais.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
     @PostConstruct
    public void init() {
        carregarLocais();
    }

    private void carregarLocais() {
        try {
            List<Local> locais = localController.findAllAtivos();
            preencherTabela(locais);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherTabela(List<Local> locais) {
        modeloTabela.setRowCount(0);
        if (locais == null) return;

        for (Local l : locais) {
            modeloTabela.addRow(new Object[]{
                l.getId(),
                l.getCidade(),
                l.getRua(),
                l.getNumero(),
                l.getComplemento()
            });
        }
    }

    private Long getIdSelecionadoDaTabela() {
        int linha = tblLocais.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um local na tabela.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        Object valorId = tblLocais.getValueAt(linha, 0);
        if (valorId == null) {
            JOptionPane.showMessageDialog(this, "Não foi possível obter o ID do local.", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            return Long.valueOf(valorId.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido na tabela.", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void atualizarLista() {
        carregarLocais();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtBuscaCidade = new javax.swing.JTextField();
        txtBuscaRua = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLocais = new javax.swing.JTable();
        btnAdicionar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnInativar = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Cidade:");

        jLabel2.setText("Rua:");

        txtBuscaRua.addActionListener(this::txtBuscaRuaActionPerformed);

        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);

        jButton2.setText("LIMPAR");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        tblLocais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Cidade", "Rua", "Número", "Complemento"
            }
        ));
        jScrollPane1.setViewportView(tblLocais);

        btnAdicionar.setText("ADICIONAR");
        btnAdicionar.addActionListener(this::btnAdicionarActionPerformed);

        btnEditar.setText("EDITAR");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnInativar.setText("INATIVAR");
        btnInativar.addActionListener(this::btnInativarActionPerformed);

        btnAtualizar.setText("ATUALIZAR");
        btnAtualizar.addActionListener(this::btnAtualizarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtBuscaCidade, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addComponent(txtBuscaRua))
                .addGap(18, 18, 18)
                .addComponent(btnBuscar)
                .addGap(41, 41, 41)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnAdicionar)
                .addGap(73, 73, 73)
                .addComponent(btnEditar)
                .addGap(67, 67, 67)
                .addComponent(btnInativar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAtualizar)
                .addGap(78, 78, 78))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtBuscaCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtBuscaRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBuscar)
                            .addComponent(jButton2))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionar)
                    .addComponent(btnEditar)
                    .addComponent(btnInativar)
                    .addComponent(btnAtualizar))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscaRuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscaRuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscaRuaActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
       Long id = getIdSelecionadoDaTabela();
        if (id == null) return;

        try {
            Local local = localController.findByIdAtivo(id);

            LocalEditar tela = new LocalEditar(this, localController, local);
            tela.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao editar", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnInativarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInativarActionPerformed
        Long id = getIdSelecionadoDaTabela();
        if (id == null) return;

        int opcao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente INATIVAR este local?",
                "Confirmar inativação",
                JOptionPane.YES_NO_OPTION
        );

        if (opcao != JOptionPane.YES_OPTION) return;

        try {
            localController.inativar(id);
            JOptionPane.showMessageDialog(this, "Local inativado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarLocais();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao inativar", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnInativarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
         try {
            String cidade = txtBuscaCidade.getText();
            String rua = txtBuscaRua.getText();

            List<Local> resultado = localController.findByCidadeAndRua(cidade, rua);
            preencherTabela(resultado);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro na busca", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        txtBuscaCidade.setText("");
        txtBuscaRua.setText("");
        carregarLocais();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        carregarLocais();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        try {
            LocalAdicionar tela = new LocalAdicionar(this, localController);
            tela.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao abrir cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAdicionarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnInativar;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblLocais;
    private javax.swing.JTextField txtBuscaCidade;
    private javax.swing.JTextField txtBuscaRua;
    // End of variables declaration//GEN-END:variables
}
