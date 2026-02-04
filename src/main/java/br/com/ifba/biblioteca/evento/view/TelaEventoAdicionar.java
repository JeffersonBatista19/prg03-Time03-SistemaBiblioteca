package br.com.ifba.biblioteca.evento.view;

import br.com.ifba.biblioteca.evento.controller.EventoController;
import br.com.ifba.biblioteca.evento.entity.Evento;
import br.com.ifba.biblioteca.evento.entity.Localfake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@org.springframework.stereotype.Component
public class TelaEventoAdicionar extends JFrame {

    @Autowired
    private EventoController eventoController;

    @Autowired
    @Lazy 
    private TelaEventosListar telaListar;

    // Componentes de Texto
    private JTextField txtTitulo, txtDescricao, txtVagas, txtDataHorario;
    private JComboBox<Localfake> cbLocal; 
    private JButton btnSalvar, btnVoltar;

    public TelaEventoAdicionar() {
        setTitle("Cadastrar Novo Evento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizado
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- FORMULÁRIO CENTRAL ---
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(Color.WHITE);
        painelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Titulo
        JLabel lblHeader = new JLabel("NOVO EVENTO");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painelForm.add(lblHeader, gbc);
        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos
        adicionarCampo(painelForm, gbc, 1, "Título:", txtTitulo = new JTextField(20));
        adicionarCampo(painelForm, gbc, 2, "Descrição:", txtDescricao = new JTextField(20));
        adicionarCampo(painelForm, gbc, 3, "Vagas:", txtVagas = new JTextField(10));
        adicionarCampo(painelForm, gbc, 4, "Data (dd/MM/yyyy HH:mm):", txtDataHorario = new JTextField(15));
        
        gbc.gridx = 0; gbc.gridy = 5;
        painelForm.add(new JLabel("Local:"), gbc);
        cbLocal = new JComboBox<>();
        gbc.gridx = 1; gbc.gridy = 5;
        painelForm.add(cbLocal, gbc);

        // Container Central
        JPanel pnlCenterContainer = new JPanel(new GridBagLayout());
        pnlCenterContainer.setBackground(new Color(240, 242, 245));
        pnlCenterContainer.add(painelForm);
        add(pnlCenterContainer, BorderLayout.CENTER);

        // --- BOTOES SUL ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        painelBotoes.setBackground(Color.WHITE);
        painelBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(99, 110, 114));
        
        btnSalvar = new JButton("Salvar Evento");
        estilizarBotao(btnSalvar, new Color(46, 204, 113));

        painelBotoes.add(btnVoltar);
        painelBotoes.add(btnSalvar);
        add(painelBotoes, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> voltarParaListagem()); 
        btnSalvar.addActionListener(e -> salvarEvento()); 
    }
    
    private void adicionarCampo(JPanel pnl, GridBagConstraints gbc, int row, String label, Component comp) {
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

    private void salvarEvento() {
        try {
            String strData = txtDataHorario.getText().trim();
            if (strData.length() == 10) {
                strData += " 00:00";
            }
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime data = LocalDateTime.parse(strData, df);

            Evento e = new Evento();
            e.setTitulo(txtTitulo.getText());
            e.setDescricao(txtDescricao.getText());
            e.setLimiteVagas(Integer.parseInt(txtVagas.getText()));
            e.setDataHorario(data);
            e.setLocalEntity((Localfake) cbLocal.getSelectedItem());

            eventoController.save(e);
            JOptionPane.showMessageDialog(this, "Evento salvo com sucesso!");
            voltarParaListagem();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltarParaListagem() {
        if (telaListar != null) {
             telaListar.carregarTabela();
             telaListar.setVisible(true);
        }
        this.dispose();
    }
    
    public void preencherLocais() {
        cbLocal.removeAllItems(); 
        for (Localfake l : eventoController.findAllLocais()) {
            cbLocal.addItem(l); 
        }
    }
}