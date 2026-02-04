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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;

@Component
@Scope("prototype")
public class LivroListar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LivroListar.class.getName());
    
    @Autowired
    private LivroService service;
    
    @Autowired
    private AutorService autorService;
    
    @Autowired
    private EditoraService editoraService;
    
    @Autowired
    private CategoriaService categoriaService;

    // Componentes Visuais
    private JTable tblLivros;
    private JTextField txtTitulo, txtIsbn, txtAutor, txtEditora, txtCategoria;
    private JSpinner spnAno;
    private JButton btnBuscar, btnLimpar, btnAdicionar, btnEditar, btnExcluir, btnAtualizar, btnVoltar;

    public LivroListar() {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }
    
    @PostConstruct
    public void init() {
        carregarLivros();    
    }

    private void initComponents() {
        setTitle("Gestão de Livros");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- PAINEL NORTE (AÇÕES + FILTROS) ---
        JPanel pnlNorte = new JPanel(new BorderLayout());
        
        // 1. Ações (Topo)
        JPanel pnlAcoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnlAcoes.setBackground(Color.WHITE);
        pnlAcoes.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        
        btnAdicionar = new JButton("Adicionar");
        estilizarBotao(btnAdicionar, new Color(46, 204, 113));
        pnlAcoes.add(btnAdicionar);
        
        btnEditar = new JButton("Editar");
        estilizarBotao(btnEditar, new Color(52, 152, 219));
        pnlAcoes.add(btnEditar);
        
        btnExcluir = new JButton("Excluir");
        estilizarBotao(btnExcluir, new Color(231, 76, 60));
        pnlAcoes.add(btnExcluir);
        
        btnAtualizar = new JButton("Atualizar");
        estilizarBotao(btnAtualizar, new Color(241, 196, 15));
        btnAtualizar.setForeground(Color.DARK_GRAY);
        pnlAcoes.add(btnAtualizar);
        
        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(99, 110, 114));
        pnlAcoes.add(btnVoltar);
        
        pnlNorte.add(pnlAcoes, BorderLayout.NORTH);
        
        // 2. Filtros
        JPanel pnlFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlFiltros.setBorder(BorderFactory.createTitledBorder("Filtros"));
        pnlFiltros.setBackground(new Color(250, 250, 250));
        
        pnlFiltros.add(new JLabel("Título:"));
        txtTitulo = new JTextField(12);
        pnlFiltros.add(txtTitulo);
        
        pnlFiltros.add(new JLabel("ISBN:"));
        txtIsbn = new JTextField(10);
        pnlFiltros.add(txtIsbn);
        
        pnlFiltros.add(new JLabel("Autor:"));
        txtAutor = new JTextField(10);
        pnlFiltros.add(txtAutor);
        
        pnlFiltros.add(new JLabel("Editora:"));
        txtEditora = new JTextField(10);
        pnlFiltros.add(txtEditora);
        
        pnlFiltros.add(new JLabel("Categ:"));
        txtCategoria = new JTextField(10);
        pnlFiltros.add(txtCategoria);
        
        pnlFiltros.add(new JLabel("Ano:"));
        spnAno = new JSpinner();
        spnAno.setPreferredSize(new java.awt.Dimension(60, 22));
        pnlFiltros.add(spnAno);
        
        btnBuscar = new JButton("Filtrar");
        estilizarBotao(btnBuscar, new Color(46, 204, 113));
        pnlFiltros.add(btnBuscar);
        
        btnLimpar = new JButton("Limpar");
        estilizarBotao(btnLimpar, new Color(127, 140, 141));
        pnlFiltros.add(btnLimpar);
        
        pnlNorte.add(pnlFiltros, BorderLayout.CENTER);
        
        add(pnlNorte, BorderLayout.NORTH);

        // --- TABELA ---
        String[] colunas = {"ID", "Título", "ISBN", "Autor", "Editora", "Categoria", "Ano"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblLivros = new JTable(modelo);
        tblLivros.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblLivros.setRowHeight(25);
        tblLivros.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        add(new JScrollPane(tblLivros), BorderLayout.CENTER);

        // LISTENERS
        btnAdicionar.addActionListener(e -> btnAdicionarActionPerformed(e));
        btnEditar.addActionListener(e -> btnEditarActionPerformed(e));
        btnExcluir.addActionListener(e -> btnExcluirActionPerformed(e));
        btnAtualizar.addActionListener(e -> carregarLivros());
        btnVoltar.addActionListener(e -> dispose());
        btnBuscar.addActionListener(e -> btnBuscarLivroActionPerformed(e));
        btnLimpar.addActionListener(e -> btnLimparFiltroActionPerformed(e));
    }
    
    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void btnAdicionarActionPerformed(ActionEvent evt) {
        LivroAdicionar tela = new LivroAdicionar(this, service, autorService, editoraService, categoriaService);
        tela.setVisible(true);
    }

    private void btnEditarActionPerformed(ActionEvent evt) {
        int linha = tblLivros.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um livro para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Long idLivro = Long.valueOf(tblLivros.getValueAt(linha, 0).toString());
        LivroEditar tela = new LivroEditar(idLivro, service, autorService, editoraService, categoriaService);
        tela.setVisible(true);
    }

    private void btnExcluirActionPerformed(ActionEvent evt) {
        int linha = tblLivros.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um livro para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Long idLivro = Long.valueOf(tblLivros.getValueAt(linha, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Livro livro = service.findById(idLivro);
                service.delete(livro);
                carregarLivros();
                JOptionPane.showMessageDialog(this, "Livro excluído com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnBuscarLivroActionPerformed(ActionEvent evt) {
        List<Livro> filtrados = service.findAll();
        
        if (!txtTitulo.getText().isBlank()) {
            String titulo = txtTitulo.getText().toLowerCase();
            filtrados = filtrados.stream().filter(l -> l.getTitulo().toLowerCase().contains(titulo)).toList();
        }
        if (!txtIsbn.getText().isBlank()) {
             filtrados = filtrados.stream().filter(l -> l.getIsbn().equals(txtIsbn.getText())).toList();
        }
        if (!txtAutor.getText().isBlank()) {
            String autor = txtAutor.getText().toLowerCase();
            filtrados = filtrados.stream().filter(l -> l.getAutorNome().toLowerCase().contains(autor)).toList();
        }
        // ... (Outros filtros simplificados para brevidade, mantendo a lógica original se necessário, ou assumindo que o usuário quer algo funcional)
        // Adicionando apenas os principais para demonstrar a padronização. Se necessário, adicionar Editora/Categoria/Ano
        
        if (!txtEditora.getText().isBlank()) {
            String ed = txtEditora.getText().toLowerCase();
            filtrados = filtrados.stream().filter(l -> l.getEditora() != null && l.getEditora().getNome().toLowerCase().contains(ed)).toList();
        }
        if (!txtCategoria.getText().isBlank()) {
            String cat = txtCategoria.getText().toLowerCase();
            filtrados = filtrados.stream().filter(l -> l.getCategoriaNome() != null && l.getCategoriaNome().toLowerCase().contains(cat)).toList();
        }
        
        int ano = (Integer) spnAno.getValue();
        if (ano > 0) {
             filtrados = filtrados.stream().filter(l -> l.getAnoPublicacao() == ano).toList();
        }

        DefaultTableModel model = (DefaultTableModel) tblLivros.getModel();
        model.setRowCount(0);
        for (Livro l : filtrados) {
            model.addRow(new Object[]{l.getId(), l.getTitulo(), l.getIsbn(), l.getAutorNome(), 
                l.getEditora() != null ? l.getEditora().getNome() : "", l.getCategoriaNome(), l.getAnoPublicacao()});
        }
    }

    private void btnLimparFiltroActionPerformed(ActionEvent evt) {
        txtTitulo.setText("");
        txtIsbn.setText("");
        txtAutor.setText("");
        txtEditora.setText("");
        txtCategoria.setText("");
        spnAno.setValue(0);
        carregarLivros();
    }

    public void carregarLivros() {
        try {
            List<Object[]> dados = service.findAllComNomeAutor();
            DefaultTableModel model = (DefaultTableModel) tblLivros.getModel();
            model.setRowCount(0);
            for (Object[] linha : dados) {
                model.addRow(linha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar livros: " + e.getMessage());
        }
    }
}
