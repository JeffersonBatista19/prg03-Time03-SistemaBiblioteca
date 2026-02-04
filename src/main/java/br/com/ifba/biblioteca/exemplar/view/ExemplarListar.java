package br.com.ifba.biblioteca.exemplar.view;

import br.com.ifba.biblioteca.exemplar.controller.ExemplarController;
import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;

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

@Component
@Scope("prototype")
public class ExemplarListar extends javax.swing.JFrame {
    
    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private ExemplarController exemplarController;

    private JTable tblExemplares;
    private JSpinner spnTombamento;
    private JButton btnAdicionar, btnEditar, btnExcluir, btnAtualizar, btnVoltar;

    public ExemplarListar() {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        
        // Carrega dados automaticamente quando a janela é mostrada
        addWindowListener(new java.awt.event.WindowAdapter() {
            private boolean primeiraVez = true;
            @Override
            public void windowOpened(java.awt.event.WindowEvent evt) {
                if (primeiraVez) {
                    carregarExemplares();
                    primeiraVez = false;
                }
            }
        });
    }
    
    private void initComponents() {
        setTitle("Gestão de Exemplares");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- PAINEL TOPO ---
        JPanel pnlTopo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnlTopo.setBackground(Color.WHITE);
        pnlTopo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        pnlTopo.add(new JLabel("Tombamento:"));
        spnTombamento = new JSpinner(new javax.swing.SpinnerNumberModel());
        spnTombamento.setPreferredSize(new java.awt.Dimension(80, 25));
        spnTombamento.addChangeListener(e -> filtrarPorTombamento());
        pnlTopo.add(spnTombamento);
        
        btnAdicionar = new JButton("Adicionar");
        estilizarBotao(btnAdicionar, new Color(46, 204, 113));
        pnlTopo.add(btnAdicionar);
        
        btnEditar = new JButton("Editar");
        estilizarBotao(btnEditar, new Color(52, 152, 219));
        pnlTopo.add(btnEditar);
        
        btnExcluir = new JButton("Excluir");
        estilizarBotao(btnExcluir, new Color(231, 76, 60));
        pnlTopo.add(btnExcluir);
        
        btnAtualizar = new JButton("Atualizar");
        estilizarBotao(btnAtualizar, new Color(241, 196, 15));
        btnAtualizar.setForeground(Color.DARK_GRAY);
        pnlTopo.add(btnAtualizar);
        
        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(99, 110, 114));
        pnlTopo.add(btnVoltar);
        
        add(pnlTopo, BorderLayout.NORTH);

        // --- TABELA ---
        String[] colunas = {"Tombamento", "Estado", "Localização", "Status", "ID", "ISBN"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return false;
             }
        };
        tblExemplares = new JTable(modelo);
        tblExemplares.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblExemplares.setRowHeight(25);
        tblExemplares.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // Ocultar coluna ID (index 4)
        tblExemplares.getColumnModel().getColumn(4).setMinWidth(0);
        tblExemplares.getColumnModel().getColumn(4).setMaxWidth(0);
        tblExemplares.getColumnModel().getColumn(4).setWidth(0);
        
        add(new JScrollPane(tblExemplares), BorderLayout.CENTER);

        // LISTENERS
        btnAdicionar.addActionListener(e -> btnAdicionarActionPerformed(e));
        btnEditar.addActionListener(e -> btnEditarActionPerformed(e));
        btnExcluir.addActionListener(e -> btnExcluirActionPerformed(e));
        btnAtualizar.addActionListener(e -> carregarExemplares());
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

    // Método get para compatibilidade se algo externo precisar
    public javax.swing.JTable getjTable1() {
        return tblExemplares;
    }

    private void btnAdicionarActionPerformed(ActionEvent evt) {
        ExemplarAdicionar telaAdicionar = context.getBean(ExemplarAdicionar.class);
        telaAdicionar.setExemplarListar(this);
        // Não criar novo JFrame, pois ExemplarAdicionar já é um JFrame
        telaAdicionar.setVisible(true);
    }

    private void btnExcluirActionPerformed(ActionEvent evt) {
        int linhaSelecionada = tblExemplares.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um exemplar para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Long idExemplar = (Long) tblExemplares.getValueAt(linhaSelecionada, 4);
        try {
            Exemplar ex = exemplarController.findById(idExemplar);
            int opcao = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir o exemplar?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                exemplarController.delete(ex);
                carregarExemplares();
                JOptionPane.showMessageDialog(this, "Exemplar excluído com sucesso!");
            }
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
             JOptionPane.showMessageDialog(this, "Não é possível excluir este exemplar pois ele está vinculado a Empréstimos ou Reservas.", "Erro de Vínculo", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            String msg = e.getMessage();
            if (msg != null && (msg.contains("ConstraintViolation") || msg.contains("Foreign Key"))) {
                 JOptionPane.showMessageDialog(this, "Não é possível excluir este exemplar pois ele está vinculado a outro registro (Emprestimo/Reserva).", "Erro de Vínculo", JOptionPane.ERROR_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
            }
        }
    }

    private void btnEditarActionPerformed(ActionEvent evt) {
        int linhaSelectionada = tblExemplares.getSelectedRow();
        if (linhaSelectionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um exemplar para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int tombamento = (Integer) tblExemplares.getValueAt(linhaSelectionada, 0);
        String conservacao = tblExemplares.getValueAt(linhaSelectionada, 1).toString();
        String local = tblExemplares.getValueAt(linhaSelectionada, 2).toString();
        String status = tblExemplares.getValueAt(linhaSelectionada, 3).toString();
        Long idExemplar = (Long) tblExemplares.getValueAt(linhaSelectionada, 4);
        String isbn = tblExemplares.getValueAt(linhaSelectionada, 5).toString();

        ExemplarEditar editar = context.getBean(ExemplarEditar.class, tombamento, conservacao, local, status, isbn, idExemplar, this);
        editar.setVisible(true);
    }
    
    public void carregarExemplares() {
         try {
            List<Exemplar> exemplares = exemplarController.findAll();
            DefaultTableModel model = (DefaultTableModel) tblExemplares.getModel();
            model.setRowCount(0);
            for (Exemplar ex : exemplares) {
                model.addRow(new Object[]{
                    ex.getNumeroTombamento(),
                    ex.getConservacao(),
                    ex.getLocalizacaoFisica(),
                    ex.getStatus(),
                    ex.getId(),
                    ex.getIsbnLivro()      
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar exemplares: " + e.getMessage());
        }
    }

    private void filtrarPorTombamento() {
        int valor = (Integer) spnTombamento.getValue();
        if (valor == 0) {
            carregarExemplares();
            return;
        }
        DefaultTableModel model = (DefaultTableModel) tblExemplares.getModel();
        model.setRowCount(0);
        try {
            for (Exemplar ex : exemplarController.findAll()) {
                if (ex.getNumeroTombamento() == valor) {
                    model.addRow(new Object[]{
                        ex.getNumeroTombamento(),
                        ex.getConservacao(),
                        ex.getLocalizacaoFisica(),
                        ex.getStatus(),
                        ex.getId(),
                        ex.getIsbnLivro()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao filtrar: " + e.getMessage());
        }
    }
}
