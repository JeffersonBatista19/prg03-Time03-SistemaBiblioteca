package br.com.ifba.biblioteca.menu.view;

import br.com.ifba.biblioteca.autor.view.AutorListar;
import br.com.ifba.biblioteca.categoria.view.CategoriaListar;
import br.com.ifba.biblioteca.cliente.view.ClienteListar;
import br.com.ifba.biblioteca.editora.view.EditoraListar;
import br.com.ifba.biblioteca.emprestimo.view.EmprestimoListar;
import br.com.ifba.biblioteca.evento.view.TelaEventosListar;
import br.com.ifba.biblioteca.exemplar.view.ExemplarListar;
import br.com.ifba.biblioteca.livro.view.LivroListar;
import br.com.ifba.biblioteca.multa.view.MultaListar;
import br.com.ifba.biblioteca.reserva.view.ReservaListar;
import br.com.ifba.biblioteca.usuario.view.UsuarioListar;
import br.com.ifba.biblioteca.usuario.entity.Usuario;
import br.com.ifba.biblioteca.sugestaoaquisicao.view.TelaSugestaoAquisicaoListar;
import br.com.ifba.biblioteca.login.view.LoginView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Tela Principal Modernizada - Dashboard
 * @author guilhermeAmedrado
 */
@Component
public class TelaPrincipal extends JFrame {

    @Autowired
    private ApplicationContext context;

    // Cores do Tema (Flat Design)
    private final Color COR_FUNDO = new Color(240, 242, 245);       // Cinza Claro (Off-White)
    private final Color COR_HEADER = new Color(44, 62, 80);         // Azul Escuro (Midnight Blue)
    private final Color COR_HEADER_TEXTO = Color.WHITE;
    private final Color COR_BOTAO_FUNDO = Color.WHITE;
    private final Color COR_BOTAO_TEXTO = new Color(50, 50, 50);
    private final Color COR_BOTAO_HOVER = new Color(230, 230, 230); // Cinza Hover
    
    // Cores de Categoria (Detalhes laterais dos botões)
    private final Color CAT_CIRCULACAO = new Color(52, 152, 219);   // Azul
    private final Color CAT_ACERVO = new Color(230, 126, 34);       // Laranja
    private final Color CAT_PESSOAS = new Color(46, 204, 113);      // Verde
    private final Color CAT_OUTROS = new Color(155, 89, 182);       // Roxo

    // Controle de Permissões
    private JPanel cardUsuarios;
    private Usuario usuarioLogado;

    public TelaPrincipal() {
        configurarJanela();
        inicializarComponentes();
    }

    private void configurarJanela() {
        setTitle("Sistema de Biblioteca - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768); // Tamanho HD padrão
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Tela Cheia
        setLocationRelativeTo(null); // Centraliza
        setLayout(new BorderLayout());
        getContentPane().setBackground(COR_FUNDO);
    }

    private void inicializarComponentes() {
        // CABEÇALHO (HEADER)
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(COR_HEADER);
        panelHeader.setPreferredSize(new Dimension(getWidth(), 80));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JLabel lblTitulo = new JLabel("SISTEMA DE BIBLIOTECA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(COR_HEADER_TEXTO);
        
        JLabel lblSubtitulo = new JLabel("Painel de Gestão");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(200, 200, 200));

        JPanel tituloContainer = new JPanel(new GridLayout(2, 1));
        tituloContainer.setOpaque(false);
        tituloContainer.add(lblTitulo);
        tituloContainer.add(lblSubtitulo);

        // Botão Sair no Header
        JButton btnSair = creatingBtnSair();
        
        panelHeader.add(tituloContainer, BorderLayout.WEST);
        panelHeader.add(btnSair, BorderLayout.EAST);
        add(panelHeader, BorderLayout.NORTH);


        // ÁREA DE CONTEÚDO (GRID DE BOTÕES)
        JPanel panelConteudo = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelConteudo.setBackground(COR_FUNDO);
        panelConteudo.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Container Grid Responsivo (fixamos largura para organização)
        JPanel gridMenu = new JPanel(new GridLayout(0, 4, 20, 20)); // 4 Colunas
        gridMenu.setBackground(COR_FUNDO);
        
        // Adicionando os Cards
        // --- CIRCULAÇÃO ---
        gridMenu.add(criarCard("Empréstimos", "Gerenciar Saídas", "📚", CAT_CIRCULACAO, e -> abrirTela(EmprestimoListar.class)));
        gridMenu.add(criarCard("Reservas", "Fila de Espera", "📅", CAT_CIRCULACAO, e -> abrirTela(ReservaListar.class)));
        gridMenu.add(criarCard("Multas", "Pendências", "💲", CAT_CIRCULACAO, e -> abrirTela(MultaListar.class)));
        gridMenu.add(criarCard("Exemplares", "Cópias Físicas", "🏷️", CAT_CIRCULACAO, e -> abrirTela(ExemplarListar.class)));

        // --- ACERVO ---
        gridMenu.add(criarCard("Livros", "Catálogo Geral", "📖", CAT_ACERVO, e -> abrirTela(LivroListar.class)));
        gridMenu.add(criarCard("Autores", "Escritores", "✍️", CAT_ACERVO, e -> abrirTela(AutorListar.class)));
        gridMenu.add(criarCard("Editoras", "Publicadoras", "🏢", CAT_ACERVO, e -> abrirTela(EditoraListar.class)));
        gridMenu.add(criarCard("Categorias", "Gêneros", "🔖", CAT_ACERVO, e -> abrirTela(CategoriaListar.class)));

        // --- PESSOAS ---
        gridMenu.add(criarCard("Clientes", "Leitores", "👥", CAT_PESSOAS, e -> abrirTela(ClienteListar.class)));
        
        // --- CARD USUÁRIOS (COM REFERÊNCIA) ---
        this.cardUsuarios = criarCard("Usuários", "Funcionários", "🔐", CAT_PESSOAS, e -> abrirTela(UsuarioListar.class));
        gridMenu.add(cardUsuarios);

        // --- OUTROS ---
        gridMenu.add(criarCard("Eventos", "Agenda Cultural", "🎉", CAT_OUTROS, e -> abrirTela(TelaEventosListar.class)));
        gridMenu.add(criarCard("Sugestões", "Pedidos de Compra", "💡", CAT_OUTROS, e -> abrirTela(TelaSugestaoAquisicaoListar.class)));

        panelConteudo.add(gridMenu);
        add(panelConteudo, BorderLayout.CENTER);
        
        //RODAPÉ (FOOTER)
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelFooter.setBackground(COR_FUNDO);
        JLabel lblFooter = new JLabel("Versão 2.0 - Desenvolvido por Equipe Biblioteca Time 03");
        lblFooter.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblFooter.setForeground(Color.GRAY);
        panelFooter.add(lblFooter);
        add(panelFooter, BorderLayout.SOUTH);
    }
    
    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        
        // Aplica permissões
        if (usuario instanceof br.com.ifba.biblioteca.usuario.entity.Bibliotecario) {
            // Bibliotecário NÃO pode gerenciar usuários (Criar/Editar Admins)
            if(cardUsuarios != null) cardUsuarios.setVisible(false);
        } else {
             // Admin vê tudo
            if(cardUsuarios != null) cardUsuarios.setVisible(true);
        }
    }

    // --- MÉTODOS AUXILIARES DE UI ---

    private JButton creatingBtnSair() {
        JButton btn = new JButton("SAIR");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(192, 57, 43)); // Vermelho
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> confirmarSaida());
        return btn;
    }

    private JPanel criarCard(String titulo, String subtitulo, String icone, Color corDestaque, ActionListener acao) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(200, 120));
        card.setBackground(COR_BOTAO_FUNDO);
        
        // Borda Esquerda Colorida (indicador de categoria)
        JPanel indicador = new JPanel();
        indicador.setBackground(corDestaque);
        indicador.setPreferredSize(new Dimension(6, 120));
        card.add(indicador, BorderLayout.WEST);

        // Conteúdo do Card
        JPanel conteudo = new JPanel(new GridLayout(3, 1));
        conteudo.setOpaque(false);
        conteudo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lblIcone = new JLabel(icone);
        lblIcone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24)); // Emoji Font
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(COR_BOTAO_TEXTO);

        JLabel lblSub = new JLabel(subtitulo);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(Color.GRAY);

        conteudo.add(lblIcone);
        conteudo.add(lblTitulo);
        conteudo.add(lblSub);
        
        card.add(conteudo, BorderLayout.CENTER);

        // Interatividade (Hover e Click)
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(COR_BOTAO_HOVER);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(COR_BOTAO_FUNDO);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                acao.actionPerformed(null);
            }
        });

        // Simula sombra com borda
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        return card;
    }

    // --- LÓGICA DE NAVEGAÇÃO ---

    private <T extends JFrame> void abrirTela(Class<T> classeTela) {
        try {
            T tela = context.getBean(classeTela);
            tela.setLocationRelativeTo(this);
            
            // Tratamento especial para telas que precisam carregar dados
            if (tela instanceof ReservaListar) {
                ((ReservaListar) tela).carregarDados();
            } else if (tela instanceof EmprestimoListar) {
                 ((EmprestimoListar) tela).carregarDados();
            } else if (tela instanceof UsuarioListar) {
                 ((UsuarioListar) tela).carregarDados();
            } else if (tela instanceof TelaSugestaoAquisicaoListar) {
                 ((TelaSugestaoAquisicaoListar) tela).carregarTabela();
            }
            // Outras telas carregam no construtor ou no init, por padrão
            
            tela.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir tela: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void confirmarSaida() {
        Object[] options = {"Trocar Usuário", "Fechar Sistema", "Cancelar"};
        int opcao = JOptionPane.showOptionDialog(this,
                "O que deseja fazer?",
                "Sair",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (opcao == 0) { // Trocar Usuário
            this.dispose();
            try {
                LoginView loginView = context.getBean(LoginView.class);
                loginView.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0); // Fallback
            }
        } else if (opcao == 1) { // Fechar Sistema
            System.exit(0);
        }
    }
}
