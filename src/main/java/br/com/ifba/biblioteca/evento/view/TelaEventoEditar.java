package br.com.ifba.biblioteca.evento.view;

/**
 *
 * @author guilhermeAmedrado
 */

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
    
    
    // Relationship placeholders

    
    
    public TelaEventoEditar() {
        setTitle("Editar Evento");
        setSize(550, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 10, 10));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtTitulo = new JTextField();
        txtDescricao = new JTextField();
        cbLocal = new JComboBox<>();
        txtVagas = new JTextField();
        txtDataHorario = new JTextField();

        pnlForm.add(new JLabel("Título:")); pnlForm.add(txtTitulo);
        pnlForm.add(new JLabel("Descrição:")); pnlForm.add(txtDescricao);
        pnlForm.add(new JLabel("Local:")); pnlForm.add(cbLocal);
        pnlForm.add(new JLabel("Vagas:")); pnlForm.add(txtVagas);
        pnlForm.add(new JLabel("Data:")); pnlForm.add(txtDataHorario);

        JButton btnSalvar = new JButton("Atualizar Dados");
        btnSalvar.addActionListener(e -> atualizar());
        
        add(pnlForm, BorderLayout.CENTER);
        add(btnSalvar, BorderLayout.SOUTH);
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
        txtDataHorario.setText(e.getDataHorario().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
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
        telaListar.carregarTabela();
        telaListar.setVisible(true);
        this.dispose();
    }

    private void preencherComboLocais() {
        cbLocal.removeAllItems();
        for (Localfake l : eventoController.findAllLocais()) {
            cbLocal.addItem(l);
        }
    }
}