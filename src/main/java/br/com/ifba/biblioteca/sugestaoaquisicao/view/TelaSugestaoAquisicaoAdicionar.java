package br.com.ifba.biblioteca.sugestaoaquisicao.view;

/**
 *
 * @author guilhermeAmedrado
 */

import br.com.ifba.biblioteca.sugestaoaquisicao.controller.SugestaoAquisicaoController;
import br.com.ifba.biblioteca.sugestaoaquisicao.entity.SugestaoAquisicao;
import br.com.ifba.biblioteca.usuario.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;

@Component
public class TelaSugestaoAquisicaoAdicionar extends JFrame {

    @Autowired
    private SugestaoAquisicaoController controller;

    @Autowired
    private br.com.ifba.biblioteca.usuario.controller.UsuarioIController usuarioController;

    @Autowired
    @Lazy
    private TelaSugestaoAquisicaoListar telaListar;

    private JTextField txtTitulo, txtAutor, txtEditora;
    private JTextArea txtJustificativa;
    private JButton btnEnviar, btnVoltar;

    public TelaSugestaoAquisicaoAdicionar() {
        setTitle("Nova Sugestão de Aquisição");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel pnlForm = new JPanel(new GridLayout(5, 1, 10, 10));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtEditora = new JTextField();
        txtJustificativa = new JTextArea(5, 20);

        pnlForm.add(new JLabel("Título do Livro (Obrigatório):"));
        pnlForm.add(txtTitulo);
        pnlForm.add(new JLabel("Autor do Livro (Obrigatório):"));
        pnlForm.add(txtAutor);
        pnlForm.add(new JLabel("Editora:"));
        pnlForm.add(txtEditora);
        
        JPanel pnlJust = new JPanel(new BorderLayout());
        pnlJust.add(new JLabel("Justificativa:"), BorderLayout.NORTH);
        pnlJust.add(new JScrollPane(txtJustificativa), BorderLayout.CENTER);

        JPanel pnlBotoes = new JPanel(new FlowLayout());
        btnEnviar = new JButton("Enviar Sugestão");
        btnVoltar = new JButton("Voltar");
        pnlBotoes.add(btnVoltar);
        pnlBotoes.add(btnEnviar);

        add(pnlForm, BorderLayout.NORTH);
        add(pnlJust, BorderLayout.CENTER);
        add(pnlBotoes, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> {
            setVisible(false);
            telaListar.carregarTabela();
            telaListar.setVisible(true);
        });

        btnEnviar.addActionListener(e -> salvar());
    }

    private void salvar() {
        try {
            SugestaoAquisicao s = new SugestaoAquisicao();
            s.setTituloSugerido(txtTitulo.getText());
            s.setAutorSugerido(txtAutor.getText());
            s.setEditoraSugerida(txtEditora.getText());
            s.setJustificativa(txtJustificativa.getText());

            // Busca primeiro usuário do banco para simular login; se vazio, salva anônimo para não travar teste
            java.util.List<Usuario> usuarios = usuarioController.findAll();
            if (usuarios != null && !usuarios.isEmpty()) {
                // Vincula o primeiro usuário encontrado (Simulando o usuário logado)
                s.setUsuario(usuarios.get(0));
            } else {
                System.out.println("Aviso: Nenhum usuário encontrado para vincular à sugestão. Salvando sem usuário.");
            }

            controller.save(s); // Envia para o controlador salvar no banco
            JOptionPane.showMessageDialog(this, "Sugestão enviada para análise com sucesso.");
            limparCampos();
            btnVoltar.doClick(); // Volta automaticamente para a lista após salvar
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtEditora.setText("");
        txtJustificativa.setText("");
    }
}
