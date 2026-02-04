package br.com.ifba.biblioteca.usuario.view;

import br.com.ifba.biblioteca.usuario.controller.UsuarioIController;
import br.com.ifba.biblioteca.usuario.entity.Usuario;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

@Component
public class UsuarioListar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UsuarioListar.class.getName());
    
    private TableRowSorter<DefaultTableModel> sorter;

    @Autowired
    private UsuarioIController usuarioController;

    @Autowired
    private ApplicationContext context;

    // Componentes Visuais
    private JTable tblUsuarios;
    private JTextField txtBuscar;
    private JButton btnAdicionar, btnEditar, btnAtualizar, btnVoltar;

    public UsuarioListar() {
        initComponents(); // Inicia com o construtor vazio pra configurar layout básico se precisar antes do PostConstruct
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }


    private void initComponents() {
        setTitle("Gestão de Usuários");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- PAINEL TOPO ---
        JPanel pnlTopo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnlTopo.setBackground(Color.WHITE);
        pnlTopo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        pnlTopo.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(25);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                aplicarFiltro();
            }
        });
        pnlTopo.add(txtBuscar);

        btnAdicionar = new JButton("Adicionar");
        estilizarBotao(btnAdicionar, new Color(46, 204, 113)); // Verde
        pnlTopo.add(btnAdicionar);

        btnEditar = new JButton("Editar");
        estilizarBotao(btnEditar, new Color(52, 152, 219)); // Azul
        pnlTopo.add(btnEditar);
        
        btnAtualizar = new JButton("Atualizar");
        estilizarBotao(btnAtualizar, new Color(241, 196, 15)); // Amarelo
        btnAtualizar.setForeground(Color.DARK_GRAY);
        pnlTopo.add(btnAtualizar);
        
        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(99, 110, 114)); // Cinza
        pnlTopo.add(btnVoltar);

        add(pnlTopo, BorderLayout.NORTH);

        // --- TABELA ---
        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Login", "Perfil", "Nível", "Status"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblUsuarios = new JTable(modelo);
        tblUsuarios.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblUsuarios.setRowHeight(25);
        tblUsuarios.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        sorter = new TableRowSorter<>(modelo);
        tblUsuarios.setRowSorter(sorter);
        
        add(new JScrollPane(tblUsuarios), BorderLayout.CENTER);

        // EVENTOS
        btnAdicionar.addActionListener(e -> btnAdicionarActionPerformed(e));
        btnEditar.addActionListener(e -> btnEditarActionPerformed(e));
        btnAtualizar.addActionListener(e -> carregarDados());
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
    
    public void carregarDados() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblUsuarios.getModel();
            modelo.setRowCount(0);

            List<Usuario> lista = usuarioController.findAll();

            for (Usuario u : lista) {
                modelo.addRow(new Object[]{
                    u.getId(),
                    u.getNomeCompleto(),
                    u.getCpf(),
                    u.getTelefone(),
                    u.getLogin(),
                    u.getTipoPerfil(),
                    u.getNivelAcesso(),
                    u.getStatusPessoa()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuários: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
     private void aplicarFiltro() {
        String texto = txtBuscar.getText().trim();
        if (texto.isEmpty()) {
            sorter.setRowFilter(null);
            return;
        }
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
    }

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            UsuarioAdicionar tela = context.getBean(UsuarioAdicionar.class);
            tela.setUsuarioListar(this);
            tela.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            tela.setLocationRelativeTo(null);
            tela.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir tela: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {
       int linhaSelecionada = tblUsuarios.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para editar!");
            return;
        }

        int linhaModelo = tblUsuarios.convertRowIndexToModel(linhaSelecionada);

        Long id = (Long) tblUsuarios.getModel().getValueAt(linhaModelo, 0);
        String nome = (String) tblUsuarios.getModel().getValueAt(linhaModelo, 1);
        String cpf = (String) tblUsuarios.getModel().getValueAt(linhaModelo, 2);
        String telefone = (String) tblUsuarios.getModel().getValueAt(linhaModelo, 3);
        String login = (String) tblUsuarios.getModel().getValueAt(linhaModelo, 4);
        String perfil = String.valueOf(tblUsuarios.getModel().getValueAt(linhaModelo, 5));
        String nivel = String.valueOf(tblUsuarios.getModel().getValueAt(linhaModelo, 6));
        String status = String.valueOf(tblUsuarios.getModel().getValueAt(linhaModelo, 7));

        try {
            UsuarioEditar tela = context.getBean(UsuarioEditar.class, nome, cpf, telefone, login, perfil, nivel, status, id, this);
            tela.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            tela.setLocationRelativeTo(null);
            tela.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir edição: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
