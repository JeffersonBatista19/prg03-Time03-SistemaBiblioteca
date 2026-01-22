package br.com.ifba.biblioteca.exemplar.view;

import br.com.ifba.biblioteca.livro.entity.Livro;
import br.com.ifba.biblioteca.livro.service.LivroService;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class BuscarLivro extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BuscarLivro.class.getName());

    private ExemplarAdicionar telaExemplar;
    
    @Autowired
    private LivroService service;


    
    public BuscarLivro() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    public void setTelaExemplar(ExemplarAdicionar telaExemplar) {
    this.telaExemplar = telaExemplar;
}


    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblLivros = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLivros1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtIsbn = new javax.swing.JTextField();
        btnBuscarISBN = new javax.swing.JButton();
        btnSelecionar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();

        tblLivros.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblLivros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Título", "ISBN", "Autor", "Editora", "Categoria", "Ano Publicação"
            }
        ));
        jScrollPane1.setViewportView(tblLivros);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("CADASTRANDO LIVRO");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel3.setText("Título:");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblLivros1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblLivros1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Título", "ISBN", "Autor", "Editora", "Categoria", "Ano Publicação"
            }
        ));
        jScrollPane2.setViewportView(tblLivros1);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("LISTAGEM DE LIVROS");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel4.setText("ISBN:");

        txtIsbn.setBackground(new java.awt.Color(255, 255, 255));
        txtIsbn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIsbnActionPerformed(evt);
            }
        });

        btnBuscarISBN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscarISBN.setText("Buscar");
        btnBuscarISBN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarISBNActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(238, 238, 238)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBuscarISBN)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSelecionar)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAtualizar)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarISBN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelecionar)
                    .addComponent(btnCancelar)
                    .addComponent(btnAtualizar))
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIsbnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIsbnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIsbnActionPerformed

    private void btnBuscarISBNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarISBNActionPerformed
        try {
        String isbn = txtIsbn.getText();

        if (isbn == null || isbn.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um ISBN para buscar.");
            return;
        }

        Livro livro = service.findByIsbn(isbn.trim());

        DefaultTableModel model =
            (DefaultTableModel) tblLivros1.getModel();

        model.setRowCount(0); // limpa tabela

        if (livro != null) {
            model.addRow(new Object[]{
                livro.getId(),
                livro.getTitulo(),
                livro.getIsbn(),
                livro.getAutorNome(),
                livro.getEditoraNome(),
                livro.getCategoriaNome(),
                livro.getAnoPublicacao()
            });
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum livro encontrado com esse ISBN.");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            this,
            "Erro ao buscar livro: " + e.getMessage()
        );
    }
    }//GEN-LAST:event_btnBuscarISBNActionPerformed

    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarActionPerformed
        int row = tblLivros1.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Selecione um livro.");
        return;
    }

    String isbn = tblLivros1.getValueAt(row, 2).toString(); // coluna ISBN

    telaExemplar.setIsbnLivro(isbn); // método da tela ExemplarAdicionar
    dispose();
    }//GEN-LAST:event_btnSelecionarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose(); // fecha apenas essa janela.
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        carregarLivros();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    public void carregarLivros() {
    try {
        List<Livro> livros = service.findAll();

        DefaultTableModel model =
            (DefaultTableModel) tblLivros1.getModel();

        model.setRowCount(0); // limpa tabela

        for (Livro l : livros) {
            model.addRow(new Object[]{
                l.getId(),
                l.getTitulo(),
                l.getIsbn(),
                l.getAutorNome(),
                l.getEditoraNome(),
                l.getCategoriaNome(),
                l.getAnoPublicacao()
     });
        }

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Erro ao carregar livros: " + e.getMessage()
        );
    }
}
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnBuscarISBN;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSelecionar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblLivros;
    private javax.swing.JTable tblLivros1;
    private javax.swing.JTextField txtIsbn;
    // End of variables declaration//GEN-END:variables
}
