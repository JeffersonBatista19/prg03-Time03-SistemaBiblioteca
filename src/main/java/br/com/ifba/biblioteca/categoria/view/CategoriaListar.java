package br.com.ifba.biblioteca.categoria.view;

import br.com.ifba.biblioteca.categoria.controller.CategoriaIController;
import br.com.ifba.biblioteca.categoria.entity.Categoria;
import jakarta.annotation.PostConstruct;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
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
import javax.swing.JTable;
import javax.swing.JTextField;

@Component
@Scope("prototype")
public class CategoriaListar extends javax.swing.JFrame {
    
    @Autowired
    private CategoriaIController categoriaController;

    private JTable tblCategorias;
    private JTextField txtBuscaNome;
    private JButton btnBuscar, btnLimpar, btnAdicionar, btnEditar, btnInativar, btnAtualizar, btnVoltar;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CategoriaListar.class.getName());

    public CategoriaListar() {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }
    
    @PostConstruct
    public void init() {
        carregarCategorias();
    }
    
    private void initComponents() {
        setTitle("Gestão de Categorias");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- PAINEL TOPO ---
        JPanel pnlTopo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnlTopo.setBackground(Color.WHITE);
        pnlTopo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        pnlTopo.add(new JLabel("Nome:"));
        txtBuscaNome = new JTextField(20);
        txtBuscaNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscaNome.addActionListener(e -> buscarPorNome());
        pnlTopo.add(txtBuscaNome);
        
        btnBuscar = new JButton("Buscar");
        estilizarBotao(btnBuscar, new Color(52, 152, 219));
        pnlTopo.add(btnBuscar);
        
        btnAdicionar = new JButton("Adicionar");
        estilizarBotao(btnAdicionar, new Color(46, 204, 113));
        pnlTopo.add(btnAdicionar);
        
        btnEditar = new JButton("Editar");
        estilizarBotao(btnEditar, new Color(243, 156, 18));
        pnlTopo.add(btnEditar);
        
        btnInativar = new JButton("Inativar");
        estilizarBotao(btnInativar, new Color(231, 76, 60));
        pnlTopo.add(btnInativar);
        
        btnAtualizar = new JButton("Atualizar");
        estilizarBotao(btnAtualizar, new Color(241, 196, 15));
        btnAtualizar.setForeground(Color.DARK_GRAY);
        pnlTopo.add(btnAtualizar);
        
        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(99, 110, 114));
        pnlTopo.add(btnVoltar);
        
        add(pnlTopo, BorderLayout.NORTH);

        // --- TABELA ---
        String[] colunas = {"ID", "Nome", "Descrição"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
             public boolean isCellEditable(int row, int column) {
                 return false;
             }
        };
        tblCategorias = new JTable(modelo);
        tblCategorias.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblCategorias.setRowHeight(25);
        tblCategorias.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        add(new JScrollPane(tblCategorias), BorderLayout.CENTER);

        // LISTENERS
        btnBuscar.addActionListener(e -> buscarPorNome());
        btnLimpar = new JButton("Limpar"); // Botão extra não adicionado ao painel visualmente antes, mas pode ser util na lógica
        // Para manter consistencia com o codigo anterior e UI, o botao buscar ja faz o filtro.
        // O Atualizar serve como reset.
        
        btnAdicionar.addActionListener(e -> {
            try {
                CategoriaAdicionar tela = new CategoriaAdicionar(this, categoriaController);
                tela.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnEditar.addActionListener(e -> editarCategoria());
        btnInativar.addActionListener(e -> inativarCategoria());
        btnAtualizar.addActionListener(e -> {
            txtBuscaNome.setText("");
            carregarCategorias();
        });
        btnVoltar.addActionListener(e -> dispose());
    }
    
    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void carregarCategorias() {
        try {
            List<Categoria> categorias = categoriaController.findAllAtivas();
            preencherTabela(categorias);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void preencherTabela(List<Categoria> categorias) {
        DefaultTableModel modelo = (DefaultTableModel) tblCategorias.getModel();
        modelo.setRowCount(0);
        if (categorias == null) return;
        for (Categoria c : categorias) {
            modelo.addRow(new Object[]{c.getId(), c.getNome(), c.getDescricao()});
        }
    }
    
    private Long getIdSelecionadoDaTabela() {
        int linha = tblCategorias.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma categoria na tabela.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        Object valorId = tblCategorias.getValueAt(linha, 0);
        if (valorId == null) return null;
        try {
            return Long.valueOf(valorId.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void buscarPorNome() {
        try {
            String nome = txtBuscaNome.getText();
            List<Categoria> resultado = categoriaController.findByNome(nome);
            preencherTabela(resultado);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro na busca", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void inativarCategoria() {
        Long id = getIdSelecionadoDaTabela();
        if (id == null) return;
        int opcao = JOptionPane.showConfirmDialog(this,"Deseja realmente INATIVAR esta categoria?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcao != JOptionPane.YES_OPTION) return;
        try {
            categoriaController.inativar(id);
            JOptionPane.showMessageDialog(this, "Categoria inativada com sucesso.");
            carregarCategorias();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarCategoria() {
        Long id = getIdSelecionadoDaTabela();
        if (id == null) return;
        try {
            Categoria categoria = categoriaController.findByIdAtiva(id);
            CategoriaEditar tela = new CategoriaEditar(this, categoriaController, categoria);
            tela.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void atualizarLista() {
        carregarCategorias();
    }
}
