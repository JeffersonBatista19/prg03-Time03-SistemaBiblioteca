package br.com.ifba.biblioteca.reserva.view;

import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import br.com.ifba.biblioteca.reserva.entity.Reserva;
import br.com.ifba.biblioteca.reserva.entity.StatusReserva;
import br.com.ifba.biblioteca.reserva.service.ReservaService;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import br.com.ifba.biblioteca.cliente.entity.Cliente;
import br.com.ifba.biblioteca.cliente.service.ClienteService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@Component
@Scope("prototype")
public class ReservaAdicionar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReservaAdicionar.class.getName());

    @Autowired
    private ReservaService reservaService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private ApplicationContext context;

    private Cliente clienteSelecionado;
    private Exemplar exemplarSelecionado;
    private ReservaListar telaPai;

    private JTextField txtClienteId;
    private JTextField txtTombamento;
    private JSpinner spinnerDataReserva;
    private JButton btnBuscarCliente, btnBuscarExemplar, btnSalvar, btnCancelar, btnVoltar;

    @Autowired
    public ReservaAdicionar() {
        initComponents();
        setLocationRelativeTo(null);
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // Maximizado
    }

    private void initComponents() {
        setTitle("Criar Reserva");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- PAINEL CENTRAL (FORMULÁRIO) ---
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
        JLabel lblTitulo = new JLabel("CRIAR NOVA RESERVA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlForm.add(lblTitulo, gbc);
        
        gbc.gridwidth = 1; // Reset width
        gbc.anchor = GridBagConstraints.WEST; // Reset anchor

        // Cliente
        gbc.gridx = 0; gbc.gridy = 1;
        pnlForm.add(new JLabel("ID Cliente:"), gbc);

        txtClienteId = new JTextField(15);
        txtClienteId.setEditable(false);
        gbc.gridx = 1; gbc.gridy = 1;
        pnlForm.add(txtClienteId, gbc);

        btnBuscarCliente = new JButton("Buscar Cliente");
        estilizarBotao(btnBuscarCliente, new Color(52, 152, 219));
        gbc.gridx = 2; gbc.gridy = 1;
        pnlForm.add(btnBuscarCliente, gbc);

        // Exemplar
        gbc.gridx = 0; gbc.gridy = 2;
        pnlForm.add(new JLabel("Tombamento:"), gbc);

        txtTombamento = new JTextField(15);
        txtTombamento.setEditable(false);
        gbc.gridx = 1; gbc.gridy = 2;
        pnlForm.add(txtTombamento, gbc);

        btnBuscarExemplar = new JButton("Buscar Exemplar");
        estilizarBotao(btnBuscarExemplar, new Color(52, 152, 219));
        gbc.gridx = 2; gbc.gridy = 2;
        pnlForm.add(btnBuscarExemplar, gbc);

        // Data
        gbc.gridx = 0; gbc.gridy = 3;
        pnlForm.add(new JLabel("Data da Reserva:"), gbc);

        spinnerDataReserva = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerDataReserva, "dd/MM/yyyy");
        spinnerDataReserva.setEditor(editor);
        spinnerDataReserva.setPreferredSize(new java.awt.Dimension(150, 25));
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.gridwidth = 2;
        pnlForm.add(spinnerDataReserva, gbc);

        // Container centralizar
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
        
        btnSalvar = new JButton("Salvar");
        estilizarBotao(btnSalvar, new Color(46, 204, 113));
        pnlBotoes.add(btnSalvar);

        add(pnlBotoes, BorderLayout.SOUTH);

        // Listeners
        btnVoltar.addActionListener(e -> dispose());
        btnSalvar.addActionListener(e -> btnSalvarActionPerformed(e));
        btnBuscarCliente.addActionListener(e -> btnBuscarClienteActionPerformed(e));
        btnBuscarExemplar.addActionListener(e -> btnBuscarExemplarActionPerformed(e));
    }

    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    public void setTelaPai(ReservaListar telaPai) {
        this.telaPai = telaPai;
    }
    
    public void setClienteSelecionado(Cliente cliente) {
        this.clienteSelecionado = cliente;
        txtClienteId.setText(String.valueOf(cliente.getId()));
    }
    
    public void setExemplarSelecionado(Exemplar exemplar) {
        this.exemplarSelecionado = exemplar;
        txtTombamento.setText(String.valueOf(exemplar.getNumeroTombamento()));
    }

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {
        BuscarCliente tela = context.getBean(BuscarCliente.class);
        tela.setTelaPai(this);
        tela.setVisible(true);
    }
    
    private void btnBuscarExemplarActionPerformed(java.awt.event.ActionEvent evt) {
        BuscarExemplar tela = context.getBean(BuscarExemplar.class);
        tela.setTelaPai(this);
        tela.carregarExemplares();
        tela.setVisible(true);
    }
    
    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            logger.info("Iniciando criação de reserva");
            if (clienteSelecionado == null) throw new RuntimeException("Selecione um cliente!");
            if (exemplarSelecionado == null) throw new RuntimeException("Selecione um exemplar!");

            Reserva reserva = new Reserva();
            reserva.setCliente(clienteSelecionado);
            reserva.setExemplar(exemplarSelecionado);
            
            Date data = (Date) spinnerDataReserva.getValue();
            reserva.setDataReserva(data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            reserva.setStatus(StatusReserva.PENDENTE);

            reservaService.save(reserva);

            if (telaPai != null) {
                telaPai.carregarTabela(reservaService.findAll());
            }

            JOptionPane.showMessageDialog(this, "Reserva criada com sucesso!");
            logger.info("Reserva criada com sucesso");
            this.dispose();

        } catch (RuntimeException e) {
            logger.severe("Erro ao criar reserva: " + e.getMessage());
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
