package br.com.ifba.biblioteca.evento.view;

import br.com.ifba.biblioteca.evento.controller.EventoController;
import br.com.ifba.biblioteca.evento.entity.Evento;
import br.com.ifba.biblioteca.evento.entity.Localfake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TelaEventoEditar extends JFrame {

    @Autowired private EventoController eventoController;
    @Autowired @Lazy private TelaEventosListar telaListar;

    private Evento eventoAtual;
    private JTextField txtTitulo, txtDescricao, txtVagas, txtDataHorario;
    private JComboBox<Localfake> cbLocal;
    private JButton btnSalvar, btnVoltar;

    public TelaEventoEditar() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Editar Evento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- PAINEL FORMULÁRIO ---
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Título
        JLabel lblTitulo = new JLabel("EDITAR EVENTO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlForm.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos
        adicionarCampo(pnlForm, gbc, 1, "Título:", txtTitulo = new JTextField(30));
        adicionarCampo(pnlForm, gbc, 2, "Descrição:", txtDescricao = new JTextField(30));
        
        cbLocal = new JComboBox<>();
        adicionarCampo(pnlForm, gbc, 3, "Local:", cbLocal);
        
        adicionarCampo(pnlForm, gbc, 4, "Vagas:", txtVagas = new JTextField(10));
        adicionarCampo(pnlForm, gbc, 5, "Data (dd/MM/yyyy HH:mm):", txtDataHorario = new JTextField(15));

        // Container Central
        JPanel pnlCenterContainer = new JPanel(new GridBagLayout());
        pnlCenterContainer.setBackground(new Color(240, 242, 245));
        pnlCenterContainer.add(pnlForm);
        add(pnlCenterContainer, BorderLayout.CENTER);

        // --- BOTÕES ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlBotoes.setBackground(Color.WHITE);
        pnlBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(99, 110, 114));
        btnVoltar.addActionListener(e -> voltar());

        btnSalvar = new JButton("Salvar Alterações");
        estilizarBotao(btnSalvar, new Color(46, 204, 113));
        btnSalvar.addActionListener(e -> atualizar());

        pnlBotoes.add(btnVoltar);
        pnlBotoes.add(btnSalvar);
        add(pnlBotoes, BorderLayout.SOUTH);
    }
    
    private void adicionarCampo(JPanel pnl, GridBagConstraints gbc, int row, String label, java.awt.Component comp) {
        gbc.gridx = 0; gbc.gridy = row;
        pnl.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.gridy = row;
        pnl.add(comp, gbc);
    }

    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    public void preencherDados(Evento e) {
        preencherComboLocais();
        this.eventoAtual = e;
        txtTitulo.setText(e.getTitulo());
        txtDescricao.setText(e.getDescricao());
        
        // Selecionar o local correto no combo
        if (e.getLocalEntity() != null) {
            for (int i = 0; i < cbLocal.getItemCount(); i++) {
                if (cbLocal.getItemAt(i).getId().equals(e.getLocalEntity().getId())) {
                    cbLocal.setSelectedIndex(i);
                    break;
                }
            }
        }

        
        // Preenche o campo de vagas e data com os dados atuais do evento
        txtVagas.setText(String.valueOf(e.getLimiteVagas()));
        if (e.getDataHorario() != null) {
            txtDataHorario.setText(e.getDataHorario().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        }
    }

    private void atualizar() {
        try {
            String strData = txtDataHorario.getText().trim();
            if (strData.length() == 10) {
                strData += " 00:00";
            }
            
            eventoAtual.setTitulo(txtTitulo.getText());
            eventoAtual.setDescricao(txtDescricao.getText());
            eventoAtual.setLocalEntity((Localfake) cbLocal.getSelectedItem());
            eventoAtual.setLimiteVagas(Integer.parseInt(txtVagas.getText()));
            eventoAtual.setDataHorario(LocalDateTime.parse(strData, 
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

            // Tenta atualizar os dados através do controlador
            eventoController.update(eventoAtual);
            JOptionPane.showMessageDialog(this, "Evento atualizado!");
            voltar(); // Retorna para a tela de listagem
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
    }

    private void voltar() {
        if (telaListar != null) {
            telaListar.carregarTabela();
            telaListar.setVisible(true); // Garante que a tela pai reapareça ou atualize
        }
        this.dispose();
    }

    private void preencherComboLocais() {
        cbLocal.removeAllItems();
        for (Localfake l : eventoController.findAllLocais()) {
            cbLocal.addItem(l);
        }
    }
}