package br.com.ifba.biblioteca.reserva.view;

import br.com.ifba.biblioteca.reserva.entity.Reserva;
import br.com.ifba.biblioteca.reserva.entity.StatusReserva;
import br.com.ifba.biblioteca.reserva.service.ReservaService;
import java.util.List;
import javax.swing.JOptionPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author misae
 */

@Component
@Scope("prototype")
public class ReservaListar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReservaListar.class.getName());

    @Autowired
    private ReservaService reservaService;
    
    @Autowired
    private ApplicationContext context;

    private List<Reserva> reservasExibidas;

    // Componentes de Tela
    private JTable tblReservas;
    private JComboBox<String> cbStatus;
    private JTextField txtClienteId;
    private JTextField txtExemplarId;
    private JButton btnAdicionar, btnAtender, btnCancelar, btnFiltrar, btnAtualizar, btnVoltar;

    public ReservaListar() {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // Tela Cheia
        setLocationRelativeTo(null);
        preencherComboStatus();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- PAINEL TOPO (Filtros e Ações) ---
        JPanel pnlTopo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnlTopo.setBackground(Color.WHITE);
        pnlTopo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        // Filtros
        pnlTopo.add(new JLabel("Status:"));
        cbStatus = new JComboBox<>();
        pnlTopo.add(cbStatus);

        pnlTopo.add(new JLabel("Cliente ID:"));
        txtClienteId = new JTextField(5);
        pnlTopo.add(txtClienteId);

        pnlTopo.add(new JLabel("Exemplar ID:"));
        txtExemplarId = new JTextField(5);
        pnlTopo.add(txtExemplarId);

        btnFiltrar = new JButton("Filtrar");
        estilizarBotao(btnFiltrar, new Color(52, 152, 219)); // Azul
        pnlTopo.add(btnFiltrar);

        // Ações
        btnAdicionar = new JButton("Nova Reserva");
        estilizarBotao(btnAdicionar, new Color(46, 204, 113)); // Verde
        pnlTopo.add(btnAdicionar);

        btnAtender = new JButton("Atender");
        estilizarBotao(btnAtender, new Color(155, 89, 182)); // Roxo
        pnlTopo.add(btnAtender);

        btnCancelar = new JButton("Cancelar");
        estilizarBotao(btnCancelar, new Color(231, 76, 60)); // Vermelho
        pnlTopo.add(btnCancelar);
        
        btnAtualizar = new JButton("Atualizar Lista");
        estilizarBotao(btnAtualizar, new Color(241, 196, 15)); // Amarelo
        btnAtualizar.setForeground(Color.DARK_GRAY);
        pnlTopo.add(btnAtualizar);

        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(99, 110, 114)); // Cinza Escuro
        pnlTopo.add(btnVoltar);

        add(pnlTopo, BorderLayout.NORTH);

        // --- TABELA ---
        tblReservas = new JTable();
        tblReservas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblReservas.setRowHeight(25);
        tblReservas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JScrollPane scrollPane = new JScrollPane(tblReservas);
        add(scrollPane, BorderLayout.CENTER);

        // Eventos
        btnFiltrar.addActionListener(evt -> btnFiltrarActionPerformed(evt));
        btnAdicionar.addActionListener(evt -> btnAdicionarActionPerformed(evt));
        btnAtender.addActionListener(evt -> btnAtenderActionPerformed(evt));
        btnCancelar.addActionListener(evt -> btnCancelarActionPerformed(evt));
        btnAtualizar.addActionListener(evt -> btnAtualizarActionPerformed(evt));
        btnVoltar.addActionListener(evt -> dispose());
    }

    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }
    
    public void carregarTabela(List<Reserva> reservas) {
        String[] colunas = {"ID", "Cliente", "Exemplar", "Data", "Status"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Reserva r : reservas) {
            modelo.addRow(new Object[]{
                r.getId(), // Adicionei ID para facilitar identificação visual
                r.getCliente().getNomeCompleto(),
                r.getExemplar().getNumeroTombamento(),
                r.getDataReserva(),
                r.getStatus()
            });
        }

        tblReservas.setModel(modelo); 
        this.reservasExibidas = reservas;
    }
    
    public void carregarDados() {
        carregarTabela(reservaService.findAll());
    }
    
    private void preencherComboStatus() {
        cbStatus.removeAllItems();
        cbStatus.addItem(""); // Opção vazia
        for (StatusReserva status : StatusReserva.values()) {
            cbStatus.addItem(status.name());
        }
    }

    private void btnAdicionarActionPerformed(ActionEvent evt) {
        try {
            ReservaAdicionar tela = context.getBean(ReservaAdicionar.class);
            tela.setTelaPai(this);
            tela.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir tela: " + e.getMessage());
        }
    }

    private void btnAtenderActionPerformed(ActionEvent evt) {
        int linha = tblReservas.getSelectedRow();
        if (linha != -1) {
            // CUIDADO: Se tiver filtro aplicado a linha da tabela pode nao bater com o indice da lista
            // Melhor pegar pelo ID da tabela
            Long idReserva = (Long) tblReservas.getModel().getValueAt(linha, 0); // Coluna 0 é o ID agora
            
            // Buscar na lista exibida (ou buscar do banco se preferir segurança)
            Reserva reserva = reservasExibidas.stream().filter(r -> r.getId().equals(idReserva)).findFirst().orElse(null);
            
            if (reserva != null) {
                ReservaAtender tela = new ReservaAtender(reserva, reservaService);
                tela.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        carregarDados();
                    }
                });
                tela.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma reserva para atender.");
        }
    }

    private void btnFiltrarActionPerformed(ActionEvent evt) {
        List<Reserva> filtradas = reservaService.findAll();

        String statusSelecionado = (String) cbStatus.getSelectedItem();
        if (statusSelecionado != null && !statusSelecionado.isBlank()) {
            filtradas = filtradas.stream()
                    .filter(r -> r.getStatus().name().equals(statusSelecionado))
                    .toList();
        }

        if (!txtClienteId.getText().isBlank()) {
            try {
                long clienteId = Long.parseLong(txtClienteId.getText());
                filtradas = filtradas.stream()
                        .filter(r -> r.getCliente().getId() == clienteId)
                        .toList();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Cliente ID deve ser numérico!");
                return;
            }
        }

        if (!txtExemplarId.getText().isBlank()) {
            try {
                long exemplarId = Long.parseLong(txtExemplarId.getText());
                filtradas = filtradas.stream()
                        .filter(r -> r.getExemplar().getNumeroTombamento() == exemplarId)
                        .toList();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Exemplar ID deve ser numérico!");
                return;
            }
        }

        carregarTabela(filtradas);
    }

    private void btnAtualizarActionPerformed(ActionEvent evt) {
        carregarDados();
        JOptionPane.showMessageDialog(this, "Tabela atualizada!");
    }

    private void btnCancelarActionPerformed(ActionEvent evt) {
       int linha = tblReservas.getSelectedRow();
       if (linha != -1) {
            Long idReserva = (Long) tblReservas.getModel().getValueAt(linha, 0);
            Reserva reserva = reservasExibidas.stream().filter(r -> r.getId().equals(idReserva)).findFirst().orElse(null);

            if (reserva != null) {
                if (reserva.getStatus() == StatusReserva.CANCELADA) {
                    JOptionPane.showMessageDialog(this, "Esta reserva já está cancelada!", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int opcao = JOptionPane.showConfirmDialog(this, "Deseja realmente cancelar esta reserva?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (opcao == JOptionPane.YES_OPTION) {
                    reservaService.delete(reserva);
                    carregarDados();
                    JOptionPane.showMessageDialog(this, "Reserva cancelada!");
                }
            }
       } else {
            JOptionPane.showMessageDialog(this, "Selecione uma reserva para cancelar.", "Aviso", JOptionPane.WARNING_MESSAGE);
       }
    }
}
