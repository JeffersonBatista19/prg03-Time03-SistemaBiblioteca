package br.com.ifba.biblioteca.reserva.view;

import br.com.ifba.biblioteca.cliente.entity.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import br.com.ifba.biblioteca.cliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *
 * @author misae
 */

// tela responsável por buscar e selecionar um Cliente existente
// Integra-se com o módulo Cliente
@Component
public class BuscarCliente extends javax.swing.JFrame { 
                                                         
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(BuscarCliente.class.getName());

    // Tela que chamou esta (ReservaAdicionar)
    private  ReservaAdicionar telaPai;
    
    private  ClienteService clienteService;


    
    
    @Autowired
    public BuscarCliente(ClienteService clienteService) {
        initComponents();
        this.clienteService = clienteService;

        logger.info("Tela BuscarCliente aberta");
        atualizarTabela(clienteService.findAll()); //atualiza a tabela com todos os clientes
        
        // Esconde a coluna ID
        tblClientes1.getColumnModel().getColumn(0).setMinWidth(0);
        tblClientes1.getColumnModel().getColumn(0).setMaxWidth(0);
        tblClientes1.getColumnModel().getColumn(0).setWidth(0);
        tblClientes1.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    
    public void setTelaPai(ReservaAdicionar telaPai) {
         this.telaPai = telaPai;
        setLocationRelativeTo(telaPai);
    }

    
    //Atualiza a tabela com a lista informada
    private void atualizarTabela(List<Cliente> clientes) {
        DefaultTableModel model = (DefaultTableModel) tblClientes1.getModel();
        model.setRowCount(0);

        for (Cliente c : clientes) {
            model.addRow(new Object[]{
                c.getId(),
                c.getNomeCompleto(),
                c.getCpf(),
                c.getTelefone(),
                c.getTipoCliente(),
                c.getLimiteEmprestimo(),     // limite de empréstimos
                c.getStatusPessoa()      // ativo, inativo,
            });
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        btnBuscar1 = new javax.swing.JButton();
        txtCliente = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnSelecionar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblClientes1 = new javax.swing.JTable();
        btnAtualizar = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "CPF", "Telefone", "TipoCliente", "Limite", "Status"
            }
        ));
        jScrollPane1.setViewportView(tblClientes);

        btnBuscar1.setText("Buscar");
        btnBuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscar1ActionPerformed(evt);
            }
        });

        txtCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnSelecionar.setText("Selecionar");
        btnSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("LISTAGEM DE CLIENTES");

        tblClientes1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblClientes1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "CPF", "Telefone", "TipoCliente", "Limite", "Status"
            }
        ));
        jScrollPane3.setViewportView(tblClientes1);

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
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSelecionar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtCliente))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAtualizar, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(404, 404, 404)
                .addComponent(jLabel1)
                .addContainerGap(525, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addGap(6, 6, 6)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelecionar)
                    .addComponent(btnCancelar)
                    .addComponent(btnAtualizar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    //Botão Buscar – filtra clientes pelo nome
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String texto = txtCliente.getText().trim().toLowerCase();
        
        // Se não digitou nada, mostra todos
         if (texto.isEmpty()) {
            atualizarTabela(clienteService.findAll());
            return;
        }

        List<Cliente> clientes = clienteService.findAll();
        List<Cliente> filtrados = new ArrayList<>();

        for (Cliente c : clientes) {
            if (c.getNomeCompleto().toLowerCase().contains(texto)) {
            filtrados.add(c);
            }
        }


        atualizarTabela(filtrados);

        if (filtrados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente encontrado.");
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    //Botão Selecionar – envia o ID do cliente para ReservaAdicionar
    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarActionPerformed
        int linha = tblClientes1.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente.");
            return;
        }

        Long clienteId = (Long) tblClientes1.getValueAt(linha, 0);
        Cliente cliente = clienteService.findById(clienteId);

        // Envia o cliente selecionado para a tela de reserva
        telaPai.setClienteSelecionado(cliente);

        this.dispose(); // Fecha a tela após selecionar
    }//GEN-LAST:event_btnSelecionarActionPerformed

    private void txtClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClienteActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
         this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscar1ActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        // atualiza a tabela com todos os clientes cadastrados
        atualizarTabela(clienteService.findAll());
        txtCliente.setText(""); // limpa o campo de busca
    }//GEN-LAST:event_btnAtualizarActionPerformed

    /**
     * @param args the command line arguments
     */
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscar1;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSelecionar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblClientes1;
    private javax.swing.JTextField txtCliente;
    // End of variables declaration//GEN-END:variables
}
