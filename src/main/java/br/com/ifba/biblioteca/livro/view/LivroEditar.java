package br.com.ifba.biblioteca.livro.view;

import br.com.ifba.biblioteca.autor.service.AutorService;
import br.com.ifba.biblioteca.categoria.entity.Categoria;
import br.com.ifba.biblioteca.categoria.service.CategoriaService;
import br.com.ifba.biblioteca.editora.entity.Editora;
import br.com.ifba.biblioteca.editora.service.EditoraService;
import br.com.ifba.biblioteca.livro.entity.Livro;
import br.com.ifba.biblioteca.livro.service.LivroService;
import javax.swing.JOptionPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class LivroEditar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LivroEditar.class.getName());
    
    private String autorSelecionado;
    private Long idEditoraSelecionada;
    private Long idCategoriaSelecionada;
    private LivroService service;
    private Long idLivro;
    private AutorService autorService;
    private EditoraService editoraService;
    private CategoriaService categoriaService;


    
     // construtor recebe id do livro e service
    public LivroEditar(Long idLivro, LivroService service, AutorService autorService,  EditoraService editoraService, CategoriaService categoriaService) {
    this.service = service;
    this.idLivro = idLivro;
    this.autorService = autorService;
    this.editoraService = editoraService;
    this.categoriaService = categoriaService;

    initComponents();

    txtIsbn.setEditable(false); // ISBN não editável
    spnAno.setEnabled(false); // Ano de publicação não editável
    txtAutor.setEditable(false); 
    txtEditora.setEditable(false);
    txtCategoria.setEditable(false); 

    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    carregarLivro();
}

    
    public void setEditoraSelecionada(Long idEditora, String nomeEditora) {
        txtEditora.setText(nomeEditora);
        this.idEditoraSelecionada = idEditora;
    }
    
   public void setAutor(String nomeAutor){
    this.autorSelecionado = nomeAutor;
    txtAutor.setText(nomeAutor);
}



   public void setCategoriaSelecionada(Long idCategoria, String nomeCategoria) {
    this.idCategoriaSelecionada = idCategoria;
    txtCategoria.setText(nomeCategoria);
}

    
    // carrega dados do livro na tela.
    private void carregarLivro() {
    try {
        Livro livro = service.findById(idLivro);

        // dados básicos
        txtTitulo.setText(livro.getTitulo());
        txtAutor.setText(livro.getAutorNome());

        // categoria
        if (livro.getCategoria() != null) {
            idCategoriaSelecionada = livro.getCategoria().getId();
            txtCategoria.setText(livro.getCategoriaNome());
        } else {
            idCategoriaSelecionada = null;
            txtCategoria.setText("");
        }

        // editora
        if (livro.getEditora() != null) {
            idEditoraSelecionada = livro.getEditora().getId();
            txtEditora.setText(livro.getEditoraNome());
        } else {
            idEditoraSelecionada = null;
            txtEditora.setText("");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            this,
            "Erro ao carregar livro: " + e.getMessage(),
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
        dispose();
    }
}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        btnCadastrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtIsbn = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtAutor = new javax.swing.JTextField();
        btnAutor = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtEditora = new javax.swing.JTextField();
        btnEditora = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtCategoria = new javax.swing.JTextField();
        btnCategoria = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        spnAno = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel2.setText("Título:");

        btnCadastrar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCadastrar.setText("Cadastrar");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("EDITANDO LIVRO");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel3.setText("Novo Título:");

        txtTitulo.setBackground(new java.awt.Color(255, 255, 255));
        txtTitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTituloActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel4.setText("ISBN:");

        txtIsbn.setBackground(new java.awt.Color(255, 255, 255));
        txtIsbn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIsbnActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel5.setText("Novo Autor:");

        txtAutor.setBackground(new java.awt.Color(255, 255, 255));
        txtAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAutorActionPerformed(evt);
            }
        });

        btnAutor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAutor.setText("Buscar Autor");
        btnAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutorActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel6.setText("Nova Editora:");

        txtEditora.setBackground(new java.awt.Color(255, 255, 255));
        txtEditora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEditoraActionPerformed(evt);
            }
        });

        btnEditora.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditora.setText("Buscar Editora");
        btnEditora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditoraActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel7.setText("Nova Categoria:");

        txtCategoria.setBackground(new java.awt.Color(255, 255, 255));
        txtCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCategoriaActionPerformed(evt);
            }
        });

        btnCategoria.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCategoria.setText("Buscar Categoria");
        btnCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoriaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel8.setText("Ano publicação:");

        spnAno.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setText("Não editável após o cadastro.");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setText("Não editável após o cadastro.");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnAno, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCategoria)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnCategoria))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtEditora)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnEditora))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtAutor)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnAutor))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnSalvar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnCancelar)))
                                .addGap(12, 12, 12)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAutor))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEditora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditora))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCategoria))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(spnAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnCancelar))
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
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEditoraActionPerformed

    private void txtCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCategoriaActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose(); // fecha apenas essa janela.
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        try {
        Livro livro = service.findById(idLivro);

        // campos editáveis
        livro.setTitulo(txtTitulo.getText().trim());
        livro.setAutorNome(autorSelecionado);

        // editora
        Editora editora = new Editora();
        editora.setId(idEditoraSelecionada);
        livro.setEditora(editora);
        livro.setEditoraNome(txtEditora.getText());

        // categoria
        Categoria categoria = new Categoria();
        categoria.setId(idCategoriaSelecionada);
        livro.setCategoria(categoria);
        livro.setCategoriaNome(txtCategoria.getText());

        // valida título duplicado
        Livro livroAtual = service.findById(idLivro);

        if (!livroAtual.getTitulo().equalsIgnoreCase(livro.getTitulo())
                && service.existsByTitulo(livro.getTitulo())) {

            int opcao = JOptionPane.showConfirmDialog(
                this,
                "Já existe um livro com esse título.\nDeseja continuar mesmo assim?",
                "Título duplicado",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (opcao != JOptionPane.YES_OPTION) {
                return;
            }
        }

        service.update(livro);

        JOptionPane.showMessageDialog(this,
            "Livro atualizado com sucesso!");

        dispose();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Erro ao atualizar livro: " + e.getMessage(),
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
    }

    }//GEN-LAST:event_btnSalvarActionPerformed
    
    // botão buscar categoria
    private void btnCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoriaActionPerformed
        BuscarCategoria tela = new BuscarCategoria(this, categoriaService); // abre buscar categoria
        tela.setLocationRelativeTo(this);
        tela.setVisible(true);
    }//GEN-LAST:event_btnCategoriaActionPerformed

    // botão buscar editora
    private void btnEditoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditoraActionPerformed
         BuscarEditora tela = new BuscarEditora(this, editoraService);
    tela.setLocationRelativeTo(this);
    tela.setVisible(true);
    }//GEN-LAST:event_btnEditoraActionPerformed

    // botão buscar autor
    private void btnAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutorActionPerformed
        BuscarAutor tela = new BuscarAutor(this, autorService);
        tela.setLocationRelativeTo(this);
        tela.setVisible(true);
    }//GEN-LAST:event_btnAutorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAutor;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCategoria;
    private javax.swing.JButton btnEditora;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSpinner spnAno;
    private javax.swing.JTextField txtAutor;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtEditora;
    private javax.swing.JTextField txtIsbn;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}

