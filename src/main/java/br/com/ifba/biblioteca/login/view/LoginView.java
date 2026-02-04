package br.com.ifba.biblioteca.login.view;

import br.com.ifba.biblioteca.login.service.LoginService;
import br.com.ifba.biblioteca.usuario.entity.Usuario;
import br.com.ifba.biblioteca.usuario.entity.TipoPerfilUsuario;
import java.awt.*;
import javax.swing.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LoginView extends JFrame {

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private ConfigurableApplicationContext context; // Para abrir a tela principal

    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnSair;

    public LoginView() {
        initComponents();
        setUndecorated(true); // Estilo moderno sem barra padrão
        setBackground(new Color(0, 0, 0, 0)); // Transparente para bordas arredondadas
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        
        JPanel pnlMain = new JPanel(new GridBagLayout());
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Logo / Título
        JLabel lblTitulo = new JLabel("BIBLIOTECH");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(44, 62, 80));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridx = 0; gbc.gridy = 0;
        pnlMain.add(lblTitulo, gbc);
        
        JLabel lblSub = new JLabel("Acesse sua conta");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(Color.GRAY);
        lblSub.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridy = 1;
        pnlMain.add(lblSub, gbc);
        
        // Login
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 20, 5, 20);
        pnlMain.add(new JLabel("Usuário"), gbc);
        
        txtLogin = new JTextField();
        txtLogin.setPreferredSize(new Dimension(300, 35));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 20, 15, 20);
        pnlMain.add(txtLogin, gbc);
        
        // Senha
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 20, 5, 20);
        pnlMain.add(new JLabel("Senha"), gbc);
        
        txtSenha = new JPasswordField();
        txtSenha.setPreferredSize(new Dimension(300, 35));
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 20, 30, 20);
        pnlMain.add(txtSenha, gbc);
        
        // Botão Entrar
        btnEntrar = new JButton("ENTRAR");
        btnEntrar.setBackground(new Color(41, 128, 185));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEntrar.setFocusPainted(false);
        btnEntrar.setPreferredSize(new Dimension(300, 40));
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        gbc.gridy = 6;
        pnlMain.add(btnEntrar, gbc);
        
        // Botão Sair
        btnSair = new JButton("Sair");
        btnSair.setForeground(new Color(231, 76, 60));
        btnSair.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnSair.setBorderPainted(false);
        btnSair.setContentAreaFilled(false);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        gbc.gridy = 7;
        gbc.insets = new Insets(10, 20, 10, 20);
        pnlMain.add(btnSair, gbc);

        add(pnlMain);
        
        // Actions
        btnEntrar.addActionListener(e -> autenticar());
        btnSair.addActionListener(e -> System.exit(0));
        
        // Enter key support
        getRootPane().setDefaultButton(btnEntrar);
    }
    
    private void autenticar() {
        String login = txtLogin.getText();
        String senha = new String(txtSenha.getPassword());
        
        Usuario usuario = loginService.autenticar(login, senha);
        
        if (usuario != null) {
            // Sucesso!
            JOptionPane.showMessageDialog(this, "Bem-vindo, " + usuario.getNomeCompleto() + "!");

            
             abrirTelaPrincipal(usuario);
             this.dispose();
             
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirTelaPrincipal(Usuario usuario) {
        try {
            // Obtém a instância de TelaPrincipal gerenciada pelo Spring
            br.com.ifba.biblioteca.menu.view.TelaPrincipal telaPrincipal = 
                    context.getBean(br.com.ifba.biblioteca.menu.view.TelaPrincipal.class);
            
            // Passa o usuário logado para configurar permissões
            telaPrincipal.setUsuarioLogado(usuario);
            
            telaPrincipal.setVisible(true);
             
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir tela principal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
