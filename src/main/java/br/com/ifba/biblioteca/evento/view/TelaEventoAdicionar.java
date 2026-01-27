package br.com.ifba.biblioteca.evento.view;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.evento.controller.EventoController;
import br.com.ifba.biblioteca.evento.entity.Evento;
import br.com.ifba.biblioteca.evento.entity.Localfake;
import br.com.ifba.biblioteca.evento.entity.Localfake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TelaEventoAdicionar extends JFrame {

    @Autowired
    private EventoController eventoController;

    @Autowired
    @Lazy 
    private TelaEventosListar telaListar;

    // Componentes de Texto
    private JTextField txtTitulo, txtDescricao, txtVagas, txtDataHorario;
    
    
    
    // --- LÓGICA DE TRANSIÇÃO DO LOCAL ---
    private JComboBox<Localfake> cbLocal; 
    
    
    
    
    private JButton btnSalvar, btnVoltar;

    public TelaEventoAdicionar() {
        setTitle("Cadastrar Novo Evento");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        JPanel painelFundo = new JPanel();
        painelFundo.setLayout(new BoxLayout(painelFundo, BoxLayout.Y_AXIS));
        painelFundo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel painelForm = new JPanel(new GridLayout(5, 2, 10, 15));

        txtTitulo = new JTextField();
        txtDescricao = new JTextField();
        txtVagas = new JTextField();
        txtDataHorario = new JTextField();

        // Configuração do Local (Versão atual: JComboBox)
        cbLocal = new JComboBox<>();

        painelForm.add(new JLabel("Título:"));
        painelForm.add(txtTitulo);
        
        painelForm.add(new JLabel("Descrição:"));
        painelForm.add(txtDescricao);

        painelForm.add(new JLabel("Local:"));
        painelForm.add(cbLocal); 
        
        painelForm.add(new JLabel("Vagas:"));
        painelForm.add(txtVagas);

        painelForm.add(new JLabel("Data (dd/MM/yyyy HH:mm):"));
        painelForm.add(txtDataHorario);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnVoltar = new JButton("Voltar");
        btnSalvar = new JButton("Salvar Evento");

        painelBotoes.add(btnVoltar);
        painelBotoes.add(btnSalvar);

        painelFundo.add(painelForm);
        painelFundo.add(painelBotoes);
        add(painelFundo);

        btnVoltar.addActionListener(e -> voltarParaListagem()); // Aciona a volta para a tela principal
        btnSalvar.addActionListener(e -> salvarEvento()); // Aciona a lógica de salvar o evento
    }

    private void salvarEvento() {
        try {
            String strData = txtDataHorario.getText().trim();
            // Se o usuário digitar apenas a data (10 caracteres), adiciona o horário padrão
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
            
            

            //VERSÃO ATUAL (Localfake)
            e.setLocalEntity((Localfake) cbLocal.getSelectedItem());

            
            
            
            // Tenta salvar o evento usando o controlador
            eventoController.save(e);
            JOptionPane.showMessageDialog(this, "Evento salvo com sucesso!");
            voltarParaListagem(); // Retorna para a tela de listagem se tudo der certo

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltarParaListagem() {
        telaListar.carregarTabela();
        telaListar.setVisible(true);
        this.dispose();
    }
    
    public void preencherLocais() {
        cbLocal.removeAllItems(); // Limpa a lista atual de locais
        for (Localfake l : eventoController.findAllLocais()) {
            cbLocal.addItem(l); // Adiciona cada local vindo do banco na caixa de seleção
        }
    }
}