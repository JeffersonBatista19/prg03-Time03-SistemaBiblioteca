package br.com.ifba.biblioteca.reserva.view;

import br.com.ifba.biblioteca.cliente.entity.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import br.com.ifba.biblioteca.cliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *
 * @author misae
 */

// tela responsável por buscar e selecionar um Cliente existente
// Integra-se com o módulo Cliente
@Component
public class BuscarCliente extends javax.swing.JFrame { 
                                                         
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(BuscarCliente.class.getName());

    // Tela que chamou esta (ReservaAdicionar) - LEGADO
    private  ReservaAdicionar telaPai;
    
    // Callback para retorno genérico 
    private java.util.function.Consumer<Cliente> callback;
    
    private  ClienteService clienteService;

    //construtor com injeção do serviço ClienteService
    @Autowired
    public BuscarCliente(ClienteService clienteService) {
        initComponents();
        this.clienteService = clienteService;

        logger.info("Tela BuscarCliente aberta");
        atualizarTabela(clienteService.findAll()); //atualiza a tabela com todos os clientes
    }
    
    public void setCallback(java.util.function.Consumer<Cliente> callback) {
        this.callback = callback;
    }
    
    public void setTelaPai(ReservaAdicionar telaPai) {
        this.telaPai = telaPai;
        setLocationRelativeTo(telaPai);// centraliza
    }

    
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSelecionar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClientes; // Renomeado de tblClientes1 para ficar padrao
    private javax.swing.JTextField txtCliente;

    private void initComponents() {
        setTitle("Listagem de Clientes");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new java.awt.BorderLayout());
        getContentPane().setBackground(new java.awt.Color(240, 242, 245));

        // --- PAINEL TOPO (Busca) ---
        javax.swing.JPanel pnlTopo = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 15));
        pnlTopo.setBackground(java.awt.Color.WHITE);
        pnlTopo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(200, 200, 200)));

        pnlTopo.add(new javax.swing.JLabel("Nome:"));
        txtCliente = new javax.swing.JTextField(30);
        pnlTopo.add(txtCliente);

        btnBuscar = new javax.swing.JButton("Buscar");
        estilizarBotao(btnBuscar, new java.awt.Color(52, 152, 219)); // Blue
        pnlTopo.add(btnBuscar);

        add(pnlTopo, java.awt.BorderLayout.NORTH);

        // --- TABELA CENTRAL ---
        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "TipoCliente", "Limite", "Status"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return false;
             }
        };
        
        tblClientes = new javax.swing.JTable(modelo);
        tblClientes.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        tblClientes.setRowHeight(25);
        tblClientes.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        tblClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        // Esconde ID
        tblClientes.getColumnModel().getColumn(0).setMinWidth(0);
        tblClientes.getColumnModel().getColumn(0).setMaxWidth(0);
        tblClientes.getColumnModel().getColumn(0).setWidth(0);
        
        tblClientes.getColumnModel().getColumn(1).setPreferredWidth(200); // Nome

        jScrollPane1 = new javax.swing.JScrollPane(tblClientes);
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
        btnBuscar.addActionListener(evt -> btnBuscarActionPerformed(evt));
        txtCliente.addActionListener(evt -> btnBuscarActionPerformed(evt)); // Enter no campo busca
        btnSelecionar.addActionListener(evt -> btnSelecionarActionPerformed(evt));
        btnCancelar.addActionListener(evt -> btnCancelarActionPerformed(evt));
        btnAtualizar.addActionListener(evt -> btnAtualizarActionPerformed(evt));

        pack();
        setSize(900, 600); // Um pouco maior pois tem mais colunas
    }

    private void estilizarBotao(javax.swing.JButton btn, java.awt.Color cor) {
        btn.setBackground(cor);
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    //Atualiza a tabela com a lista informada
    private void atualizarTabela(List<Cliente> clientes) {
        DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();
        model.setRowCount(0);

        for (Cliente c : clientes) {
            model.addRow(new Object[]{
                c.getId(), // ID escondido, mas necessário internamente
                c.getNomeCompleto(),
                c.getCpf(),
                c.getTelefone(),
                c.getTipoCliente(),
                c.getLimiteEmprestimo(),     // limite de empréstimos
                c.getStatusPessoa()      // ativo, inativo,
            });
        }
    }

    //Botão Buscar – filtra clientes pelo nome
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        String texto = txtCliente.getText().trim().toLowerCase();
        
        // Se não digitou nada, mostra todos
         if (texto.isEmpty()) {
            atualizarTabela(clienteService.findAll());
            return;
        }

        List<Cliente> clientes = clienteService.findAll();
        List<Cliente> filtrados = new ArrayList<>();

        for (Cliente c : clientes) {
            if (c.getNomeCompleto().toLowerCase().contains(texto)) {
            filtrados.add(c);
            }
        }

        atualizarTabela(filtrados);

        if (filtrados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente encontrado.");
        }
    }

    //envia o cliente selecionado para a tela de reserva
    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {
        int linha = tblClientes.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente.");
            return;
        }

        Long clienteId = (Long) tblClientes.getValueAt(linha, 0);
        Cliente cliente = clienteService.findById(clienteId);

        // Envia o cliente selecionado
        if (callback != null) {
            callback.accept(cliente);
        } else if (telaPai != null) {
            telaPai.setClienteSelecionado(cliente);
        }

        this.dispose(); // Fecha a tela após selecionar
    }

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
         this.dispose();
    }

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {
        // atualiza a tabela com todos os clientes cadastrados
        atualizarTabela(clienteService.findAll());
        txtCliente.setText(""); // limpa o campo de busca
    }
}
