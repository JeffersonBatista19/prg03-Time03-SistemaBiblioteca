package br.com.ifba.biblioteca.livro.view;

import br.com.ifba.biblioteca.autor.service.AutorService;
import br.com.ifba.biblioteca.categoria.entity.Categoria;
import br.com.ifba.biblioteca.categoria.service.CategoriaService;
import br.com.ifba.biblioteca.editora.entity.Editora;
import br.com.ifba.biblioteca.editora.service.EditoraService;
import br.com.ifba.biblioteca.livro.entity.Livro;
import br.com.ifba.biblioteca.livro.service.LivroService;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class LivroAdicionar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LivroAdicionar.class.getName());

    private LivroService service;
    private LivroListar telaListar;
    private AutorService autorService;
    private EditoraService editoraService;
    private CategoriaService categoriaService;

    private Long idEditoraSelecionada;
    private Long idCategoriaSelecionada;

    // Componentes
    private JTextField txtTitulo, txtIsbn, txtAutor, txtEditora, txtCategoria;
    private JSpinner spnAno;
    private JButton btnAutor, btnEditora, btnCategoria, btnCadastrar, btnCancelar;

    public LivroAdicionar(LivroListar telaListar, LivroService service, AutorService autorService, EditoraService editoraService, CategoriaService categoriaService) {
        this.telaListar = telaListar;
        this.service = service;
        this.autorService = autorService;
        this.editoraService = editoraService;
        this.categoriaService = categoriaService;
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Cadastrar Livro");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- PAINEL FORMULÁRIO ---
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Header
        JLabel lblTitulo = new JLabel("CADASTRANDO LIVRO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlForm.add(lblTitulo, gbc);
        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Titulo
        gbc.gridx = 0; gbc.gridy = 1;
        pnlForm.add(new JLabel("Título:"), gbc);
        txtTitulo = new JTextField(30);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2;
        pnlForm.add(txtTitulo, gbc);
        gbc.gridwidth = 1;

        // ISBN
        gbc.gridx = 0; gbc.gridy = 2;
        pnlForm.add(new JLabel("ISBN:"), gbc);
        txtIsbn = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2;
        pnlForm.add(txtIsbn, gbc);
        gbc.gridwidth = 1;

        // Autor
        gbc.gridx = 0; gbc.gridy = 3;
        pnlForm.add(new JLabel("Autor:"), gbc);
        txtAutor = new JTextField(20);
        txtAutor.setEditable(false);
        gbc.gridx = 1; gbc.gridy = 3; 
        pnlForm.add(txtAutor, gbc);
        btnAutor = new JButton("Buscar Autor");
        estilizarBotao(btnAutor, new Color(52, 152, 219));
        gbc.gridx = 2; gbc.gridy = 3;
        pnlForm.add(btnAutor, gbc);

        // Editora
        gbc.gridx = 0; gbc.gridy = 4;
        pnlForm.add(new JLabel("Editora:"), gbc);
        txtEditora = new JTextField(20);
        txtEditora.setEditable(false);
        gbc.gridx = 1; gbc.gridy = 4;
        pnlForm.add(txtEditora, gbc);
        btnEditora = new JButton("Buscar Editora");
        estilizarBotao(btnEditora, new Color(52, 152, 219));
        gbc.gridx = 2; gbc.gridy = 4;
        pnlForm.add(btnEditora, gbc);

        // Categoria
        gbc.gridx = 0; gbc.gridy = 5;
        pnlForm.add(new JLabel("Categoria:"), gbc);
        txtCategoria = new JTextField(20);
        txtCategoria.setEditable(false);
        gbc.gridx = 1; gbc.gridy = 5;
        pnlForm.add(txtCategoria, gbc);
        btnCategoria = new JButton("Buscar Categoria");
        estilizarBotao(btnCategoria, new Color(52, 152, 219));
        gbc.gridx = 2; gbc.gridy = 5;
        pnlForm.add(btnCategoria, gbc);
        
        // Ano
        gbc.gridx = 0; gbc.gridy = 6;
        pnlForm.add(new JLabel("Ano Publicação:"), gbc);
        spnAno = new JSpinner();
        spnAno.setPreferredSize(new java.awt.Dimension(80, 25));
        gbc.gridx = 1; gbc.gridy = 6;
        pnlForm.add(spnAno, gbc);

        // Container Central
        JPanel pnlCenterContainer = new JPanel(new GridBagLayout());
        pnlCenterContainer.setBackground(new Color(240, 242, 245));
        pnlCenterContainer.add(pnlForm);
        add(pnlCenterContainer, BorderLayout.CENTER);

        // --- BOTOES SUL ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlBotoes.setBackground(Color.WHITE);
        pnlBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        btnCancelar = new JButton("Voltar");
        estilizarBotao(btnCancelar, new Color(99, 110, 114));
        
        btnCadastrar = new JButton("Cadastrar");
        estilizarBotao(btnCadastrar, new Color(46, 204, 113));

        pnlBotoes.add(btnCancelar);
        pnlBotoes.add(btnCadastrar);
        add(pnlBotoes, BorderLayout.SOUTH);
        
        // Listeners
        btnAutor.addActionListener(e -> {
            BuscarAutor tela = new BuscarAutor(this, autorService);
            tela.setVisible(true);
        });
        btnEditora.addActionListener(e -> {
            BuscarEditora tela = new BuscarEditora(this, editoraService);
            tela.setVisible(true);
        });
        btnCategoria.addActionListener(e -> {
            BuscarCategoria tela = new BuscarCategoria(this, categoriaService);
            tela.setVisible(true);
        });
        btnCancelar.addActionListener(e -> dispose());
        btnCadastrar.addActionListener(e -> salvarLivro());
    }

    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }
    
    public void setEditoraSelecionada(Long idEditora, String nomeEditora) {
        this.idEditoraSelecionada = idEditora;
        txtEditora.setText(nomeEditora);
    }

    public void setAutor(String nomeAutor){
        txtAutor.setText(nomeAutor);
    }

    public void setCategoriaSelecionada(Long idCategoria, String nomeCategoria) {
        this.idCategoriaSelecionada = idCategoria;
        txtCategoria.setText(nomeCategoria);
    }
    
    private void salvarLivro() {
        String titulo = txtTitulo.getText();
        String isbn = txtIsbn.getText();

        if (titulo.isEmpty() || isbn.isEmpty() || idCategoriaSelecionada == null || idEditoraSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos e selecione autor, editora e categoria!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Livro livro = new Livro();
            livro.setTitulo(titulo);
            livro.setIsbn(isbn);
            livro.setAnoPublicacao((Integer) spnAno.getValue());
            livro.setAutorNome(txtAutor.getText());

            Categoria categoria = new Categoria();
            categoria.setId(idCategoriaSelecionada);
            livro.setCategoria(categoria);
            livro.setCategoriaNome(txtCategoria.getText());

            Editora editora = new Editora();
            editora.setId(idEditoraSelecionada);
            livro.setEditora(editora);
            livro.setEditoraNome(txtEditora.getText());

            service.save(livro);

            JOptionPane.showMessageDialog(this, "Livro \"" + titulo + "\" adicionado com sucesso!");

            telaListar.carregarLivros();
            this.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar livro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
