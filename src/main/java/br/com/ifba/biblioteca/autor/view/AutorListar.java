package br.com.ifba.biblioteca.autor.view;

import br.com.ifba.biblioteca.autor.controller.AutorIController;
import br.com.ifba.biblioteca.autor.entity.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Tela principal para listar, buscar e gerenciar Autores.
 * @author guilherme
 */
@Component
public class AutorListar extends JFrame {

    @Autowired
    private AutorIController autorController;

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField txtBusca;
    private JButton btnBuscar, btnAdicionar, btnEditar, btnRemover, btnAtualizar, btnVoltar;

    public AutorListar() {
        super("Gerenciar Autores");
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Tela Cheia
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- Painel Superior (Busca e Ações) ---
        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        painelTopo.setBackground(Color.WHITE);
        painelTopo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        painelTopo.add(new JLabel("Nome:"));
        txtBusca = new JTextField(25);
        txtBusca.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelTopo.add(txtBusca);
        
        btnBuscar = new JButton("Buscar");
        estilizarBotao(btnBuscar, new Color(52, 152, 219)); // Azul
        painelTopo.add(btnBuscar);
        
        btnAdicionar = new JButton("Adicionar");
        estilizarBotao(btnAdicionar, new Color(46, 204, 113)); // Verde
        painelTopo.add(btnAdicionar);
        
        btnEditar = new JButton("Editar");
        estilizarBotao(btnEditar, new Color(243, 156, 18)); // Laranja
        painelTopo.add(btnEditar);
        
        btnRemover = new JButton("Remover");
        estilizarBotao(btnRemover, new Color(231, 76, 60)); // Vermelho
        painelTopo.add(btnRemover);
        
        btnAtualizar = new JButton("Atualizar Lista");
        estilizarBotao(btnAtualizar, new Color(241, 196, 15)); // Amarelo
        btnAtualizar.setForeground(Color.DARK_GRAY);
        painelTopo.add(btnAtualizar);
        
        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(99, 110, 114)); // Cinza
        painelTopo.add(btnVoltar);
        
        add(painelTopo, BorderLayout.NORTH);

        // --- Tabela (Centro) ---
        String[] colunas = {"ID", "Nome", "Nacionalidade", "Obras"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabela.setRowHeight(25);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // --- Ações dos Botões ---
        btnBuscar.addActionListener(e -> buscarPorNome());
        btnAtualizar.addActionListener(e -> carregarDados());

        btnAdicionar.addActionListener(e -> {
            AutorAdicionar telaAdicionar = new AutorAdicionar(autorController, this);
            telaAdicionar.setLocationRelativeTo(null);
            telaAdicionar.setVisible(true);
        });

        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                Long id = (Long) tabela.getValueAt(linha, 0);
                Autor autor = autorController.findById(id);
                AutorEditar telaEditar = new AutorEditar(autorController, this, autor);
                telaEditar.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um autor para editar.");
            }
        });

        btnRemover.addActionListener(e -> deletarAutor());
        btnVoltar.addActionListener(e -> dispose());
        
        // Listener ENTER na busca
        txtBusca.addActionListener(e -> buscarPorNome());
    }
    
    private void estilizarBotao(JButton btn, Color cor) {
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    public void carregarDados() {
        modeloTabela.setRowCount(0); // Limpa tabela
        List<Autor> lista = autorController.findAll();
        for (Autor a : lista) {
            modeloTabela.addRow(new Object[]{
                a.getId(), 
                a.getNome(), 
                a.getNacionalidade(), 
                a.getQuantidadeObras()
            });
        }
    }

    private void buscarPorNome() {
        String nome = txtBusca.getText();
        if (nome.isEmpty()) {
            carregarDados();
            return;
        }
        modeloTabela.setRowCount(0);
        List<Autor> lista = autorController.findByNome(nome);
        for (Autor a : lista) {
            modeloTabela.addRow(new Object[]{
                a.getId(), a.getNome(), a.getNacionalidade(), a.getQuantidadeObras()
            });
        }
    }

    private void deletarAutor() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            Long id = (Long) tabela.getValueAt(linha, 0);
            int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir?", "Excluir", JOptionPane.YES_NO_OPTION);
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                try {
                    autorController.delete(id);
                    JOptionPane.showMessageDialog(this, "Autor excluído com sucesso!");
                    carregarDados();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um autor para remover.");
        }
    }
}