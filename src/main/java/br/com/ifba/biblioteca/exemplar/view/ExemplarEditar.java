package br.com.ifba.biblioteca.exemplar.view;
import br.com.ifba.biblioteca.exemplar.controller.ExemplarController;
import br.com.ifba.biblioteca.exemplar.entity.EstadoConservacao;
import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import br.com.ifba.biblioteca.exemplar.entity.StatusExemplar;
import javax.swing.SwingUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ExemplarEditar extends javax.swing.JFrame {
    
    @Autowired
    private ExemplarController exemplarController;

    private ExemplarListar exemplarListar; // instância visível da lista
    private int linhaSelecionada; // linha selecionada na tabela
    private Long idExemplar; // id do exemplar que está sendo editado
    private String isbn; // ISBN do livro vinculado ao exemplar
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ExemplarEditar.class.getName());

   
    public ExemplarEditar(int tombamento, String conservacao, String local, String status,String isbn,
                      Long idExemplar, ExemplarListar telaListar) {
    this.exemplarListar = telaListar;
    this.idExemplar = idExemplar;
    this.isbn = isbn;

    initComponents(); // inicializa os componentes da GUI
    txtIsbn.setText(isbn);  
    txtIsbn.setEditable(false); 
    txtIsbn.setBackground(new java.awt.Color(230, 230, 230));
    btnLivro.setEnabled(false);   // desativa botão
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    // preenche os campos com os valores atuais
    spnTombamento.setValue(tombamento);
    cmbConservacao.setSelectedItem(conservacao);
    txtLocal1.setText(local);
    cmbStatus.setSelectedItem(status);
    
    pack(); // garante que o tamanho da janela está definido
    setLocationRelativeTo(null); // centraliza corretamente
}

    // setter para passar instância da tela de listar + linha selecionada + id
    public void setExemplarListar(ExemplarListar exemplarListar, int linhaSelecionada, Long idExemplar) {
    this.exemplarListar = exemplarListar;
    this.linhaSelecionada = linhaSelecionada;
    this.idExemplar = idExemplar;
}

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtConserva = new javax.swing.JTextField();
        txtLocal = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnSalvar1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        spnTombamento = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtLocal1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        cmbConservacao = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtIsbn = new javax.swing.JTextField();
        btnLivro = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

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
        jScrollPane1.setViewportView(jTable1);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Número Tombamento:");

        txtConserva.setBackground(new java.awt.Color(255, 255, 255));

        txtLocal.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Criar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnSalvar1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSalvar1.setText("Salvar");
        btnSalvar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvar1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel1.setText("EDITANDO EXEMPLAR");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Novo Número Tombamento:");

        spnTombamento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        spnTombamento.setModel(new javax.swing.SpinnerNumberModel());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Novo Estado Conservação:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Nova Localização:");

        txtLocal1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Status:");

        cmbStatus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DISPONIVEL", "EMPRESTADO", "RESERVADO", "INATIVO" }));
        cmbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStatusActionPerformed(evt);
            }
        });

        btnSalvar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        cmbConservacao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbConservacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NOVO", "BOM", "REGULAR", "RUIM" }));
        cmbConservacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbConservacaoActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Livro ISBN:");

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

        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Não editável após o cadastro.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(btnSalvar)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLocal1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbConservacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnTombamento, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLivro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLivro)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(spnTombamento, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbConservacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtLocal1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbStatusActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        try {
        int novoTombamento = (Integer) spnTombamento.getValue();
        
        String conservacaoStr = (String) cmbConservacao.getSelectedItem();
        EstadoConservacao novaConservacao = EstadoConservacao.valueOf(conservacaoStr);

        String novaLocalizacao = txtLocal1.getText();
        StatusExemplar novoStatus = StatusExemplar.valueOf((String) cmbStatus.getSelectedItem());

        if (novaLocalizacao.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Preencha todos os campos!",
                "Erro",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 🔎 busca o exemplar atual no banco
        Exemplar ex = exemplarController.findById(idExemplar);

        // 🔐 valida tombamento único
        if (novoTombamento != ex.getNumeroTombamento() &&
            exemplarController.existsByNumeroTombamento(novoTombamento)) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Já existe um exemplar com este número de tombamento!",
                "Erro",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ✅ atualiza dados
        ex.setNumeroTombamento(novoTombamento);
        ex.setConservacao(novaConservacao);
        ex.setLocalizacaoFisica(novaLocalizacao);
        ex.setStatus(novoStatus);

        // ⭐ CORREÇÃO DO ISBN (OBRIGATÓRIA)
        ex.setIsbnLivro(this.isbn); 
        // se for relação:
        // ex.setLivro(livroController.findByIsbn(this.isbn));

        // 💾 salva no banco
        exemplarController.update(ex);

        // 🔄 atualiza a tabela
        exemplarListar.carregarExemplares();

        this.dispose();

        javax.swing.JOptionPane.showMessageDialog(null,
            "Exemplar atualizado com sucesso!",
            "Sucesso",
            javax.swing.JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null,
            e.getMessage(),
            "Erro",
            javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSalvar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalvar1ActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cmbConservacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbConservacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbConservacaoActionPerformed

    private void txtIsbnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIsbnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIsbnActionPerformed

    private void btnLivroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLivroActionPerformed
        
    }//GEN-LAST:event_btnLivroActionPerformed

    

    public void preencherCampos(int tombamento, String conservacao, String local, String status,
                            Long idExemplar, ExemplarListar listar, int linhaSelecionada) {
    spnTombamento.setValue(tombamento);
    cmbConservacao.setSelectedItem(conservacao);
    txtLocal1.setText(local);
    cmbStatus.setSelectedItem(status);

    this.exemplarListar = listar;
    this.idExemplar = idExemplar;
    this.linhaSelecionada = linhaSelecionada;
}

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnLivro;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSalvar1;
    private javax.swing.JComboBox<String> cmbConservacao;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JSpinner spnTombamento;
    private javax.swing.JTextField txtConserva;
    private javax.swing.JTextField txtIsbn;
    private javax.swing.JTextField txtLocal;
    private javax.swing.JTextField txtLocal1;
    // End of variables declaration//GEN-END:variables
}
