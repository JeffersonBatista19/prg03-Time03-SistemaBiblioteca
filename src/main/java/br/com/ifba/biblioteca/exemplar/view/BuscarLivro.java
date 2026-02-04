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

    private final LivroService service;
    private ExemplarAdicionar telaExemplar;

    @Autowired
    public BuscarLivro(LivroService service) {
        this.service = service;
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void setTelaExemplar(ExemplarAdicionar telaExemplar) {
        this.telaExemplar = telaExemplar;
    }


    
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnBuscarISBN;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSelecionar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblLivros1;
    private javax.swing.JTextField txtIsbn;

    private void initComponents() {
        setTitle("Listagem de Livros");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new java.awt.BorderLayout());
        getContentPane().setBackground(new java.awt.Color(240, 242, 245));

        // --- PAINEL TOPO (Busca) ---
        javax.swing.JPanel pnlTopo = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 15));
        pnlTopo.setBackground(java.awt.Color.WHITE);
        pnlTopo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(200, 200, 200)));

        pnlTopo.add(new javax.swing.JLabel("ISBN:"));
        txtIsbn = new javax.swing.JTextField(15);
        pnlTopo.add(txtIsbn);

        btnBuscarISBN = new javax.swing.JButton("Buscar");
        estilizarBotao(btnBuscarISBN, new java.awt.Color(52, 152, 219)); // Blue
        pnlTopo.add(btnBuscarISBN);

        add(pnlTopo, java.awt.BorderLayout.NORTH);

        // --- TABELA CENTRAL ---
        String[] colunas = {"ID", "Título", "ISBN", "Autor", "Editora", "Categoria", "Ano Publicação"};
        // Modelo não editável
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return false;
             }
        };
        
        tblLivros1 = new javax.swing.JTable(modelo);
        tblLivros1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        tblLivros1.setRowHeight(25);
        tblLivros1.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        tblLivros1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        // Esconder ID se quiser, mas deixarei visível pois a foto mostra
        tblLivros1.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblLivros1.getColumnModel().getColumn(1).setPreferredWidth(200);

        jScrollPane2 = new javax.swing.JScrollPane(tblLivros1);
        add(jScrollPane2, java.awt.BorderLayout.CENTER);

        // --- PAINEL BOTOES (Sul) ---
        javax.swing.JPanel pnlSul = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 15, 15));
        pnlSul.setBackground(java.awt.Color.WHITE);
        pnlSul.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(200, 200, 200)));

        btnSelecionar = new javax.swing.JButton("Selecionar");
        estilizarBotao(btnSelecionar, new java.awt.Color(46, 204, 113)); // Green
        
        btnAtualizar = new javax.swing.JButton("Atualizar");
        estilizarBotao(btnAtualizar, new java.awt.Color(241, 196, 15)); // Yellow
        btnAtualizar.setForeground(java.awt.Color.DARK_GRAY);

        btnCancelar = new javax.swing.JButton("Cancelar");
        estilizarBotao(btnCancelar, new java.awt.Color(231, 76, 60)); // Red

        pnlSul.add(btnAtualizar);
        pnlSul.add(btnCancelar);
        pnlSul.add(btnSelecionar);

        add(pnlSul, java.awt.BorderLayout.SOUTH);

        // Listeners
        btnBuscarISBN.addActionListener(evt -> btnBuscarISBNActionPerformed(evt));
        btnSelecionar.addActionListener(evt -> btnSelecionarActionPerformed(evt));
        btnCancelar.addActionListener(evt -> btnCancelarActionPerformed(evt));
        btnAtualizar.addActionListener(evt -> btnAtualizarActionPerformed(evt));
        
        // Enter no campo de busca
        txtIsbn.addActionListener(evt -> btnBuscarISBNActionPerformed(evt));

        pack();
        setSize(800, 500); // Tamanho inicial razoável
    }

    private void estilizarBotao(javax.swing.JButton btn, java.awt.Color cor) {
        btn.setBackground(cor);
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void btnBuscarISBNActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String isbn = txtIsbn.getText();
            if (isbn == null || isbn.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite um ISBN para buscar.");
                return;
            }
            Livro livro = service.findByIsbn(isbn.trim());
            DefaultTableModel model = (DefaultTableModel) tblLivros1.getModel();
            model.setRowCount(0);

            if (livro != null) {
                String catNome = livro.getCategoriaNome();
                if (catNome == null && livro.getCategoria() != null) catNome = livro.getCategoria().getNome();
                
                String edtNome = livro.getEditoraNome();
                if (edtNome == null && livro.getEditora() != null) edtNome = livro.getEditora().getNome();

                model.addRow(new Object[]{
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getIsbn(),
                    livro.getAutorNome(),
                    edtNome,
                    catNome,
                    livro.getAnoPublicacao()
                });
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum livro encontrado com esse ISBN.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar livro: " + e.getMessage());
        }
    }

    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {
        int row = tblLivros1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um livro.");
            return;
        }
        String isbn = tblLivros1.getValueAt(row, 2).toString();
        if (telaExemplar != null) {
            telaExemplar.setIsbnLivro(isbn);
        }
        dispose();
    }

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {
        carregarLivros();
    }

    public void carregarLivros() {
        try {
            List<Livro> livros = service.findAll();
            DefaultTableModel model = (DefaultTableModel) tblLivros1.getModel();
            model.setRowCount(0);
            for (Livro l : livros) {
                String catNome = l.getCategoriaNome();
                if (catNome == null && l.getCategoria() != null) catNome = l.getCategoria().getNome();
                
                String edtNome = l.getEditoraNome();
                if (edtNome == null && l.getEditora() != null) edtNome = l.getEditora().getNome();

                model.addRow(new Object[]{
                    l.getId(),
                    l.getTitulo(),
                    l.getIsbn(),
                    l.getAutorNome(),
                    edtNome,
                    catNome,
                    l.getAnoPublicacao()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar livros: " + e.getMessage());
        }
    }
}
