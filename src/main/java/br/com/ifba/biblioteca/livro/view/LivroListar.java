package br.com.ifba.biblioteca.livro.view;


import br.com.ifba.biblioteca.autor.service.AutorService;
import br.com.ifba.biblioteca.categoria.service.CategoriaService;
import br.com.ifba.biblioteca.editora.service.EditoraService;
import br.com.ifba.biblioteca.livro.entity.Livro;
import br.com.ifba.biblioteca.livro.service.LivroService;
import jakarta.annotation.PostConstruct;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



@Component
@Scope("prototype")
public class LivroListar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LivroListar.class.getName());
    
    @Autowired // injeta o service de livros.
    private LivroService service;
    
    @Autowired // injeta o service de autor.
    private AutorService autorService;
    
    @Autowired // injeta o service de editora.
    private EditoraService editoraService;
    
    @Autowired // injeta o service de categoria.
    private CategoriaService categoriaService;



    public LivroListar() {
        initComponents();
        setLocationRelativeTo(null); // centraliza
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
    }
    
    // método chamado após a construção do bean.
    @PostConstruct
    public void init() {
    carregarLivros(); // carrega livros na tabela.
}

    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpinner1 = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtIsbn = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAutor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEditora = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCategoria = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        spnAno = new javax.swing.JSpinner();
        btnBuscarLivro = new javax.swing.JButton();
        btnLimparFiltro = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLivros = new javax.swing.JTable();
        btnAdicionar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("LIVROS CADASTRADOS");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel2.setText("Título:");

        txtTitulo.setBackground(new java.awt.Color(255, 255, 255));
        txtTitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTituloActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel3.setText("ISBN:");

        txtIsbn.setBackground(new java.awt.Color(255, 255, 255));
        txtIsbn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIsbnActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel4.setText("Autor:");

        txtAutor.setBackground(new java.awt.Color(255, 255, 255));
        txtAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAutorActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel5.setText("Editora:");

        txtEditora.setBackground(new java.awt.Color(255, 255, 255));
        txtEditora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEditoraActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel6.setText("Categoria:");

        txtCategoria.setBackground(new java.awt.Color(255, 255, 255));
        txtCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCategoriaActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel7.setText("Ano publicação:");

        spnAno.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        btnBuscarLivro.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscarLivro.setText("Buscar");
        btnBuscarLivro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarLivroActionPerformed(evt);
            }
        });

        btnLimparFiltro.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLimparFiltro.setText("Limpar");
        btnLimparFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparFiltroActionPerformed(evt);
            }
        });

        tblLivros.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblLivros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Título", "ISBN", "Autor", "Editora", "Categoria", "Ano Publicação"
            }
        ));
        jScrollPane1.setViewportView(tblLivros);

        btnAdicionar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(276, 276, 276))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(spnAno, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnBuscarLivro)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnLimparFiltro))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtAutor))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtEditora))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtCategoria))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdicionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAtualizar)
                        .addGap(21, 21, 21))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtEditora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(spnAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarLivro)
                    .addComponent(btnLimparFiltro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionar)
                    .addComponent(btnEditar)
                    .addComponent(btnExcluir)
                    .addComponent(btnAtualizar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTituloActionPerformed

    private void txtIsbnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIsbnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIsbnActionPerformed

    private void txtAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAutorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAutorActionPerformed

    private void txtEditoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEditoraActionPerformed
       
    }//GEN-LAST:event_txtEditoraActionPerformed

    private void txtCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCategoriaActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // abre tela para adicionar livro.
        LivroAdicionar tela =
        new LivroAdicionar(this, service, autorService, editoraService, categoriaService);

        tela.setVisible(true);
 
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // edita livro selecionado.
        int linha = tblLivros.getSelectedRow();

    if (linha == -1) {
        JOptionPane.showMessageDialog(this,
            "Selecione um livro para editar.",
            "Aviso",
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    Long idLivro = Long.valueOf(
        tblLivros.getValueAt(linha, 0).toString()
    );

    LivroEditar tela = new LivroEditar(idLivro, service, autorService, editoraService, categoriaService);
    tela.setVisible(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // exclui livro selecionado após confirmação.
        int linhaSelecionada = tblLivros.getSelectedRow();

    if (linhaSelecionada == -1) {
        JOptionPane.showMessageDialog(this,
            "Selecione um livro para excluir!",
            "Aviso",
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    Long idLivro = Long.valueOf(
        tblLivros.getValueAt(linhaSelecionada, 0).toString()
    );

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Tem certeza que deseja excluir este livro?",
        "Confirmar exclusão",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        try {
            Livro livro = service.findById(idLivro); // busca o objeto.
            service.delete(livro);

            carregarLivros(); // atualiza tabela.

            JOptionPane.showMessageDialog(this,
                "Livro excluído com sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao excluir livro: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
         carregarLivros(); // recarrega tabela de livros.
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnBuscarLivroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarLivroActionPerformed
       List<Livro> filtrados = service.findAll(); // filtra livros pelos campos preenchidos.

    // Filtro por Título
    if (!txtTitulo.getText().isBlank()) {
        String titulo = txtTitulo.getText().toLowerCase();
        filtrados = filtrados.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(titulo))
                .toList();
    }

    // Filtro por ISBN
    if (!txtIsbn.getText().isBlank()) {
        String isbn = txtIsbn.getText();
        filtrados = filtrados.stream()
                .filter(l -> l.getIsbn().equals(isbn))
                .toList();
    }

    // Filtro por nome do Autor.
    if (!txtAutor.getText().isBlank()) {
    String autor = txtAutor.getText().toLowerCase();
    filtrados = filtrados.stream()
            .filter(l -> l.getAutorNome().toLowerCase().contains(autor))
            .toList();
}


   // Filtro por nome da Editora.
if (!txtEditora.getText().isBlank()) {
    String nomeEditora = txtEditora.getText().toLowerCase();

    filtrados = filtrados.stream()
            .filter(l ->
                l.getEditora() != null &&
                l.getEditora().getNome() != null &&
                l.getEditora().getNome().toLowerCase().contains(nomeEditora)
            )
            .toList();
}


   // Filtro por nome da Categoria
if (!txtCategoria.getText().isBlank()) {
    String nomeCategoria = txtCategoria.getText().toLowerCase();

    filtrados = filtrados.stream()
            .filter(l ->
                l.getCategoriaNome() != null &&
                l.getCategoriaNome().toLowerCase().contains(nomeCategoria)
            )
            .toList();
}



    // Filtro por Ano de Publicação.
    int ano = (int) spnAno.getValue();
    if (ano > 0) {
        filtrados = filtrados.stream()
                .filter(l -> l.getAnoPublicacao() == ano)
                .toList();
    }

    // Atualiza a tabela com os filtrados.
    DefaultTableModel model = (DefaultTableModel) tblLivros.getModel();
    model.setRowCount(0);

    for (Livro l : filtrados) {
        
        String nomeAutor = l.getAutorNome();

        
        model.addRow(new Object[]{
            l.getId(),
            l.getTitulo(),
            l.getIsbn(),
            nomeAutor,
            l.getEditora() != null ? l.getEditora().getNome() : "",
           l.getCategoriaNome(),

            l.getAnoPublicacao()
        });
    }
    }//GEN-LAST:event_btnBuscarLivroActionPerformed

    // botão pra limpar os campos de busca.
    private void btnLimparFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparFiltroActionPerformed
        txtTitulo.setText("");
        txtIsbn.setText("");
        txtAutor.setText("");
        txtEditora.setText("");
        txtCategoria.setText("");
        spnAno.setValue(0);

        carregarLivros();
    }//GEN-LAST:event_btnLimparFiltroActionPerformed

    // carrega os livros da tabela.
    public void carregarLivros() {
   try {
        List<Object[]> dados = service.findAllComNomeAutor();

        DefaultTableModel model =
            (DefaultTableModel) tblLivros.getModel();

        model.setRowCount(0); // limpa tabela

        for (Object[] linha : dados) {
            model.addRow(linha);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            this,
            "Erro ao carregar livros: " + e.getMessage()
        );
    }
}



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnBuscarLivro;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimparFiltro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner spnAno;
    private javax.swing.JTable tblLivros;
    private javax.swing.JTextField txtAutor;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtEditora;
    private javax.swing.JTextField txtIsbn;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
