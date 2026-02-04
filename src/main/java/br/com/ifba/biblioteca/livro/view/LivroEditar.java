package br.com.ifba.biblioteca.livro.view;

import br.com.ifba.biblioteca.autor.service.AutorService;
import br.com.ifba.biblioteca.categoria.entity.Categoria;
import br.com.ifba.biblioteca.categoria.service.CategoriaService;
import br.com.ifba.biblioteca.editora.entity.Editora;
import br.com.ifba.biblioteca.editora.service.EditoraService;
import br.com.ifba.biblioteca.livro.entity.Livro;
import br.com.ifba.biblioteca.livro.service.LivroService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class LivroEditar extends JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LivroEditar.class.getName());
    
    private String autorSelecionado;
    private Long idEditoraSelecionada;
    private Long idCategoriaSelecionada;
    private LivroService service;
    private Long idLivro;
    private AutorService autorService;
    private EditoraService editoraService;
    private CategoriaService categoriaService;

    // Componentes
    private JTextField txtTitulo, txtIsbn, txtAutor, txtEditora, txtCategoria;
    private JSpinner spnAno;
    private JButton btnAutor, btnEditora, btnCategoria;
    private JButton btnSalvar, btnCancelar;

    public LivroEditar(Long idLivro, LivroService service, AutorService autorService,  EditoraService editoraService, CategoriaService categoriaService) {
        this.service = service;
        this.idLivro = idLivro;
        this.autorService = autorService;
        this.editoraService = editoraService;
        this.categoriaService = categoriaService;

        initComponents();
        
        // Configurações de campos não editáveis
        txtIsbn.setEditable(false);
        spnAno.setEnabled(false);
        txtAutor.setEditable(false);
        txtEditora.setEditable(false);
        txtCategoria.setEditable(false);

        carregarLivro();
    }

    private void initComponents() {
        setTitle("Editar Livro");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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

        // Título
        JLabel lblTitulo = new JLabel("EDITAR LIVRO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlForm.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos
        adicionarCampo(pnlForm, gbc, 1, "Título:", txtTitulo = new JTextField(30));
        adicionarCampo(pnlForm, gbc, 2, "ISBN (Não editável):", txtIsbn = new JTextField(20));

        // Autor com Busca
        gbc.gridx = 0; gbc.gridy = 3;
        pnlForm.add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1;
        txtAutor = new JTextField(20);
        pnlForm.add(txtAutor, gbc);
        btnAutor = new JButton("Buscar");
        configurarBotaoBusca(btnAutor);
        btnAutor.addActionListener(e -> buscarAutor());
        gbc.gridx = 2;
        pnlForm.add(btnAutor, gbc);

        // Editora com Busca
        gbc.gridx = 0; gbc.gridy = 4;
        pnlForm.add(new JLabel("Editora:"), gbc);
        gbc.gridx = 1;
        txtEditora = new JTextField(20);
        pnlForm.add(txtEditora, gbc);
        btnEditora = new JButton("Buscar");
        configurarBotaoBusca(btnEditora);
        btnEditora.addActionListener(e -> buscarEditora());
        gbc.gridx = 2;
        pnlForm.add(btnEditora, gbc);

        // Categoria com Busca
        gbc.gridx = 0; gbc.gridy = 5;
        pnlForm.add(new JLabel("Categoria:"), gbc);
        gbc.gridx = 1;
        txtCategoria = new JTextField(20);
        pnlForm.add(txtCategoria, gbc);
        btnCategoria = new JButton("Buscar");
        configurarBotaoBusca(btnCategoria);
        btnCategoria.addActionListener(e -> buscarCategoria());
        gbc.gridx = 2;
        pnlForm.add(btnCategoria, gbc);
        
        // Ano
        gbc.gridx = 0; gbc.gridy = 6;
        pnlForm.add(new JLabel("Ano Publicação (Fixo):"), gbc);
        gbc.gridx = 1;
        spnAno = new JSpinner();
        pnlForm.add(spnAno, gbc);

        // Container Central
        JPanel pnlCenterContainer = new JPanel(new GridBagLayout());
        pnlCenterContainer.setBackground(new Color(240, 242, 245));
        pnlCenterContainer.add(pnlForm);
        add(pnlCenterContainer, BorderLayout.CENTER);

        // --- BOTÕES ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlBotoes.setBackground(Color.WHITE);
        pnlBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        btnCancelar = new JButton("Cancelar");
        estilizarBotao(btnCancelar, new Color(99, 110, 114));
        btnCancelar.addActionListener(e -> dispose());
        
        btnSalvar = new JButton("Salvar Alterações");
        estilizarBotao(btnSalvar, new Color(46, 204, 113));
        btnSalvar.addActionListener(e -> salvarAlteracoes());

        pnlBotoes.add(btnCancelar);
        pnlBotoes.add(btnSalvar);
        add(pnlBotoes, BorderLayout.SOUTH);
    }
    
    private void adicionarCampo(JPanel pnl, GridBagConstraints gbc, int row, String label, java.awt.Component comp) {
        gbc.gridx = 0; gbc.gridy = row;
        pnl.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.gridy = row;
        pnl.add(comp, gbc);
    }

    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }
    
    private void configurarBotaoBusca(JButton btn) {
        btn.setBackground(new Color(52, 152, 219));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

    private void carregarLivro() {
        try {
            Livro livro = service.findById(idLivro);

            txtTitulo.setText(livro.getTitulo());
            txtAutor.setText(livro.getAutorNome());
            autorSelecionado = livro.getAutorNome(); // Inicializa variável interna
            txtIsbn.setText(livro.getIsbn());
            spnAno.setValue(livro.getAnoPublicacao() > 0 ? livro.getAnoPublicacao() : 0);

            if (livro.getCategoria() != null) {
                idCategoriaSelecionada = livro.getCategoria().getId();
                txtCategoria.setText(livro.getCategoriaNome());
            }

            if (livro.getEditora() != null) {
                idEditoraSelecionada = livro.getEditora().getId();
                txtEditora.setText(livro.getEditoraNome());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar livro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void salvarAlteracoes() {
        try {
            Livro livro = service.findById(idLivro);

            livro.setTitulo(txtTitulo.getText().trim());
            livro.setAutorNome(autorSelecionado);

            // Editora
            Editora editora = new Editora();
            editora.setId(idEditoraSelecionada);
            livro.setEditora(editora);
            livro.setEditoraNome(txtEditora.getText());

            // Categoria
            Categoria categoria = new Categoria();
            categoria.setId(idCategoriaSelecionada);
            livro.setCategoria(categoria);
            livro.setCategoriaNome(txtCategoria.getText());

            // Validação de duplicidade
            Livro livroAtual = service.findById(idLivro);
            if (!livroAtual.getTitulo().equalsIgnoreCase(livro.getTitulo()) && service.existsByTitulo(livro.getTitulo())) {
                int opcao = JOptionPane.showConfirmDialog(this, 
                    "Já existe um livro com esse título.\nDeseja continuar mesmo assim?",
                    "Título duplicado", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (opcao != JOptionPane.YES_OPTION) return;
            }

            service.update(livro);
            JOptionPane.showMessageDialog(this, "Livro atualizado com sucesso!");
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar livro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarCategoria() {
        BuscarCategoria tela = new BuscarCategoria(this, categoriaService);
        tela.setLocationRelativeTo(this);
        tela.setVisible(true);
    }

    private void buscarEditora() {
        BuscarEditora tela = new BuscarEditora(this, editoraService);
        tela.setLocationRelativeTo(this);
        tela.setVisible(true);
    }

    private void buscarAutor() {
        BuscarAutor tela = new BuscarAutor(this, autorService);
        tela.setLocationRelativeTo(this);
        tela.setVisible(true);
    }
}
