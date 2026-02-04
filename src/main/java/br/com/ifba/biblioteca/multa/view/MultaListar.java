package br.com.ifba.biblioteca.multa.view;

import br.com.ifba.biblioteca.multa.controller.MultaIController;
import br.com.ifba.biblioteca.multa.entity.Multa;
import br.com.ifba.biblioteca.multa.entity.StatusMulta;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;

@Component
@Scope("prototype")
public class MultaListar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MultaListar.class.getName());
    
    @Autowired
    private MultaIController multaController;
    
    @Autowired
    private ApplicationContext context;

    private DefaultTableModel tableModel;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Componentes Visuais
    private JTable jTable1;
    private JTextField txtAluno, txtLivro;
    private JComboBox<String> cmbStatus;
    private JSpinner spnDataInicio, spnDataFim;
    private JFormattedTextField ftdValorMin, ftdValorMax;
    private JButton btnBuscar, btnLimpar, btnVisualizar, btnEditar, btnAtualizar, btnVoltar;

    public MultaListar() {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        configurarCombos();
        configurarTabela();
        
        // Carrega dados automaticamente quando a janela é mostrada
        addWindowListener(new java.awt.event.WindowAdapter() {
            private boolean primeiraVez = true;
            @Override
            public void windowOpened(java.awt.event.WindowEvent evt) {
                if (primeiraVez) {
                    carregarMultas();
                    primeiraVez = false;
                }
            }
        });
    }
    
    // Método chamado para exibir
    public void carregarTela() {
        configurarCombos();
        configurarTabela();
        carregarMultas();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Gestão de Multas");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- PAINEL FILTRO (NORTE) ---
        // Vamos usar um painel maior com GridBag ou FlowLayout quebrando linhas, mas
        // para manter padrão e simplicidade, usaremos dois painéis: Topo (Ações) e um painel de Filtros colapsável ou fixo.
        // Dado que multa tem muito filtro, vamos fazer um Painel Norte com Grid de 2 linhas.
        
        JPanel pnlNorte = new JPanel(new BorderLayout());
        
        // 1. Painel de Ações Rápidas (Topo)
        JPanel pnlAcoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnlAcoes.setBackground(Color.WHITE);
        pnlAcoes.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        
        btnVisualizar = new JButton("Visualizar");
        estilizarBotao(btnVisualizar, new Color(52, 152, 219)); // Azul
        pnlAcoes.add(btnVisualizar);
        
        btnEditar = new JButton("Editar");
        estilizarBotao(btnEditar, new Color(243, 156, 18)); // Laranja
        pnlAcoes.add(btnEditar);
        
        btnAtualizar = new JButton("Atualizar");
        estilizarBotao(btnAtualizar, new Color(241, 196, 15)); // Amarelo
        btnAtualizar.setForeground(Color.DARK_GRAY);
        pnlAcoes.add(btnAtualizar);
        
        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(99, 110, 114)); // Cinza
        pnlAcoes.add(btnVoltar);
        
        pnlNorte.add(pnlAcoes, BorderLayout.NORTH);

        // 2. Painel de Filtros (Centro do Norte)
        JPanel pnlFiltros = new JPanel();
        pnlFiltros.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlFiltros.setBorder(BorderFactory.createTitledBorder("Filtros de Pesquisa"));
        pnlFiltros.setBackground(new Color(250, 250, 250));

        pnlFiltros.add(new JLabel("Aluno:"));
        txtAluno = new JTextField(10);
        pnlFiltros.add(txtAluno);

        pnlFiltros.add(new JLabel("Livro:"));
        txtLivro = new JTextField(10);
        pnlFiltros.add(txtLivro);

        pnlFiltros.add(new JLabel("Status:"));
        cmbStatus = new JComboBox<>();
        pnlFiltros.add(cmbStatus);
        
        pnlFiltros.add(new JLabel("Data Ini:"));
        spnDataInicio = new JSpinner(new javax.swing.SpinnerDateModel());
        spnDataInicio.setEditor(new JSpinner.DateEditor(spnDataInicio, "dd/MM/yyyy"));
        pnlFiltros.add(spnDataInicio);
        
        pnlFiltros.add(new JLabel("Data Fim:"));
        spnDataFim = new JSpinner(new javax.swing.SpinnerDateModel());
        spnDataFim.setEditor(new JSpinner.DateEditor(spnDataFim, "dd/MM/yyyy"));
        pnlFiltros.add(spnDataFim);
        
        btnBuscar = new JButton("Filtrar");
        estilizarBotao(btnBuscar, new Color(46, 204, 113)); // Verde
        pnlFiltros.add(btnBuscar);
        
        btnLimpar = new JButton("Limpar");
        estilizarBotao(btnLimpar, new Color(127, 140, 141)); // Cinza Claro
        pnlFiltros.add(btnLimpar);

        pnlNorte.add(pnlFiltros, BorderLayout.CENTER);
        
        add(pnlNorte, BorderLayout.NORTH);

        // --- TABELA (CENTRO) ---
        tableModel = new DefaultTableModel( new Object[]{"ID", "Aluno", "Livro", "Empréstimo", "Dias Atraso", "Valor", "Data", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1 = new JTable(tableModel);
        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTable1.setRowHeight(25);
        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        add(new JScrollPane(jTable1), BorderLayout.CENTER);

        // Listeners
        btnVisualizar.addActionListener(e -> btnVisualizarActionPerformed(e));
        btnEditar.addActionListener(e -> btnEditarActionPerformed(e));
        btnAtualizar.addActionListener(e -> carregarMultas());
        btnVoltar.addActionListener(e -> dispose());
        btnBuscar.addActionListener(e -> btnBuscarActionPerformed(e));
        btnLimpar.addActionListener(e -> btnLimparActionPerformed(e));
    }
    
    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    public void setMultaController(MultaIController multaController) {
        this.multaController = multaController;
    }

    private void configurarCombos() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("TODOS"); 
        for (StatusMulta status : StatusMulta.values()) {
            model.addElement(status.name());
        }
        cmbStatus.setModel(model);
        cmbStatus.setSelectedIndex(0);
    }
    
    private void configurarTabela() {
        // Já configurado no InitComponents, mas mantendo caso seja chamado externamente pra limpar
        if (tableModel != null) tableModel.setRowCount(0);
    }
   
    private void carregarMultas() {
        if (tableModel == null) return;
        tableModel.setRowCount(0);
        List<Multa> todas = multaController.findAll();
        if (todas == null) return;

        for (Multa m : todas) {
            if (m.getEmprestimo() == null || m.getEmprestimo().getCliente() == null || m.getEmprestimo().getExemplar() == null) continue;

            tableModel.addRow(new Object[]{
                m.getId(),
                m.getEmprestimo().getCliente().getNomeCompleto(),
                m.getEmprestimo().getExemplar().getIsbnLivro(),
                m.getEmprestimo().getId(),
                m.getDiasAtraso(),
                m.getValorTotal(),
                m.getDataGeracao().format(dtf),
                m.getStatus()
            });
        }
    }
    
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            tableModel.setRowCount(0);
            String alunoFiltro = txtAluno.getText().trim();
            String livroFiltro = txtLivro.getText().trim();
            String statusSelecionado = (String) cmbStatus.getSelectedItem();
            StatusMulta statusFiltro = null;

            if (statusSelecionado != null && !statusSelecionado.equals("TODOS")) {
                statusFiltro = StatusMulta.valueOf(statusSelecionado);
            }

            List<Multa> todas = multaController.findAll();

            for (Multa m : todas) {
                boolean passa = true;
                String nomeAluno = m.getEmprestimo().getCliente().getNomeCompleto();
                String isbnLivro = m.getEmprestimo().getExemplar().getIsbnLivro();

                if (!alunoFiltro.isEmpty() && !nomeAluno.toLowerCase().contains(alunoFiltro.toLowerCase())) {
                    passa = false;
                }
                if (!livroFiltro.isEmpty() && !isbnLivro.toLowerCase().contains(livroFiltro.toLowerCase())) {
                    passa = false;
                }
                if (statusFiltro != null && m.getStatus() != statusFiltro) {
                    passa = false;
                }

                if (passa) {
                    tableModel.addRow(new Object[]{
                        m.getId(),
                        m.getEmprestimo().getCliente().getNomeCompleto(),
                        m.getEmprestimo().getExemplar().getIsbnLivro(),
                        m.getEmprestimo().getId(),
                        m.getDiasAtraso(),
                        m.getValorTotal(),
                        m.getDataGeracao().format(dtf),
                        m.getStatus()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao filtrar multas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {
        txtAluno.setText("");
        txtLivro.setText("");
        cmbStatus.setSelectedIndex(0);
        spnDataInicio.setValue(new java.util.Date());
        spnDataFim.setValue(new java.util.Date());
        carregarMultas();
    }

    private void btnVisualizarActionPerformed(java.awt.event.ActionEvent evt) {
        int linha = jTable1.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma multa para visualizar!");
            return;
        }
        Long id = (Long) jTable1.getValueAt(linha, 0);
        MultaVisualizar tela = context.getBean(MultaVisualizar.class);
        tela.setMultaId(id);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {
        int linha = jTable1.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma multa para editar!");
            return;
        }
        Long id = (Long) jTable1.getValueAt(linha, 0);
        MultaEditar tela = context.getBean(MultaEditar.class);
        tela.setMultaId(id);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }
}
