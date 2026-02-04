package br.com.ifba.biblioteca.editora.view;

import org.springframework.stereotype.Component;
import br.com.ifba.biblioteca.editora.controller.EditoraIController;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import br.com.ifba.biblioteca.editora.entity.Editora;
import javax.swing.JOptionPane;

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
public class EditoraListar extends javax.swing.JFrame {
    
    private final EditoraIController controller;
    
    private JTable tblEditoras;
    private JTextField txtNome;
    private JButton btnFiltrar, btnAdicionar, btnEditar, btnInativar, btnAtualizar, btnVoltar;

    @Autowired
    public EditoraListar(EditoraIController controller) {
        this.controller = controller;
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        configurarTela();
        carregarTabela(controller.listarAtivas());
    }

    private void initComponents() {
        setTitle("Gestão de Editoras");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- PAINEL TOPO ---
        JPanel pnlTopo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnlTopo.setBackground(Color.WHITE);
        pnlTopo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        pnlTopo.add(new JLabel("Nome:"));
        txtNome = new JTextField(20);
        txtNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNome.addActionListener(e -> filtrarEditoras());
        pnlTopo.add(txtNome);
        
        btnFiltrar = new JButton("Filtrar");
        estilizarBotao(btnFiltrar, new Color(52, 152, 219));
        pnlTopo.add(btnFiltrar);
        
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
        String[] colunas = {"ID", "Nome", "CNPJ", "Telefone", "Endereço", "Situação"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
             public boolean isCellEditable(int row, int column) {
                 return false;
             }
        };
        tblEditoras = new JTable(modelo);
        tblEditoras.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblEditoras.setRowHeight(25);
        tblEditoras.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        add(new JScrollPane(tblEditoras), BorderLayout.CENTER);

        // LISTENERS
        btnFiltrar.addActionListener(e -> filtrarEditoras());
        btnAdicionar.addActionListener(e -> abrirTelaAdicionar());
        btnEditar.addActionListener(e -> editarEditora());
        btnInativar.addActionListener(e -> inativarEditora());
        btnAtualizar.addActionListener(e -> {
            txtNome.setText("");
            carregarTabela(controller.listarAtivas());
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
    
    private void configurarTela() {
        setLocationRelativeTo(null);
        // oculta a coluna de ID
        tblEditoras.getColumnModel().getColumn(0).setMinWidth(0);
        tblEditoras.getColumnModel().getColumn(0).setMaxWidth(0);
        tblEditoras.getColumnModel().getColumn(0).setWidth(0);
        tblEditoras.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    private void abrirTelaAdicionar() {
        EditoraAdicionar tela = new EditoraAdicionar(controller);
        tela.setLocationRelativeTo(this);
        tela.setVisible(true);
        tela.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                carregarTabela(controller.listarAtivas());
            }
        });
    }

    private void editarEditora() {
        int linha = tblEditoras.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma editora para editar.");
            return;
        }
        Long id = (Long) tblEditoras.getValueAt(linha, 0);
        Editora editora = controller.findById(id);
        if (editora == null) {
            JOptionPane.showMessageDialog(this, "Editora não encontrada!");
            return;
        }
        EditoraEditar telaEditar = new EditoraEditar(controller, id);
        telaEditar.setLocationRelativeTo(this);
        telaEditar.setVisible(true);
        telaEditar.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                carregarTabela(controller.listarAtivas());
            }
        });
    }

    private void filtrarEditoras() {
        String nome = txtNome.getText();
        carregarTabela(controller.filtrarPorNome(nome));
    }

    private void inativarEditora() {
        int linha = tblEditoras.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma editora para inativar.");
            return;
        }
        Long id = (Long) tblEditoras.getValueAt(linha, 0);
        int confirmar = JOptionPane.showConfirmDialog(this, "Deseja realmente inativar esta editora?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            controller.inativar(id);
            carregarTabela(controller.listarAtivas());
            JOptionPane.showMessageDialog(this, "Editora inativada com sucesso.");
        }
    }

    private void carregarTabela(List<Editora> editoras) {
        DefaultTableModel model = (DefaultTableModel) tblEditoras.getModel();
        model.setRowCount(0);
        for (Editora e : editoras) {
            model.addRow(new Object[]{
                e.getId(),
                e.getNome(),
                e.getCnpj(),
                e.getTelefone(),
                e.getEndereco(),
                e.getSituacao()
            });
        }
    }
}
