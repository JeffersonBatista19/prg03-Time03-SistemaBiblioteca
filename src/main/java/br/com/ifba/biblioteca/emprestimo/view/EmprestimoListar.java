package br.com.ifba.biblioteca.emprestimo.view;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.emprestimo.controller.EmprestimoIController;
import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import br.com.ifba.biblioteca.multa.view.MultaGerar;
import java.awt.event.KeyEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class EmprestimoListar extends javax.swing.JFrame {
   
    private TableRowSorter<DefaultTableModel> sorter;

    @Autowired
    private EmprestimoIController emprestimoController;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private br.com.ifba.biblioteca.livro.service.LivroService livroService;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EmprestimoListar.class.getName());
    
    // VARIÁVEIS DE COMPONENTES DE TELA
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblEmprestimos;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnMultas;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtBuscar;
    
    public EmprestimoListar() {
        initComponents();
        setTitle("Gestão de Empréstimos");
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        configurarTabela();
    }
    
    private void configurarTabela() {
        String[] colunas = {"ID", "Livro", "Cliente", "Exemplar", "Data Emp.", "Devolução", "Status"};
        
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblEmprestimos.setModel(modelo);
        sorter = new javax.swing.table.TableRowSorter<>(modelo);
        tblEmprestimos.setRowSorter(sorter);
    }
    
    public void carregarDados() {
        try {
            javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tblEmprestimos.getModel();
            modelo.setRowCount(0); 

            java.util.List<br.com.ifba.biblioteca.emprestimo.entity.Emprestimo> lista = emprestimoController.findAll();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (br.com.ifba.biblioteca.emprestimo.entity.Emprestimo emp : lista) {
                // Busca o título do livro usando o ISBN do exemplar
                String tituloLivro = "Não encontrado";
                if (emp.getExemplar() != null && emp.getExemplar().getIsbnLivro() != null) {
                    try {
                        String isbnBusca = emp.getExemplar().getIsbnLivro();
                        br.com.ifba.biblioteca.livro.entity.Livro livro = livroService.findByIsbn(isbnBusca);
                        if (livro != null) {
                            tituloLivro = livro.getTitulo();
                        } else {
                            tituloLivro = "Não enc. (" + isbnBusca + ")";
                        }
                    } catch (Exception ex) {
                        tituloLivro = "Erro busca";
                    }
                }

                modelo.addRow(new Object[]{
                    emp.getId(),
                    tituloLivro,
                    emp.getCliente().getNomeCompleto(),
                    emp.getExemplar().getId(),
                    emp.getDataEmprestimo() != null ? emp.getDataEmprestimo().format(formatter) : "",
                    emp.getDataPrevistaDevolucao() != null ? emp.getDataPrevistaDevolucao().format(formatter) : "",
                    emp.getStatus()
                });
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage());
        }
    }
    
    // COMPONENTES A MÃO (MANUTENÇÃO MAIS FÁCIL e VISUAL PROFISSIONAL)
    private void initComponents() {
        // Layout Principal: BorderLayout para expandir a tabela no centro
        setLayout(new java.awt.BorderLayout());
        
        // Configuração Janela
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // NÃO FECHA O PROGRAMA TODO
        getContentPane().setBackground(new java.awt.Color(240, 242, 245));

        // --- PAINEL TOPO (Filtros e Botões) ---
        javax.swing.JPanel pnlTopo = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 15));
        pnlTopo.setBackground(new java.awt.Color(255, 255, 255));
        pnlTopo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(200, 200, 200)));

        jLabel2 = new javax.swing.JLabel("Buscar por ID/Nome:");
        jLabel2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        
        txtBuscar = new javax.swing.JTextField(20);
        txtBuscar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        
        btnAdicionar = new javax.swing.JButton("Novo Empréstimo");
        estilizarBotao(btnAdicionar, new java.awt.Color(46, 204, 113));
        
        btnEditar = new javax.swing.JButton("Editar");
        estilizarBotao(btnEditar, new java.awt.Color(52, 152, 219));
        
        btnExcluir = new javax.swing.JButton("Devolver/Excluir");
        estilizarBotao(btnExcluir, new java.awt.Color(231, 76, 60));
        
        btnMultas = new javax.swing.JButton("Ver Multas");
        estilizarBotao(btnMultas, new java.awt.Color(241, 196, 15));
        btnMultas.setForeground(java.awt.Color.DARK_GRAY); // Multa amarelinho texto escuro
        
        javax.swing.JButton btnVoltar = new javax.swing.JButton("Voltar");
        estilizarBotao(btnVoltar, new java.awt.Color(99, 110, 114)); // Cinza escuro
        
        pnlTopo.add(jLabel2);
        pnlTopo.add(txtBuscar);
        pnlTopo.add(btnAdicionar);
        pnlTopo.add(btnEditar);
        pnlTopo.add(btnExcluir);
        pnlTopo.add(btnMultas);
        pnlTopo.add(btnVoltar); // Adiciona ao painel

        add(pnlTopo, java.awt.BorderLayout.NORTH);

        // --- PAINEL CENTRO (Tabela) ---
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmprestimos = new javax.swing.JTable();
        tblEmprestimos.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        tblEmprestimos.setRowHeight(25);
        tblEmprestimos.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        
        jScrollPane1.setViewportView(tblEmprestimos);
        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        // Listeners
        btnAdicionar.addActionListener(evt -> btnAdicionarActionPerformed(evt));
        btnExcluir.addActionListener(evt -> btnExcluirActionPerformed(evt));
        btnEditar.addActionListener(evt -> btnEditarActionPerformed(evt));
        btnMultas.addActionListener(evt -> btnMultasActionPerformed(evt));
        btnVoltar.addActionListener(evt -> dispose()); // Fecha a janela atual
        
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        
        pack();
    }
    
    // Método utilitário para deixar os botões bonitos (Flat Design)
    private void estilizarBotao(javax.swing.JButton btn, java.awt.Color cor) {
        btn.setBackground(cor);
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {
      try {  
            EmprestimoAdicionar telaAdicionar = context.getBean(EmprestimoAdicionar.class);
            telaAdicionar.setEmprestimoListar(this);
            telaAdicionar.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            telaAdicionar.setLocationRelativeTo(null);
            telaAdicionar.setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao abrir tela: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {
        int linhaSelecionada = tblEmprestimos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um empréstimo para excluir/devolver!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int linhaModelo = tblEmprestimos.convertRowIndexToModel(linhaSelecionada);
        Long id = (Long) tblEmprestimos.getModel().getValueAt(linhaModelo, 0);

        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja remover este empréstimo?\nIsso devolverá o livro ao estoque.",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                Emprestimo remover = new Emprestimo();
                remover.setId(id);
                emprestimoController.delete(remover);
                carregarDados();
                JOptionPane.showMessageDialog(this, "Empréstimo removido com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
            }
        }
    }

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {
        int linhaSelecionada = tblEmprestimos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um empréstimo para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int linhaModelo = tblEmprestimos.convertRowIndexToModel(linhaSelecionada);
        Long id = (Long) tblEmprestimos.getModel().getValueAt(linhaModelo, 0);

        try {
            Emprestimo emp = emprestimoController.findById(id);
            EmprestimoEditar editar = new EmprestimoEditar(emprestimoController, this, emp);
            editar.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar dados: " + e.getMessage());
        }
    }

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {
        String texto = txtBuscar.getText();
        if (texto.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            try {
                sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + texto));
            } catch (Exception e) {
            }
        }
    }

    private void btnMultasActionPerformed(java.awt.event.ActionEvent evt) {
        int linha = tblEmprestimos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um empréstimo.");
            return;
        }

        int linhaModelo = tblEmprestimos.convertRowIndexToModel(linha);
        Long id = (Long) tblEmprestimos.getModel().getValueAt(linhaModelo, 0);

        try {
            Emprestimo emp = emprestimoController.findById(id);
            if (emp == null) {
                JOptionPane.showMessageDialog(this, "Empréstimo não encontrado.");
                return;
            }

            if (emp.getMulta() != null) {
                // Se já existe multa, abre a tela de visualização
                try {
                    br.com.ifba.biblioteca.multa.view.MultaVisualizar telaVisualizar = 
                        context.getBean(br.com.ifba.biblioteca.multa.view.MultaVisualizar.class);
                    telaVisualizar.setMultaId(emp.getMulta().getId());
                    telaVisualizar.setLocationRelativeTo(null);
                    telaVisualizar.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao abrir visualização da multa: " + ex.getMessage());
                }
                return;
            }

            MultaGerar tela = context.getBean(MultaGerar.class);
            tela.setEmprestimo(emp);
            tela.setLocationRelativeTo(null);
            tela.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir multa: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new EmprestimoListar().setVisible(true);
        });
    }
}
