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

    public TelaSugestaoAquisicaoListar() {
        setTitle("Relatório de Sugestões de Aquisição");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Usuário", "Título", "Autor", "Editora", "Data", "Status"};
        modelo = new DefaultTableModel(colunas, 0);
        tblSugestoes = new JTable(modelo);
        add(new JScrollPane(tblSugestoes), BorderLayout.CENTER);

        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnNovo = new JButton("Nova Sugestão");
        JButton btnEditar = new JButton("Analisar Sugestão");
        JButton btnExcluir = new JButton("Excluir");

        pnlBotoes.add(btnNovo);
        pnlBotoes.add(btnEditar);
        pnlBotoes.add(btnExcluir);
        add(pnlBotoes, BorderLayout.SOUTH);

        btnNovo.addActionListener(e -> {
            telaAdicionar.setVisible(true);
            this.setVisible(false);
        });

        btnEditar.addActionListener(e -> editar());
        btnExcluir.addActionListener(e -> excluir());
    }

    public void carregarTabela() {
        modelo.setNumRows(0); // Limpa as linhas atuais da tabela
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (SugestaoAquisicao s : controller.findAll()) {
            try {
                // Adiciona uma nova linha com os detalhes da sugestão
                modelo.addRow(new Object[]{
                    s.getId(),
                    s.getUsuario() != null ? s.getUsuario().getNomeCompleto() : "Usuário Desconhecido",
                    s.getTituloSugerido(),
                    s.getAutorSugerido(),
                    s.getEditoraSugerida(),
                    s.getDataSugestao().format(df), // Formata a data para legibilidade
                    s.getStatus()
                });
            } catch (Exception ex) {
                // Pular registros corrompidos ou com relacionamentos inexistentes (como usuario_id faltante)
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
