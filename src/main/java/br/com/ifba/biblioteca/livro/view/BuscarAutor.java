package br.com.ifba.biblioteca.livro.view;

import br.com.ifba.biblioteca.autor.entity.Autor;
import br.com.ifba.biblioteca.autor.service.AutorService;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class BuscarAutor extends javax.swing.JFrame {
    
     // referência da tela que chamou (LivroAdicionar)
    private LivroAdicionar telaLivro;
    private LivroEditar telaEditar;
    private AutorService autorService;

    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BuscarAutor.class.getName());
    
    // construtor que recebe a tela adicionar e o autor.
    public BuscarAutor(LivroAdicionar telaLivro, AutorService autorService) {
    this.telaLivro = telaLivro;
    this.autorService = autorService;
    initComponents();
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    carregarAutores();
    setLocationRelativeTo(null);
}

     // construtor que recebe a tela editar e o autor.
public BuscarAutor(LivroEditar telaEditar, AutorService autorService) {
    this.telaEditar = telaEditar;
    this.autorService = autorService;
    initComponents();
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    carregarAutores();
    setLocationRelativeTo(null);
}



    // carrega os autores existente no banco.
     private void carregarAutores() {
       DefaultTableModel model = (DefaultTableModel) tblAutores.getModel();
    model.setRowCount(0);

    List<Autor> autores = autorService.findAll();

    for (Autor autor : autores) {
        model.addRow(new Object[]{
            autor.getId(),
            autor.getNome(),
            autor.getNacionalidade(),
            autor.getBiografia(),
            autor.getQuantidadeObras()
        });
    }
    }
 
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnBuscarId;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnLimparID;
    private javax.swing.JButton btnSelecionar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spnID;
    private javax.swing.JTable tblAutores;

    private void initComponents() {
        setTitle("Listagem de Autores");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new java.awt.BorderLayout());
        getContentPane().setBackground(new java.awt.Color(240, 242, 245));

        // --- PAINEL TOPO (Busca) ---
        javax.swing.JPanel pnlTopo = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 15));
        pnlTopo.setBackground(java.awt.Color.WHITE);
        pnlTopo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(200, 200, 200)));

        pnlTopo.add(new javax.swing.JLabel("ID:"));
        spnID = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        spnID.setPreferredSize(new java.awt.Dimension(80, 25));
        pnlTopo.add(spnID);

        btnBuscarId = new javax.swing.JButton("Buscar");
        estilizarBotao(btnBuscarId, new java.awt.Color(52, 152, 219)); // Blue
        pnlTopo.add(btnBuscarId);

        btnLimparID = new javax.swing.JButton("Limpar");
        estilizarBotao(btnLimparID, new java.awt.Color(99, 110, 114)); // Grey
        pnlTopo.add(btnLimparID);

        add(pnlTopo, java.awt.BorderLayout.NORTH);

        // --- TABELA CENTRAL ---
        String[] colunas = {"ID", "Nome", "Nacionalidade", "Biografia", "Qtd de obras"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return false;
             }
        };
        
        tblAutores = new javax.swing.JTable(modelo);
        tblAutores.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        tblAutores.setRowHeight(25);
        tblAutores.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        tblAutores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        tblAutores.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
        tblAutores.getColumnModel().getColumn(1).setPreferredWidth(200); // Nome

        jScrollPane1 = new javax.swing.JScrollPane(tblAutores);
        add(jScrollPane1, java.awt.BorderLayout.CENTER);

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
        btnBuscarId.addActionListener(evt -> btnBuscarIdActionPerformed(evt));
        btnLimparID.addActionListener(evt -> btnLimparIDActionPerformed(evt));
        btnSelecionar.addActionListener(evt -> btnSelecionarActionPerformed(evt));
        btnCancelar.addActionListener(evt -> btnCancelarActionPerformed(evt));
        btnAtualizar.addActionListener(evt -> btnAtualizarActionPerformed(evt));

        pack();
        setSize(800, 500);
    }

    private void estilizarBotao(javax.swing.JButton btn, java.awt.Color cor) {
        btn.setBackground(cor);
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {
        int linhaSelecionada = tblAutores.getSelectedRow();
        if (linhaSelecionada == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecione um autor na tabela.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nomeAutor = tblAutores.getValueAt(linhaSelecionada, 1).toString();
        if (telaLivro != null) {
            telaLivro.setAutor(nomeAutor);
        }
        if (telaEditar != null) {
            telaEditar.setAutor(nomeAutor);
        }
        dispose();
    }

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private void btnBuscarIdActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            Long id = Long.valueOf(spnID.getValue().toString());
            DefaultTableModel model = (DefaultTableModel) tblAutores.getModel();
            model.setRowCount(0);

            if (id == 0) { // Se for 0, busca todos (comportamento padrão de "Limpar")
                 carregarAutores();
                 return;
            }

            Autor autor = autorService.findById(id);
            if (autor != null) {
                model.addRow(new Object[]{
                    autor.getId(),
                    autor.getNome(),
                    autor.getNacionalidade(),
                    autor.getBiografia(),
                    autor.getQuantidadeObras()
                });
            } else {
                 javax.swing.JOptionPane.showMessageDialog(this, "Autor não encontrado.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Autor não encontrado ou erro inesperado.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    private void btnLimparIDActionPerformed(java.awt.event.ActionEvent evt) {
        spnID.setValue(0);
        carregarAutores();
    }

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {
        carregarAutores();
    }
}

