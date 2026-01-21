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

    public AutorListar() {
        super("Gerenciar Autores");
        initComponents();
    }

    private void initComponents() {
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela

        // --- Painel Superior (Busca) ---
        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBusca = new JTextField(30);
        JButton btnBuscar = new JButton("Buscar por Nome");
        
        painelTopo.add(new JLabel("Nome:"));
        painelTopo.add(txtBusca);
        painelTopo.add(btnBuscar);
        add(painelTopo, BorderLayout.NORTH);

        // --- Tabela (Centro) ---
        String[] colunas = {"ID", "Nome", "Nacionalidade", "Obras"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // --- Painel Inferior (Botões) ---
        JPanel painelBotoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");
        JButton btnAtualizar = new JButton("Atualizar Lista");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnAtualizar);
        add(painelBotoes, BorderLayout.SOUTH);

        // --- Ações dos Botões ---
        
        // Botão Buscar
        btnBuscar.addActionListener(e -> buscarPorNome());

        // Botão Atualizar (Recarrega tudo)
        btnAtualizar.addActionListener(e -> carregarDados());

        // Botão Adicionar: Abre a tela de cadastro
        btnAdicionar.addActionListener(e -> {
            AutorAdicionar telaAdicionar = new AutorAdicionar(autorController, this);
            telaAdicionar.setVisible(true);
        });

        // Botão Editar: Pega o selecionado e abre a tela de edição
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

        // Botão Remover
        btnRemover.addActionListener(e -> deletarAutor());
    }

    // Método chamado pelo Spring ou manualmente para carregar a tabela ao abrir
    // Dica: Chame este metodo logo após fazer o setVisible(true) na Main ou no construtor se o controller já estiver injetado
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