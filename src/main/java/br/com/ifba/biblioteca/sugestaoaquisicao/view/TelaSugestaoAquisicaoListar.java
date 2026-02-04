package br.com.ifba.biblioteca.sugestaoaquisicao.view;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.sugestaoaquisicao.controller.SugestaoAquisicaoController;
import br.com.ifba.biblioteca.sugestaoaquisicao.entity.SugestaoAquisicao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

@Component
public class TelaSugestaoAquisicaoListar extends JFrame {

    @Autowired
    private SugestaoAquisicaoController controller;
    @Autowired
    private TelaSugestaoAquisicaoAdicionar telaAdicionar;
    @Autowired
    private TelaSugestaoAquisicaoEditar telaEditar;

    private JTable tblSugestoes;
    private DefaultTableModel modelo;
    private JButton btnNovo, btnEditar, btnExcluir, btnVoltar;

    public TelaSugestaoAquisicaoListar() {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Relatório de Sugestões de Aquisição");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- PAINEL TOPO ---
        JPanel pnlTopo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnlTopo.setBackground(Color.WHITE);
        pnlTopo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        
        btnNovo = new JButton("Nova Sugestão");
        estilizarBotao(btnNovo, new Color(46, 204, 113));
        pnlTopo.add(btnNovo);
        
        btnEditar = new JButton("Analisar Sugestão");
        estilizarBotao(btnEditar, new Color(52, 152, 219));
        pnlTopo.add(btnEditar);
        
        btnExcluir = new JButton("Excluir");
        estilizarBotao(btnExcluir, new Color(231, 76, 60));
        pnlTopo.add(btnExcluir);
        
        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(99, 110, 114));
        pnlTopo.add(btnVoltar);
        
        add(pnlTopo, BorderLayout.NORTH);

        // --- TABELA ---
        String[] colunas = {"ID", "Usuário", "Título", "Autor", "Editora", "Data", "Status"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override
             public boolean isCellEditable(int row, int column) {
                 return false;
             }
        };
        tblSugestoes = new JTable(modelo);
        tblSugestoes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblSugestoes.setRowHeight(25);
        tblSugestoes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        add(new JScrollPane(tblSugestoes), BorderLayout.CENTER);

        // LISTENERS
        btnNovo.addActionListener(e -> {
            try {
                // Força a reinicialização da tela se necessário
                // Ou apenas garante que está visível
                if (telaAdicionar != null) {
                    telaAdicionar.setLocationRelativeTo(this); // Centraliza em relação à lista
                    telaAdicionar.setVisible(true);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Erro Interno: Tela de Adicionar não foi injetada.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao abrir tela: " + ex.getMessage());
            }
        });

        btnEditar.addActionListener(e -> editar());
        btnExcluir.addActionListener(e -> excluir());
        btnVoltar.addActionListener(e -> {
            this.dispose();
            // A tela principal deve ser re-focada ou reaberta se necessário, 
            // mas como é dispose(), ela apenas fecha esta janela.
        });
    }

    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    public void carregarTabela() {
        modelo.setNumRows(0);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (SugestaoAquisicao s : controller.findAll()) {
            try {
                modelo.addRow(new Object[]{
                    s.getId(),
                    s.getUsuario() != null ? s.getUsuario().getNomeCompleto() : "Anônimo",
                    s.getTituloSugerido(),
                    s.getAutorSugerido(),
                    s.getEditoraSugerida(),
                    s.getDataSugestao().format(df),
                    s.getStatus()
                });
            } catch (Exception ex) {
                System.err.println("Erro ao carregar sugestão ID " + s.getId() + ": " + ex.getMessage());
            }
        }
    }

    private void editar() {
        int linha = tblSugestoes.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma sugestão.");
            return;
        }
        Long id = (Long) tblSugestoes.getValueAt(linha, 0);
        telaEditar.preencherDados(controller.findById(id));
        telaEditar.setVisible(true);
        this.setVisible(false);
    }

    private void excluir() {
        int linha = tblSugestoes.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma sugestão.");
            return;
        }
        Long id = (Long) tblSugestoes.getValueAt(linha, 0);
        int resp = JOptionPane.showConfirmDialog(this, "Deseja excluir esta sugestão?");
        if (resp == JOptionPane.YES_OPTION) {
            controller.delete(id);
            carregarTabela();
        }
    }
}
