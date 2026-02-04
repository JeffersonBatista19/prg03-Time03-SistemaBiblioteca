package br.com.ifba.biblioteca.exemplar.view;

import br.com.ifba.biblioteca.exemplar.controller.ExemplarController;
import br.com.ifba.biblioteca.exemplar.entity.EstadoConservacao;
import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import br.com.ifba.biblioteca.exemplar.entity.StatusExemplar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ExemplarEditar extends JFrame {
    
    @Autowired
    private ExemplarController exemplarController;

    private ExemplarListar exemplarListar; 
    private Long idExemplar; 
    private String isbn; 
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ExemplarEditar.class.getName());

    // Componentes
    private JTextField txtIsbn, txtLocal;
    private JSpinner spnTombamento;
    private JComboBox<String> cmbConservacao;
    private JComboBox<String> cmbStatus;
    private JButton btnSalvar, btnCancelar;

    public ExemplarEditar(int tombamento, String conservacao, String local, String status,String isbn,
                      Long idExemplar, ExemplarListar telaListar) {
        this.exemplarListar = telaListar;
        this.idExemplar = idExemplar;
        this.isbn = isbn;

        initComponents();

        txtIsbn.setText(isbn);  
        txtIsbn.setText(isbn);  
        txtIsbn.setEditable(true); // Permitir correção de ISBN errado (Data Fix)
        
        spnTombamento.setValue(tombamento);
        cmbConservacao.setSelectedItem(conservacao);
        txtLocal.setText(local);
        cmbStatus.setSelectedItem(status);
        
        setLocationRelativeTo(null); 
    }

    private void initComponents() {
        setTitle("Editar Exemplar");
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
        JLabel lblTitulo = new JLabel("EDITAR EXEMPLAR");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlForm.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos
        adicionarCampo(pnlForm, gbc, 1, "ISBN do Livro:", txtIsbn = new JTextField(20));
        
        spnTombamento = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        adicionarCampo(pnlForm, gbc, 2, "Número Tombamento:", spnTombamento);
        
        cmbConservacao = new JComboBox<>(new String[] { "NOVO", "BOM", "REGULAR", "RUIM" });
        adicionarCampo(pnlForm, gbc, 3, "Estado Conservação:", cmbConservacao);
        
        adicionarCampo(pnlForm, gbc, 4, "Localização Física:", txtLocal = new JTextField(20));
        
        cmbStatus = new JComboBox<>(new String[] { "DISPONIVEL", "EMPRESTADO", "RESERVADO", "INATIVO" });
        adicionarCampo(pnlForm, gbc, 5, "Status:", cmbStatus);

        // Container Central
        JPanel pnlCenterContainer = new JPanel(new GridBagLayout());
        pnlCenterContainer.setBackground(new Color(240, 242, 245));
        pnlCenterContainer.add(pnlForm);
        add(pnlCenterContainer, BorderLayout.CENTER);

        // --- BOTÕES ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlBotoes.setBackground(Color.WHITE);
        pnlBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        btnCancelar = new JButton("Cancelar");
        estilizarBotao(btnCancelar, new Color(99, 110, 114));
        btnCancelar.addActionListener(e -> dispose());
        
        btnSalvar = new JButton("Salvar Alterações");
        estilizarBotao(btnSalvar, new Color(46, 204, 113));
        btnSalvar.addActionListener(e -> salvarAlteracoes());

        pnlBotoes.add(btnCancelar);
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
    
    // Método para atualizar os campos se necessário externo
     public void preencherCampos(int tombamento, String conservacao, String local, String status,
                            Long idExemplar, ExemplarListar listar, int linhaSelecionada) {
        spnTombamento.setValue(tombamento);
        cmbConservacao.setSelectedItem(conservacao);
        txtLocal.setText(local);
        cmbStatus.setSelectedItem(status);

        this.exemplarListar = listar;
        this.idExemplar = idExemplar;
    }

    private void salvarAlteracoes() {
        try {
            int novoTombamento = (Integer) spnTombamento.getValue();
            
            String conservacaoStr = (String) cmbConservacao.getSelectedItem();
            EstadoConservacao novaConservacao = EstadoConservacao.valueOf(conservacaoStr);

            String novaLocalizacao = txtLocal.getText();
            StatusExemplar novoStatus = StatusExemplar.valueOf((String) cmbStatus.getSelectedItem());

            if (novaLocalizacao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Exemplar ex = exemplarController.findById(idExemplar);

            if (novoTombamento != ex.getNumeroTombamento() &&
                exemplarController.existsByNumeroTombamento(novoTombamento)) {
                JOptionPane.showMessageDialog(this, "Já existe um exemplar com este número de tombamento!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ex.setNumeroTombamento(novoTombamento);
            ex.setConservacao(novaConservacao);
            ex.setLocalizacaoFisica(novaLocalizacao);
            ex.setStatus(novoStatus);
            
            //Lê o ISBN do campo de texto (que agora é editável)
            String novoIsbn = txtIsbn.getText();
            if (novoIsbn == null || novoIsbn.trim().isEmpty()) {
                 JOptionPane.showMessageDialog(this, "O ISBN não pode ficar vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
                 return;
            }
            ex.setIsbnLivro(novoIsbn); 

            exemplarController.update(ex);
            exemplarListar.carregarExemplares(); // Refresh list

            JOptionPane.showMessageDialog(null, "Exemplar atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
