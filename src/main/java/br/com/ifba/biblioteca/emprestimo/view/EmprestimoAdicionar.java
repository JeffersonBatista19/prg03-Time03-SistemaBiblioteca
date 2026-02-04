package br.com.ifba.biblioteca.emprestimo.view;

import br.com.ifba.biblioteca.emprestimo.controller.EmprestimoIController;
import br.com.ifba.biblioteca.emprestimo.entity.Emprestimo;
import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import br.com.ifba.biblioteca.cliente.entity.Cliente;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.time.format.DateTimeFormatter;
import br.com.ifba.biblioteca.reserva.view.BuscarCliente;
import org.springframework.context.ApplicationContext;
import java.util.List;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@Component
public class EmprestimoAdicionar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EmprestimoAdicionar.class.getName());

    @Autowired
    private EmprestimoIController emprestimoController;

    @Autowired
    private br.com.ifba.biblioteca.livro.service.LivroService livroService;

    @Autowired
    private ApplicationContext context;

    private void abrirBuscaCliente() {
        try {
            BuscarCliente telaBusca = context.getBean(BuscarCliente.class);
            telaBusca.setCallback(cliente -> {
                 if (cliente != null) {
                     txtIdCliente.setText(cliente.getId().toString());
                     txtIdCliente.setText(cliente.getId().toString());
                     txtNomeCliente.setText(cliente.getNomeCompleto()); // Preenche o nome visível
                 }
            });
            telaBusca.setVisible(true);
            telaBusca.setLocationRelativeTo(this);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao abrir busca: " + e.getMessage());
        }
    }

    private EmprestimoListar emprestimoListar;
    private JTextField txtIdCliente;
    private JTextField txtNomeCliente;
    private JTextField txtIdExemplar;
    private JComboBox<Exemplar> cbExemplar;
    private JButton btnSalvar;
    private JButton btnVoltar;
    
    // Cache simples para evitar consultas repetidas no banco ao renderizar a combo
    private java.util.Map<String, String> isbnTituloCache = new java.util.HashMap<>();

    public void setEmprestimoListar(EmprestimoListar emprestimoListar) {
        this.emprestimoListar = emprestimoListar;
    }
    
    public EmprestimoAdicionar() {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }
    
    @PostConstruct
    public void init() {
        configurarComboBoxes();
        preencherDados();
    }
    
    private void initComponents() {
        setTitle("Novo Empréstimo");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // Padronizado para DISPOSE
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- PAINEL CENTRAL (FORMULÁRIO) ---
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Título
        JLabel lblTitulo = new JLabel("ADICIONANDO EMPRÉSTIMOS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlForm.add(lblTitulo, gbc);
        
        // Reset anchor
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Cliente
        gbc.gridx = 0; gbc.gridy = 1;
        pnlForm.add(new JLabel("Cliente:"), gbc);
        
        // Campo hidden para ID
        txtIdCliente = new JTextField();
        txtIdCliente.setVisible(false);
        add(txtIdCliente); 
        
        // Campo de texto Nome (Read-only)
        txtNomeCliente = new JTextField(20);
        txtNomeCliente.setEditable(false);
        txtNomeCliente.setBackground(new Color(250, 250, 250));
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.gridwidth = 2; 
        gbc.weightx = 1.0;
        pnlForm.add(txtNomeCliente, gbc);
        gbc.gridwidth = 1; // Reset
        gbc.weightx = 0;

        // Botão Buscar Cliente
        JButton btnBuscarCliente = new JButton("Buscar");
        estilizarBotao(btnBuscarCliente, new Color(52, 152, 219));
        btnBuscarCliente.setPreferredSize(new java.awt.Dimension(80, 25)); 
        btnBuscarCliente.addActionListener(e -> abrirBuscaCliente());
        gbc.gridx = 3; gbc.gridy = 1;
        gbc.insets = new Insets(10, 5, 10, 10); 
        pnlForm.add(btnBuscarCliente, gbc);
        gbc.insets = new Insets(10, 10, 10, 10); // Reset
        
        // Exemplar
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0;
        pnlForm.add(new JLabel("ID Exemplar:"), gbc);
        
        txtIdExemplar = new JTextField(5);
        gbc.gridx = 1; gbc.gridy = 2;
        pnlForm.add(txtIdExemplar, gbc);
        
        cbExemplar = new JComboBox<>();
        gbc.gridx = 2; gbc.gridy = 2;
        pnlForm.add(cbExemplar, gbc);
        
        // Container para centralizar o formulário na tela
        JPanel pnlCenterContainer = new JPanel(new GridBagLayout());
        pnlCenterContainer.setBackground(new Color(240, 242, 245));
        pnlCenterContainer.add(pnlForm);
        
        add(pnlCenterContainer, BorderLayout.CENTER);

        // --- PAINEL SUL (BOTÕES) ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlBotoes.setBackground(Color.WHITE);
        pnlBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));
        
        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(99, 110, 114));
        pnlBotoes.add(btnVoltar);
        
        btnSalvar = new JButton("Confirmar");
        estilizarBotao(btnSalvar, new Color(46, 204, 113));
        pnlBotoes.add(btnSalvar);
        
        add(pnlBotoes, BorderLayout.SOUTH);

        // Listeners
        btnVoltar.addActionListener(e -> dispose());
        btnSalvar.addActionListener(e -> btnSalvarActionPerformed(e));
        
        // Sincronização listeners (mantidos da lógica original)

        cbExemplar.addActionListener(e -> {
            Exemplar sel = (Exemplar) cbExemplar.getSelectedItem();
            if (sel != null) txtIdExemplar.setText(sel.getId().toString());
        });

        txtIdExemplar.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) { sincronizarTextoParaComboExemplar(); }
        });
    }
    
    private void estiloBotao(JButton btn, Color cor) {
         estilizarBotao(btn, cor);
    }

    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void configurarComboBoxes() {
        cbExemplar.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Exemplar) {
                    Exemplar ex = (Exemplar) value;
                    String titulo = isbnTituloCache.getOrDefault(ex.getIsbnLivro(), "Carregando...");
                    setText(titulo + " (ID: " + ex.getId() + " / Tomb: " + ex.getNumeroTombamento() + ")");
                }
                return this;
            }
        });
    }

    private void preencherDados() {
        try {
            List<Exemplar> exemplares = emprestimoController.findAllExemplares();
            // Pre-carrega cache de títulos para performance na renderização
            isbnTituloCache.clear();
            for (Exemplar ex : exemplares) {
                cbExemplar.addItem(ex);
                String isbn = ex.getIsbnLivro();
                if (isbn != null && !isbnTituloCache.containsKey(isbn)) {
                    try {
                        br.com.ifba.biblioteca.livro.entity.Livro l = livroService.findByIsbn(isbn);
                        if (l != null) isbnTituloCache.put(isbn, l.getTitulo());
                        else isbnTituloCache.put(isbn, "Livro Não Enc.");
                    } catch (Exception e) {
                        isbnTituloCache.put(isbn, "Erro Busca");
                    }
                }
            }
            

            cbExemplar.setSelectedItem(null);
            txtIdCliente.setText("");
            txtIdExemplar.setText("");
        } catch (Exception e) {
            logger.severe("Erro ao preencher combos: " + e.getMessage());
        }
    }



    private void sincronizarTextoParaComboExemplar() {
        try {
            Long id = Long.parseLong(txtIdExemplar.getText());
            for (int i = 0; i < cbExemplar.getItemCount(); i++) {
                Exemplar ex = cbExemplar.getItemAt(i);
                if (ex.getId().equals(id)) {
                    cbExemplar.setSelectedItem(ex);
                    return;
                }
            }
        } catch (Exception e) {}
    }


    private void btnSalvarActionPerformed(ActionEvent evt) {
        try {
            String idClienteStr = txtIdCliente.getText();
            String idExemplarStr = txtIdExemplar.getText();

            if (idClienteStr.trim().isEmpty() || idExemplarStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha os IDs do Cliente e do Exemplar.");
                return;
            }

            Long idCliente = Long.parseLong(txtIdCliente.getText()); 
            Long idExemplar = Long.parseLong(txtIdExemplar.getText());

            Cliente cliente = new Cliente(); 
            cliente.setId(idCliente);

            Exemplar exemplar = new Exemplar();
            exemplar.setId(idExemplar);

            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setCliente(cliente); 
            emprestimo.setExemplar(exemplar);

            Emprestimo salvo = emprestimoController.save(emprestimo);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            JOptionPane.showMessageDialog(this, "Empréstimo realizado!\nDevolução: " + (salvo.getDataPrevistaDevolucao() != null ? salvo.getDataPrevistaDevolucao().format(formatter) : "N/A"));

            if (emprestimoListar != null) {
                emprestimoListar.carregarDados(); 
            }
            this.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Digite apenas números nos IDs.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
