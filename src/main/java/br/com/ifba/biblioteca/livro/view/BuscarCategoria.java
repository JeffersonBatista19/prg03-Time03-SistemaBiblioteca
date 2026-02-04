package br.com.ifba.biblioteca.livro.view;

import br.com.ifba.biblioteca.categoria.entity.Categoria;
import br.com.ifba.biblioteca.categoria.service.CategoriaService;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class BuscarCategoria extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BuscarCategoria.class.getName());
    private LivroAdicionar telaLivro;
    private LivroEditar telaEditar;
    private CategoriaService categoriaService;

    
    public BuscarCategoria(LivroAdicionar telaLivro, CategoriaService categoriaService) {
        this.telaLivro = telaLivro;
        this.categoriaService = categoriaService;
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        carregarCategorias();
        setLocationRelativeTo(null);
    }
    
    public BuscarCategoria(LivroEditar telaEditar, CategoriaService categoriaService) {
        this.telaEditar = telaEditar;
        this.categoriaService = categoriaService;
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        carregarCategorias();
        setLocationRelativeTo(null);
    }
    
    private void carregarCategorias() {
    try {
        DefaultTableModel model =
            (DefaultTableModel) tblCategoria.getModel();
        model.setRowCount(0);

        List<Categoria> categorias = categoriaService.findAllAtivas();

        for (Categoria c : categorias) {
            model.addRow(new Object[]{
                c.getId(),
                c.getNome(),
                c.getDescricao()
            });
        }

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Erro ao carregar categorias: " + e.getMessage(),
            "Erro",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
}




    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnBuscarId;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnLimparID;
    private javax.swing.JButton btnSelecionar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spnID;
    private javax.swing.JTable tblCategoria;

    private void initComponents() {
        setTitle("Listagem de Categorias");
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
        String[] colunas = {"ID", "Nome", "Descrição"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return false;
             }
        };
        
        tblCategoria = new javax.swing.JTable(modelo);
        tblCategoria.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        tblCategoria.setRowHeight(25);
        tblCategoria.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        tblCategoria.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        tblCategoria.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
        tblCategoria.getColumnModel().getColumn(1).setPreferredWidth(200); // Nome

        jScrollPane1 = new javax.swing.JScrollPane(tblCategoria);
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

    private void btnBuscarIdActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int id = (int) spnID.getValue();
            DefaultTableModel model = (DefaultTableModel) tblCategoria.getModel();
            model.setRowCount(0);

            if (id == 0) {
                 carregarCategorias();
                 return;
            }

            Categoria c = categoriaService.findByIdAtiva((long) id);
            if (c != null) {
                model.addRow(new Object[]{
                    c.getId(),
                    c.getNome(),
                    c.getDescricao()
                });
            } else {
                 javax.swing.JOptionPane.showMessageDialog(this, "Categoria não encontrada.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
             javax.swing.JOptionPane.showMessageDialog(this, "Categoria não encontrada ou erro inesperado.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    private void btnLimparIDActionPerformed(java.awt.event.ActionEvent evt) {
        spnID.setValue(0);
        carregarCategorias();
    }

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {
        carregarCategorias();
    }

    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {
        int linhaSelecionada = tblCategoria.getSelectedRow();
        if (linhaSelecionada == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecione uma categoria na tabela.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long idCategoria = Long.valueOf(tblCategoria.getValueAt(linhaSelecionada, 0).toString());
        String nomeCategoria = tblCategoria.getValueAt(linhaSelecionada, 1).toString();

        if (telaLivro != null) {
            telaLivro.setCategoriaSelecionada(idCategoria, nomeCategoria);
        } else if (telaEditar != null) {
            telaEditar.setCategoriaSelecionada(idCategoria, nomeCategoria);
        }
        dispose();
    }
}

