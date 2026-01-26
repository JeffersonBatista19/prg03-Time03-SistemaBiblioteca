/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.ifba.biblioteca.usuario.view;

import br.com.ifba.biblioteca.usuario.controller.UsuarioIController;
import br.com.ifba.biblioteca.usuario.entity.Usuario;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author jeffe
 */

@Component
public class UsuarioListar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UsuarioListar.class.getName());
    
    private TableRowSorter<DefaultTableModel> sorter;

    @Autowired
    private UsuarioIController usuarioController;

    @Autowired
    private ApplicationContext context;

    /**
     * Creates new form UsuarioListar
     */
    public UsuarioListar() {
        initComponents();
        configurarTabela();
        carregarDados();
    }
    
    private void configurarTabela() {
        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Login", "Perfil", "Nível", "Status"};

        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblUsuarios.setModel(modelo);

        sorter = new TableRowSorter<>(modelo);
        tblUsuarios.setRowSorter(sorter);
    }
    
    public void carregarDados() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblUsuarios.getModel();
            modelo.setRowCount(0);

            List<Usuario> lista = usuarioController.findAll();

            for (Usuario u : lista) {
                modelo.addRow(new Object[]{
                    u.getId(),
                    u.getNomeCompleto(),
                    u.getCpf(),
                    u.getTelefone(),
                    u.getLogin(),
                    u.getTipoPerfil(),
                    u.getNivelAcesso(),
                    u.getStatusPessoa()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuários: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
     private void aplicarFiltro() {
        String texto = txtBuscar.getText().trim();

        if (texto.isEmpty()) {
            sorter.setRowFilter(null);
            return;
        }

        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        btnAdicionar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Buscar: ");

        txtBuscar.addActionListener(this::txtBuscarActionPerformed);
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "CPF", "Telefone", "Login", "Perfil", "Status"
            }
        ));
        jScrollPane1.setViewportView(tblUsuarios);

        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(this::btnAdicionarActionPerformed);

        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnAtualizar.setText("Atualizar");
        btnAtualizar.addActionListener(this::btnAtualizarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(187, 187, 187))
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(btnAdicionar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEditar)
                .addGap(138, 138, 138)
                .addComponent(btnAtualizar)
                .addGap(102, 102, 102))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionar)
                    .addComponent(btnEditar)
                    .addComponent(btnAtualizar))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        aplicarFiltro();
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        carregarDados();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        try {
            UsuarioAdicionar tela = context.getBean(UsuarioAdicionar.class);
            tela.setUsuarioListar(this);
            tela.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            tela.setLocationRelativeTo(null);
            tela.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir tela: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
       int linhaSelecionada = tblUsuarios.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para editar!");
            return;
        }

        int linhaModelo = tblUsuarios.convertRowIndexToModel(linhaSelecionada);

        Long id = (Long) tblUsuarios.getModel().getValueAt(linhaModelo, 0);
        String nome = (String) tblUsuarios.getModel().getValueAt(linhaModelo, 1);
        String cpf = (String) tblUsuarios.getModel().getValueAt(linhaModelo, 2);
        String telefone = (String) tblUsuarios.getModel().getValueAt(linhaModelo, 3);
        String login = (String) tblUsuarios.getModel().getValueAt(linhaModelo, 4);
        String perfil = String.valueOf(tblUsuarios.getModel().getValueAt(linhaModelo, 5));
        String nivel = String.valueOf(tblUsuarios.getModel().getValueAt(linhaModelo, 6));
        String status = String.valueOf(tblUsuarios.getModel().getValueAt(linhaModelo, 7));

        try {
            UsuarioEditar tela = context.getBean(UsuarioEditar.class, nome, cpf, telefone, login, perfil, nivel, status, id, this);
            tela.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            tela.setLocationRelativeTo(null);
            tela.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir edição: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
