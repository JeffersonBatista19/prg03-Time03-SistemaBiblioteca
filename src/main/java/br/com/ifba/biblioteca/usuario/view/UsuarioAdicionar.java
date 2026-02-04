package br.com.ifba.biblioteca.usuario.view;

import br.com.ifba.biblioteca.pessoa.entity.NivelAcesso;
import br.com.ifba.biblioteca.pessoa.entity.StatusPessoa;
import br.com.ifba.biblioteca.pessoa.entity.TipoPerfil;
import br.com.ifba.biblioteca.usuario.controller.UsuarioIController;
import br.com.ifba.biblioteca.usuario.entity.Usuario;
import br.com.ifba.biblioteca.usuario.entity.Administrador;
import br.com.ifba.biblioteca.usuario.entity.Bibliotecario;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import org.springframework.beans.factory.annotation.Autowired;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
// import java.awt.Component; // REMOVED AMBIGUITY

@Component
public class UsuarioAdicionar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UsuarioAdicionar.class.getName());
    
    @Autowired
    private UsuarioIController usuarioController;

    private UsuarioListar usuarioListar;

    // Componentes
    private JTextField txtNome, txtCpf, txtTelefone, txtLogin, txtDataCadastro;
    private JPasswordField txtSenha;
    private JComboBox<TipoPerfil> cmbPerfil;
    private JComboBox<NivelAcesso> cmbNivel;
    private JComboBox<StatusPessoa> cmbStatus;
    private JButton btnSalvar, btnCancelar;

    public void setUsuarioListar(UsuarioListar usuarioListar) {
        this.usuarioListar = usuarioListar;
    }
    
    public UsuarioAdicionar() {
        initComponents();
        carregarCombos();
        txtDataCadastro.setText(LocalDate.now().toString());
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Cadastro de Usuário");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
        JLabel lblTitulo = new JLabel("CADASTRAR USUÁRIO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        pnlForm.add(lblTitulo, gbc);
        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos
        adicionarCampo(pnlForm, gbc, 1, "Nome Completo:", txtNome = new JTextField(30));
        adicionarCampo(pnlForm, gbc, 2, "CPF:", txtCpf = new JTextField(15));
        adicionarCampo(pnlForm, gbc, 3, "Telefone:", txtTelefone = new JTextField(15));
        
        txtDataCadastro = new JTextField(15);
        txtDataCadastro.setEditable(false);
        adicionarCampo(pnlForm, gbc, 4, "Data Cadastro:", txtDataCadastro);
        
        adicionarCampo(pnlForm, gbc, 5, "Login:", txtLogin = new JTextField(15));
        adicionarCampo(pnlForm, gbc, 6, "Senha:", txtSenha = new JPasswordField(15));

        adicionarCampo(pnlForm, gbc, 7, "Perfil:", cmbPerfil = new JComboBox<>());
        adicionarCampo(pnlForm, gbc, 8, "Nível:", cmbNivel = new JComboBox<>());
        adicionarCampo(pnlForm, gbc, 9, "Status:", cmbStatus = new JComboBox<>());

        // Container
        JPanel pnlCenterContainer = new JPanel(new GridBagLayout());
        pnlCenterContainer.setBackground(new Color(240, 242, 245));
        pnlCenterContainer.add(pnlForm);
        add(pnlCenterContainer, BorderLayout.CENTER);

        // --- BOTÕES ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlBotoes.setBackground(Color.WHITE);
        pnlBotoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        btnCancelar = new JButton("Voltar");
        estilizarBotao(btnCancelar, new Color(99, 110, 114));
        
        btnSalvar = new JButton("Confirmar");
        estilizarBotao(btnSalvar, new Color(46, 204, 113));

        pnlBotoes.add(btnCancelar);
        pnlBotoes.add(btnSalvar);
        add(pnlBotoes, BorderLayout.SOUTH);

        btnCancelar.addActionListener(e -> dispose());
        btnSalvar.addActionListener(e -> salvarUsuario());
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
    
    private void carregarCombos() {
        cmbPerfil.removeAllItems();
        for (TipoPerfil p : TipoPerfil.values()) cmbPerfil.addItem(p);

        cmbNivel.removeAllItems();
        for (NivelAcesso n : NivelAcesso.values()) cmbNivel.addItem(n);

        cmbStatus.removeAllItems();
        for (StatusPessoa s : StatusPessoa.values()) cmbStatus.addItem(s);
    }
    
    private boolean validar() {
        if (txtNome.getText().trim().isEmpty()) return false;
        if (txtCpf.getText().trim().isEmpty()) return false;
        if (txtTelefone.getText().trim().isEmpty()) return false;
        if (txtLogin.getText().trim().isEmpty()) return false;
        if (String.valueOf(txtSenha.getPassword()).trim().isEmpty()) return false;
        return true;
    }

    private void salvarUsuario() {
         if (!validar()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        try {
            TipoPerfil perfilSelecionado = (TipoPerfil) cmbPerfil.getSelectedItem();
            Usuario u;

            if (TipoPerfil.ADMINISTRADOR.equals(perfilSelecionado)) {
                u = new Administrador();
            } else { // Assumindo que o outro perfil é Bibliotecário
                Bibliotecario bib = new Bibliotecario();
                bib.setTurno("Matutino"); // Valor padrão/exemplo por enquanto
                bib.setMatriculaFuncionario("AUTO-" + System.currentTimeMillis()); // Gerado auto para evitar erro agora
                u = bib;
            }

            u.setNomeCompleto(txtNome.getText().trim());
            u.setCpf(txtCpf.getText().trim());
            u.setTelefone(txtTelefone.getText().trim());
            u.setDataCadastro(LocalDate.now());

            u.setLogin(txtLogin.getText().trim());
            u.setSenha(String.valueOf(txtSenha.getPassword()).trim());
            
            u.setTipoPerfil((TipoPerfil) cmbPerfil.getSelectedItem());
            u.setNivelAcesso((NivelAcesso) cmbNivel.getSelectedItem());
            u.setStatusPessoa((StatusPessoa) cmbStatus.getSelectedItem());

            usuarioController.save(u);

            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");

            if (usuarioListar != null) usuarioListar.carregarDados();
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
