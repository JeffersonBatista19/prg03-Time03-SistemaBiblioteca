package br.com.ifba.biblioteca.sugestaoaquisicao.view;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.sugestaoaquisicao.controller.SugestaoAquisicaoController;
import br.com.ifba.biblioteca.sugestaoaquisicao.entity.SugestaoAquisicao;
import br.com.ifba.biblioteca.sugestaoaquisicao.entity.StatusSugestao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;

@Component
public class TelaSugestaoAquisicaoEditar extends JFrame {

    @Autowired
    private SugestaoAquisicaoController controller;

    @Autowired
    @Lazy
    private TelaSugestaoAquisicaoListar telaListar;

    private SugestaoAquisicao sugestaoAtual;
    private JLabel lblDados;
    private JComboBox<StatusSugestao> cbStatus;
    private JButton btnSalvar, btnVoltar;

    public TelaSugestaoAquisicaoEditar() {
        setTitle("Analisar Sugestão");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel pnlInfo = new JPanel(new GridLayout(3, 1));
        pnlInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lblDados = new JLabel();
        cbStatus = new JComboBox<>(StatusSugestao.values());

        pnlInfo.add(new JLabel("Alterar Status da Sugestão:"));
        pnlInfo.add(lblDados);
        pnlInfo.add(cbStatus);

        JPanel pnlBotoes = new JPanel();
        btnSalvar = new JButton("Salvar Alteração");
        btnVoltar = new JButton("Voltar");
        pnlBotoes.add(btnVoltar);
        pnlBotoes.add(btnSalvar);

        add(pnlInfo, BorderLayout.CENTER);
        add(pnlBotoes, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> {
            setVisible(false);
            telaListar.carregarTabela();
            telaListar.setVisible(true);
        });

        btnSalvar.addActionListener(e -> atualizar());
    }

    public void preencherDados(SugestaoAquisicao s) {
        this.sugestaoAtual = s;
        lblDados.setText("Sugestão: " + s.getTituloSugerido() + " (" + s.getAutorSugerido() + ")");
        cbStatus.setSelectedItem(s.getStatus());
    }

    private void atualizar() {
        try {
            sugestaoAtual.setStatus((StatusSugestao) cbStatus.getSelectedItem());
            controller.update(sugestaoAtual);
            JOptionPane.showMessageDialog(this, "Status atualizado com sucesso!");
            btnVoltar.doClick();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }
}
