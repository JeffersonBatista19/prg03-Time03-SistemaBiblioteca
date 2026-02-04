package br.com.ifba.biblioteca.usuario.view;

import br.com.ifba.biblioteca.pessoa.entity.NivelAcesso;
import br.com.ifba.biblioteca.pessoa.entity.StatusPessoa;
import br.com.ifba.biblioteca.pessoa.entity.TipoPerfil;
import br.com.ifba.biblioteca.usuario.controller.UsuarioIController;
import br.com.ifba.biblioteca.usuario.entity.Usuario;
import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author jeffe
 */

@Component
@Scope("prototype")
public class UsuarioEditar extends JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UsuarioEditar.class.getName());
    
    @Autowired
    private UsuarioIController usuarioController;

    private UsuarioListar usuarioListar;
    private Long idUsuario;

    // Componentes
    private JTextField txtNome, txtCpf, txtTelefone, txtLogin, txtDataCadastro;
    private JPasswordField txtSenha;
    private JComboBox<TipoPerfil> cmbPerfil;
    private JComboBox<NivelAcesso> cmbNivel;
    private JComboBox<StatusPessoa> cmbStatus;
    private JButton btnSalvar, btnCancelar;

    // Construtor principal
    public UsuarioEditar(String nome, String cpf, String telefone, String login,
                         String perfil, String nivel, String status,
                         Long idUsuario, UsuarioListar telaListar) {
        
        this.usuarioListar = telaListar;
        this.idUsuario = idUsuario;
        
        initComponents();
        carregarCombos();
        
        preencherDadosIniciais(nome, cpf, telefone, login, perfil, nivel, status);
    }

    private void initComponents() {
        setTitle("Editar Usuário");
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
        JLabel lblTitulo = new JLabel("EDITAR USUÁRIO");
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
        adicionarCampo(pnlForm, gbc, 4, "Data de Cadastro:", txtDataCadastro);

        adicionarCampo(pnlForm, gbc, 5, "Login:", txtLogin = new JTextField(15));
        adicionarCampo(pnlForm, gbc, 6, "Nova Senha (opcional):", txtSenha = new JPasswordField(15));

        cmbPerfil = new JComboBox<>();
        adicionarCampo(pnlForm, gbc, 7, "Perfil:", cmbPerfil);

        cmbNivel = new JComboBox<>();
        adicionarCampo(pnlForm, gbc, 8, "Nível de Acesso:", cmbNivel);

        cmbStatus = new JComboBox<>();
        adicionarCampo(pnlForm, gbc, 9, "Status:", cmbStatus);

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

    private void carregarCombos() {
        // Preenche combos; se falhar, fica em branco
        if(cmbPerfil == null) return; 
        
        cmbPerfil.removeAllItems();
        for (TipoPerfil p : TipoPerfil.values()) cmbPerfil.addItem(p);

        cmbNivel.removeAllItems();
        for (NivelAcesso n : NivelAcesso.values()) cmbNivel.addItem(n);

        cmbStatus.removeAllItems();
        for (StatusPessoa s : StatusPessoa.values()) cmbStatus.addItem(s);
    }

    private void preencherDadosIniciais(String nome, String cpf, String telefone, String login,
                                        String perfil, String nivel, String status) {
        txtNome.setText(nome);
        txtCpf.setText(cpf);
        txtTelefone.setText(telefone);
        txtLogin.setText(login);
        
        try {
            if(perfil != null) cmbPerfil.setSelectedItem(TipoPerfil.valueOf(perfil));
            if(nivel != null) cmbNivel.setSelectedItem(NivelAcesso.valueOf(nivel));
            if(status != null) cmbStatus.setSelectedItem(StatusPessoa.valueOf(status));
        } catch(Exception e) {
            System.err.println("Erro ao converter enums: " + e.getMessage());
        }
    }
     
    private boolean validar() {
        if (txtNome.getText().trim().isEmpty()) return false;
        if (txtCpf.getText().trim().isEmpty()) return false;
        if (txtTelefone.getText().trim().isEmpty()) return false;
        if (txtLogin.getText().trim().isEmpty()) return false;
        return true;
    }

    private void salvarAlteracoes() {
        if (!validar()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios!");
            return;
        }

        try {
            Usuario existente = usuarioController.findById(idUsuario);
            if (existente == null) {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado!");
                return;
            }

            existente.setNomeCompleto(txtNome.getText().trim());
            existente.setCpf(txtCpf.getText().trim());
            existente.setTelefone(txtTelefone.getText().trim());
            existente.setLogin(txtLogin.getText().trim());

            existente.setTipoPerfil((TipoPerfil) cmbPerfil.getSelectedItem());
            existente.setNivelAcesso((NivelAcesso) cmbNivel.getSelectedItem());
            existente.setStatusPessoa((StatusPessoa) cmbStatus.getSelectedItem());

            // senha opcional: só atualiza se digitou algo
            String senha = String.valueOf(txtSenha.getPassword()).trim();
            if (!senha.isEmpty()) {
                existente.setSenha(senha);
            }

            // mantém dataCadastro, mas se estiver nula, define
            if (existente.getDataCadastro() == null) {
                existente.setDataCadastro(LocalDate.now());
            }

            usuarioController.update(existente);

            JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!");
            if (usuarioListar != null) usuarioListar.carregarDados();
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
