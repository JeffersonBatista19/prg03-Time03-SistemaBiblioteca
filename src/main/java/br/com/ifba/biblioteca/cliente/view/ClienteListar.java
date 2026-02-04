package br.com.ifba.biblioteca.cliente.view;

import br.com.ifba.biblioteca.cliente.controller.ClienteIController;
import br.com.ifba.biblioteca.cliente.entity.Cliente;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import org.springframework.stereotype.Component;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

@Component
public class ClienteListar extends javax.swing.JFrame {
    
    private final ClienteIController controller;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ClienteListar.class.getName());

    private JTable tblClientes;
    private JTextField txtNome;
    private JComboBox<String> cmbTipoCliente, cmbStatusCliente;
    private JButton btnFiltrar, btnAdicionar, btnEditar, btnAtualizar, btnVoltar;

    public ClienteListar(ClienteIController controller) {
        this.controller = controller;
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        configurarTela();
        carregarClientes();
    }
    
    private void initComponents() {
        setTitle("Gestão de Clientes");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- PAINEL NORTE (Filtros e Ações) ---
        JPanel pnlNorte = new JPanel(new BorderLayout());
        
        // 1. Painel de Ações (Topo)
        JPanel pnlAcoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnlAcoes.setBackground(Color.WHITE);
        pnlAcoes.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        
        btnAdicionar = new JButton("Adicionar");
        estilizarBotao(btnAdicionar, new Color(46, 204, 113));
        pnlAcoes.add(btnAdicionar);
        
        btnEditar = new JButton("Editar");
        estilizarBotao(btnEditar, new Color(52, 152, 219));
        pnlAcoes.add(btnEditar);
        
        btnAtualizar = new JButton("Atualizar");
        estilizarBotao(btnAtualizar, new Color(241, 196, 15));
        btnAtualizar.setForeground(Color.DARK_GRAY);
        pnlAcoes.add(btnAtualizar);
        
        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(99, 110, 114));
        pnlAcoes.add(btnVoltar);
        
        pnlNorte.add(pnlAcoes, BorderLayout.NORTH);
        
        // 2. Painel de Filtros (Centro)
        JPanel pnlFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlFiltros.setBorder(BorderFactory.createTitledBorder("Filtros de Pesquisa"));
        pnlFiltros.setBackground(new Color(250, 250, 250));
        
        pnlFiltros.add(new JLabel("Nome:"));
        txtNome = new JTextField(15);
        pnlFiltros.add(txtNome);
        
        pnlFiltros.add(new JLabel("Tipo:"));
        cmbTipoCliente = new JComboBox<>(new String[] { "", "ALUNO", "PROFESSOR", "VISITANTE" });
        pnlFiltros.add(cmbTipoCliente);
        
        pnlFiltros.add(new JLabel("Status:"));
        cmbStatusCliente = new JComboBox<>(new String[] { "", "ATIVO", "INATIVO", "BLOQUEADO" });
        pnlFiltros.add(cmbStatusCliente);
        
        btnFiltrar = new JButton("Filtrar");
        estilizarBotao(btnFiltrar, new Color(46, 204, 113));
        pnlFiltros.add(btnFiltrar);

        pnlNorte.add(pnlFiltros, BorderLayout.CENTER);
        
        add(pnlNorte, BorderLayout.NORTH);

        // --- TABELA ---
        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Tipo", "Limite", "Status"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblClientes = new JTable(modelo);
        tblClientes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblClientes.setRowHeight(25);
        tblClientes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        add(new JScrollPane(tblClientes), BorderLayout.CENTER);

        // LISTENERS
        btnAdicionar.addActionListener(e -> abrirTelaAdicionar());
        btnAtualizar.addActionListener(e -> carregarClientes());
        btnEditar.addActionListener(e -> editarCliente());
        btnFiltrar.addActionListener(e -> filtrarClientes());
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
        // Ocultar ID
        tblClientes.getColumnModel().getColumn(0).setMinWidth(0);
        tblClientes.getColumnModel().getColumn(0).setMaxWidth(0);
        tblClientes.getColumnModel().getColumn(0).setWidth(0);
    }
    
    private void abrirTelaAdicionar() {
        ClienteAdicionar tela = new ClienteAdicionar(controller);
        tela.setLocationRelativeTo(this);
        tela.setVisible(true);
        tela.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                carregarClientes();
            }
        });
    }

    private void editarCliente() {
        int linha = tblClientes.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Long idCliente = (Long) tblClientes.getValueAt(linha, 0);
        Cliente cliente = controller.findById(idCliente);
        ClienteEditar telaEditar = new ClienteEditar(controller, cliente);
        telaEditar.setLocationRelativeTo(this);
        telaEditar.setVisible(true);
        telaEditar.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                carregarClientes();
            }
        });
    }

    private void filtrarClientes() {
        String nome = txtNome.getText();
        String tipo = (String) cmbTipoCliente.getSelectedItem();
        String status = (String) cmbStatusCliente.getSelectedItem();
        
        // Ajuste para passar null se vazio, conforme pode esperar o controller ou manter string vazia
        if (tipo != null && tipo.isEmpty()) tipo = null;
        if (status != null && status.isEmpty()) status = null;

        List<Cliente> clientes = controller.filtrar(nome, tipo, status);
        preencherTabela(clientes);
    }

    private void carregarClientes() {
        logger.info("Carregando lista de clientes...");
        var clientes = controller.findAll();
        preencherTabela(clientes);
    }
    
    private void preencherTabela(List<Cliente> clientes) {
        DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();
        model.setRowCount(0);
        for (var c : clientes) {
            model.addRow(new Object[]{
                c.getId(),
                c.getNomeCompleto(),
                c.getCpf(),
                c.getTelefone(),
                c.getTipoCliente(),
                c.getLimiteEmprestimo(),
                c.getStatusPessoa()
            });
        }
    }
}
