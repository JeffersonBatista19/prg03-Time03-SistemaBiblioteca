package br.com.ifba.biblioteca.evento.view;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.evento.controller.EventoController;
import br.com.ifba.biblioteca.evento.entity.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

@Component
public class TelaEventosListar extends javax.swing.JFrame {

    @Autowired
    private EventoController eventoController;
    @Autowired
    private TelaEventoEditar telaEditar;
    @Autowired
    private TelaEventoAdicionar telaAdicionar;

    private JTable tblEventos;
    private DefaultTableModel modelo;
    private JTextField txtBusca;
    private JButton btnPesquisar, btnNovo, btnEditar, btnCancelar, btnVoltar;

    public TelaEventosListar() {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); 
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            carregarTabela();
        }
        super.setVisible(b);
    }
    
    private void initComponents() {
        setTitle("Sistema da Biblioteca - Eventos");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- PAINEL TOPO ---
        JPanel pnlTopo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnlTopo.setBackground(Color.WHITE);
        pnlTopo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        pnlTopo.add(new JLabel("Título:"));
        txtBusca = new JTextField(20);
        txtBusca.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlTopo.add(txtBusca);
        
        btnPesquisar = new JButton("Pesquisar");
        estilizarBotao(btnPesquisar, new Color(52, 152, 219)); // Azul
        pnlTopo.add(btnPesquisar);
        
        btnNovo = new JButton("Novo Evento");
        estilizarBotao(btnNovo, new Color(46, 204, 113)); // Verde
        pnlTopo.add(btnNovo);
        
        btnEditar = new JButton("Editar Evento");
        estilizarBotao(btnEditar, new Color(243, 156, 18)); // Laranja
        pnlTopo.add(btnEditar);
        
        btnCancelar = new JButton("Cancelar Evento");
        estilizarBotao(btnCancelar, new Color(231, 76, 60)); // Vermelho
        pnlTopo.add(btnCancelar);
        
        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(99, 110, 114)); // Cinza
        pnlTopo.add(btnVoltar);
        
        add(pnlTopo, BorderLayout.NORTH);

        // --- TABELA ---
        String[] colunas = {"ID", "Título", "Data", "Local", "Inscritos / Vagas", "Status"};
        modelo = new DefaultTableModel(colunas, 0) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return false;
             }
        };
        tblEventos = new JTable(modelo);
        tblEventos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblEventos.setRowHeight(25);
        tblEventos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        add(new JScrollPane(tblEventos), BorderLayout.CENTER);

        // EVENTOS
        btnPesquisar.addActionListener(e -> pesquisar());
        btnNovo.addActionListener(e -> { 
            telaAdicionar.preencherLocais();
            telaAdicionar.setLocationRelativeTo(null);
            telaAdicionar.setVisible(true); 
            this.dispose(); 
        });
        btnEditar.addActionListener(e -> editar());
        btnCancelar.addActionListener(e -> cancelar());
        btnVoltar.addActionListener(e -> dispose());
        
        // Listener para buscar ao apertar Enter
        txtBusca.addActionListener(e -> pesquisar());
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
        try {
            modelo.setNumRows(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            for (Evento e : eventoController.findAll()) {
                String vagasFormatadas = e.getVagasPreenchidas() + " / " + e.getLimiteVagas();
                String localNome = (e.getLocalEntity() != null) ? e.getLocalEntity().getNome() : "Não definido";
                modelo.addRow(new Object[]{e.getId(), e.getTitulo(), e.getDataHorario().format(formatter), 
                                          localNome, vagasFormatadas, e.getStatus()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + ex.getMessage());
        }
    }

    private void pesquisar() {
        String termo = txtBusca.getText();
        modelo.setNumRows(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if (termo.isEmpty()) {
            carregarTabela();
            return;
        }
        for (Evento e : eventoController.findByTitulo(termo)) {
            String vagasFormatadas = e.getVagasPreenchidas() + " / " + e.getLimiteVagas();
            String localNome = (e.getLocalEntity() != null) ? e.getLocalEntity().getNome() : "Não definido";
            modelo.addRow(new Object[]{e.getId(), e.getTitulo(), e.getDataHorario().format(formatter), 
                                      localNome, vagasFormatadas, e.getStatus()});
        }
    }

    private void editar() {
        int linha = tblEventos.getSelectedRow();
        if (linha == -1) { JOptionPane.showMessageDialog(this, "Selecione um evento."); return; }
        Long id = (Long) tblEventos.getValueAt(linha, 0);
        telaEditar.preencherDados(eventoController.findById(id));
        telaEditar.setVisible(true);
        this.dispose();
    }

    private void cancelar() {
        int linha = tblEventos.getSelectedRow();
        if (linha == -1) { JOptionPane.showMessageDialog(this, "Selecione um evento."); return; }

        Long id = (Long) tblEventos.getValueAt(linha, 0);
        int resp = JOptionPane.showConfirmDialog(this, "Confirmar cancelamento?", "Confirmação", JOptionPane.YES_NO_OPTION);

        if (resp == JOptionPane.YES_OPTION) {
            try {
                eventoController.cancelar(id); 
                JOptionPane.showMessageDialog(this, "Evento cancelado!");
                carregarTabela(); 
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}