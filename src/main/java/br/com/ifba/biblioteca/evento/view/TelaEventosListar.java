package br.com.ifba.biblioteca.evento.view;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.evento.controller.EventoController;
import br.com.ifba.biblioteca.evento.entity.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class TelaEventosListar extends JFrame {

    @Autowired
    private EventoController eventoController;
    @Autowired
    private TelaEventoEditar telaEditar;
    @Autowired
    private TelaEventoAdicionar telaAdicionar;

    private JTable tblEventos;
    private DefaultTableModel modelo;
    private JTextField txtBusca;

    public TelaEventosListar() {
        setTitle("Sistema da Biblioteca - Eventos");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout(10, 10));

        // TOPO
        JPanel pnlTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBusca = new JTextField(30);
        JButton btnPesquisar = new JButton("Pesquisar");
        pnlTopo.add(new JLabel("Título:"));
        pnlTopo.add(txtBusca);
        pnlTopo.add(btnPesquisar);
        add(pnlTopo, BorderLayout.NORTH);

        // CENTRO
        String[] colunas = {"ID", "Título", "Data", "Local", "Inscritos / Vagas", "Status"};
        modelo = new DefaultTableModel(colunas, 0);
        tblEventos = new JTable(modelo);
        add(new JScrollPane(tblEventos), BorderLayout.CENTER);

        // RODAPÉ
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnNovo = new JButton("Novo Evento");
        JButton btnEditar = new JButton("Editar Evento");
        JButton btnCancelar = new JButton("Cancelar Evento");
        pnlBotoes.add(btnNovo); pnlBotoes.add(btnEditar); pnlBotoes.add(btnCancelar);
        add(pnlBotoes, BorderLayout.SOUTH);

        // EVENTOS
        btnPesquisar.addActionListener(e -> pesquisar());
        btnNovo.addActionListener(e -> { 
            telaAdicionar.preencherLocais();
            telaAdicionar.setVisible(true); 
            this.dispose(); 
        });
        btnEditar.addActionListener(e -> editar());
        btnCancelar.addActionListener(e -> cancelar());
    }

    public void carregarTabela() {
        modelo.setNumRows(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Evento e : eventoController.findAll()) {
            // Formata o texto das vagas (ex: 10/50)
            String vagasFormatadas = e.getVagasPreenchidas() + " / " + e.getLimiteVagas();
            // Verifica se o local existe para evitar erros
            String localNome = (e.getLocalEntity() != null) ? e.getLocalEntity().getNome() : "Não definido";
            // Adiciona uma linha na tabela com os dados do evento
            modelo.addRow(new Object[]{e.getId(), e.getTitulo(), e.getDataHorario().format(formatter), 
                                      localNome, vagasFormatadas, e.getStatus()});
        }
    }

    private void pesquisar() {
        String termo = txtBusca.getText();
        modelo.setNumRows(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
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
                // Tenta cancelar o evento selecionado
                eventoController.cancelar(id); 
                JOptionPane.showMessageDialog(this, "Evento cancelado!");
                carregarTabela(); // Atualiza a tabela para mostrar o novo status
            } catch (RuntimeException ex) {
                // Captura as mensagens de erro do Service (RN)
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}