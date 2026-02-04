package br.com.ifba.biblioteca.exemplar.view;

import br.com.ifba.biblioteca.exemplar.controller.ExemplarController;
import br.com.ifba.biblioteca.exemplar.entity.EstadoConservacao;
import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import br.com.ifba.biblioteca.exemplar.entity.StatusExemplar;
import javax.swing.SwingUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

@Component
@Scope("prototype")
public class ExemplarAdicionar extends javax.swing.JFrame { // Mudado para JFrame para manter padrao 'Maximizado'
    
    @Autowired
    private ExemplarController exemplarController;

    @Autowired
    private ExemplarListar exemplarListar;
    
    @Autowired
    private ApplicationContext context;

    private JTextField txtIsbn, txtLocal;
    private JSpinner spnTombamento;
    private JComboBox<String> cmbConservacao, cmbStatus;
    private JButton btnLivro, btnCriar, btnCancelar;

    public ExemplarAdicionar() {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Adicionar Exemplar");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

        // Header
        JLabel lblTitulo = new JLabel("ADICIONANDO EXEMPLAR");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlForm.add(lblTitulo, gbc);
        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Livro ISBN
        gbc.gridx = 0; gbc.gridy = 1;
        pnlForm.add(new JLabel("Livro ISBN:"), gbc);
        txtIsbn = new JTextField(15);
        txtIsbn.setEditable(false);
        gbc.gridx = 1; gbc.gridy = 1;
        pnlForm.add(txtIsbn, gbc);
        btnLivro = new JButton("Buscar Livro");
        estilizarBotao(btnLivro, new Color(52, 152, 219));
        gbc.gridx = 2; gbc.gridy = 1;
        pnlForm.add(btnLivro, gbc);

        // Tombamento
        gbc.gridx = 0; gbc.gridy = 2;
        pnlForm.add(new JLabel("Tombamento:"), gbc);
        spnTombamento = new JSpinner(new javax.swing.SpinnerNumberModel());
        gbc.gridx = 1; gbc.gridy = 2;
        pnlForm.add(spnTombamento, gbc);
        
        // Conservação
        gbc.gridx = 0; gbc.gridy = 3;
        pnlForm.add(new JLabel("Conservação:"), gbc);
        cmbConservacao = new JComboBox<>(new String[] { "NOVO", "BOM", "REGULAR", "RUIM" });
        gbc.gridx = 1; gbc.gridy = 3;
        pnlForm.add(cmbConservacao, gbc);
        
        // Localização
        gbc.gridx = 0; gbc.gridy = 4;
        pnlForm.add(new JLabel("Localização:"), gbc);
        txtLocal = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 4;
        pnlForm.add(txtLocal, gbc);
        
        // Status
        gbc.gridx = 0; gbc.gridy = 5;
        pnlForm.add(new JLabel("Status:"), gbc);
        cmbStatus = new JComboBox<>(new String[] { "DISPONIVEL", "EMPRESTADO", "RESERVADO", "INATIVO" });
        gbc.gridx = 1; gbc.gridy = 5;
        pnlForm.add(cmbStatus, gbc);

        // Container Central
        JPanel pnlCenterContainer = new JPanel(new GridBagLayout());
        pnlCenterContainer.setBackground(new Color(240, 242, 245));
        pnlCenterContainer.add(pnlForm);
        add(pnlCenterContainer, BorderLayout.CENTER);

        // --- BOTOES SUL ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlBotoes.setBackground(Color.WHITE);
        pnlBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        btnCancelar = new JButton("Voltar");
        estilizarBotao(btnCancelar, new Color(99, 110, 114));
        
        btnCriar = new JButton("Criar");
        estilizarBotao(btnCriar, new Color(46, 204, 113));

        pnlBotoes.add(btnCancelar);
        pnlBotoes.add(btnCriar);
        add(pnlBotoes, BorderLayout.SOUTH);
        
        // Listeners
        btnLivro.addActionListener(e -> buscarLivro());
        btnCancelar.addActionListener(e -> dispose());
        btnCriar.addActionListener(e -> criarExemplar());
    }

    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }
    
    public void setExemplarListar(ExemplarListar exemplarListar) {
        this.exemplarListar = exemplarListar;
    }
    
    public void setIsbnLivro(String isbn) {
        txtIsbn.setText(isbn);
    }
    
    private void buscarLivro() {
        BuscarLivro tela = context.getBean(BuscarLivro.class);
        tela.setTelaExemplar(this); // Nota: Isso exige mudança em BuscarLivro para aceitar ExemplarAdicionar como JFrame agora
        tela.carregarLivros();
        tela.setVisible(true);
    }
    
    private void criarExemplar() {
         try {
            int tombamento = (Integer) spnTombamento.getValue();
            String conservacaoStr = (String) cmbConservacao.getSelectedItem();
            String local = txtLocal.getText();
            String statusStr = (String) cmbStatus.getSelectedItem();
            String isbn = txtIsbn.getText();

            if (isbn == null || isbn.trim().isEmpty()) throw new RuntimeException("Selecione um livro antes de criar o exemplar.");
            if (local == null || local.trim().isEmpty()) throw new RuntimeException("Localização física é obrigatória.");
            if (conservacaoStr == null || conservacaoStr.trim().isEmpty()) throw new RuntimeException("Estado de conservação é obrigatório.");
            if (statusStr == null || statusStr.trim().isEmpty()) throw new RuntimeException("Status do exemplar é obrigatório.");
            
            Exemplar exemplar = new Exemplar();
            exemplar.setNumeroTombamento(tombamento);
            exemplar.setConservacao(EstadoConservacao.valueOf(conservacaoStr));
            exemplar.setLocalizacaoFisica(local);
            exemplar.setStatus(StatusExemplar.valueOf(statusStr));
            exemplar.setIsbnLivro(isbn); 

            exemplarController.save(exemplar);

            if (exemplarListar != null) {
                exemplarListar.carregarExemplares();
            }

            javax.swing.JOptionPane.showMessageDialog(this, "Exemplar criado com sucesso!");
            this.dispose();

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao criar exemplar: " + e.getMessage());
        }
    }
}
