package br.com.ifba.biblioteca.emprestimo.view;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.emprestimo.controller.EmprestimoIController;
import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import br.com.ifba.biblioteca.cliente.entity.Cliente;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class EmprestimoAdicionar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EmprestimoAdicionar.class.getName());

    @Autowired
    private EmprestimoIController emprestimoController;

    private EmprestimoListar emprestimoListar; // Referência para atualizar a tabela

    // Método para receber a tela de listagem e poder atualizá-la depois
    public void setEmprestimoListar(EmprestimoListar emprestimoListar) {
        this.emprestimoListar = emprestimoListar;
    }
    
    /**
     * Creates new form EmprestimoAdicionar
     */
    
    public EmprestimoAdicionar() {
        initComponents();
        setTitle("Novo Empréstimo");
        setLocationRelativeTo(null);
    }
    
    @PostConstruct
    public void init() {
        configurarComboBoxes();
        preencherDados();
    }
    
    private void configurarComboBoxes() {
        // Renderer para Cliente mostrar o Nome
        cbCliente.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cliente) {
                    setText(((Cliente) value).getNomeCompleto());
                }
                return this;
            }
        });

        // Renderer para Exemplar mostrar o ID (já que Exemplar não tem Nome direto fácil aqui)
        cbExemplar.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Exemplar) {
                    setText("ID: " + ((Exemplar) value).getId() + " - Tombamento: " + ((Exemplar) value).getNumeroTombamento());
                }
                return this;
            }
        });

        // Sincronização: ComboBox -> Texto
        cbCliente.addActionListener(e -> {
            Cliente sel = (Cliente) cbCliente.getSelectedItem();
            if (sel != null) {
                txtIdCliente.setText(sel.getId().toString());
            }
        });

        cbExemplar.addActionListener(e -> {
            Exemplar sel = (Exemplar) cbExemplar.getSelectedItem();
            if (sel != null) {
                txtIdExemplar.setText(sel.getId().toString());
            }
        });
        
        // Sincronização: Texto -> ComboBox 
        txtIdCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                sincronizarTextoParaComboCliente();
            }
        });

        txtIdExemplar.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                sincronizarTextoParaComboExemplar();
            }
        });
    }

    private void preencherDados() {
        try {
            List<Cliente> clientes = emprestimoController.findAllClientes();
            for (Cliente c : clientes) cbCliente.addItem(c);

            List<Exemplar> exemplares = emprestimoController.findAllExemplares();
            for (Exemplar ex : exemplares) cbExemplar.addItem(ex);
            
            cbCliente.setSelectedItem(null);
            cbExemplar.setSelectedItem(null);
            txtIdCliente.setText("");
            txtIdExemplar.setText("");

        } catch (Exception e) {
            logger.severe("Erro ao preencher combos: " + e.getMessage());
        }
    }

    private void sincronizarTextoParaComboCliente() {
        try {
            Long id = Long.parseLong(txtIdCliente.getText());
            for (int i = 0; i < cbCliente.getItemCount(); i++) {
                Cliente c = cbCliente.getItemAt(i);
                if (c.getId().equals(id)) {
                    cbCliente.setSelectedItem(c);
                    return;
                }
            }
        } catch (Exception e) {}
    }

    private void sincronizarTextoParaComboExemplar() {
        try {
            Long id = Long.parseLong(txtIdExemplar.getText());
            for (int i = 0; i < cbExemplar.getItemCount(); i++) {
                Exemplar ex = cbExemplar.getItemAt(i);
                if (ex.getId().equals(id)) {
                    cbExemplar.setSelectedItem(ex);
                    return;
                }
            }
        } catch (Exception e) {}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnSalvar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        txtIdCliente = new javax.swing.JTextField();
        txtIdExemplar = new javax.swing.JTextField();
        cbCliente = new javax.swing.JComboBox<>();
        cbExemplar = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnSalvar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSalvar.setText("Confirmar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel1.setText("ADICIONANDO EMPRÉSTIMOS");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("ID do Cliente:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("ID do Exemplar:");

        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txtIdCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdClienteActionPerformed(evt);
            }
        });

        txtIdExemplar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdExemplarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdExemplar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbExemplar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(btnSalvar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtIdExemplar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbExemplar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnCancelar))
                .addContainerGap(120, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(140, 140, 140))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdExemplarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdExemplarActionPerformed
        
    }//GEN-LAST:event_txtIdExemplarActionPerformed

    private void txtIdClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdClienteActionPerformed
        
    }//GEN-LAST:event_txtIdClienteActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // Fecha a janela atual e volta para a anterior
        SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        try {
            // Pega os textos dos campos
            String idClienteStr = txtIdCliente.getText();
            String idExemplarStr = txtIdExemplar.getText();

            // Verifica se está vazio
            if (idClienteStr.trim().isEmpty() || idExemplarStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha os IDs do Cliente e do Exemplar.");
                return;
            }

            // Converte para números (Long)
            Long idCliente = Long.parseLong(txtIdCliente.getText()); 
            Long idExemplar = Long.parseLong(txtIdExemplar.getText());

            // CRIAÇÃO DO OBJETO 
            Cliente cliente = new Cliente(); 
            cliente.setId(idCliente);

            Exemplar exemplar = new Exemplar();
            exemplar.setId(idExemplar);

            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setCliente(cliente); 
            emprestimo.setExemplar(exemplar);

            Emprestimo salvo = emprestimoController.save(emprestimo);

            // Mensagem de Sucesso
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            JOptionPane.showMessageDialog(this, "Empréstimo realizado!\nDevolução: " + (salvo.getDataPrevistaDevolucao() != null ? salvo.getDataPrevistaDevolucao().format(formatter) : "N/A"));

            // Atualiza a tabela da tela de trás 
            if (emprestimoListar != null) {
                emprestimoListar.carregarDados(); 
            }

            // Fecha a janela atual
            this.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Digite apenas números nos IDs.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSalvarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<Cliente> cbCliente;
    private javax.swing.JComboBox<Exemplar> cbExemplar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdExemplar;
    // End of variables declaration//GEN-END:variables
}
