package br.com.ifba.biblioteca.exemplar.view;
import br.com.ifba.biblioteca.exemplar.controller.ExemplarController;
import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;


@Component
@Scope("prototype")
public class ExemplarListar extends javax.swing.JFrame {
    
    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private ExemplarController exemplarController;


    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ExemplarListar.class.getName());

    public ExemplarListar() {
        initComponents();
        spnTombamento.addChangeListener(e -> filtrarPorTombamento());
        setLocationRelativeTo(null); // centraliza
        
        // Esconde a coluna ID
    jTable1.getColumnModel().getColumn(4).setMinWidth(0);
    jTable1.getColumnModel().getColumn(4).setMaxWidth(0);
    jTable1.getColumnModel().getColumn(4).setWidth(0);
    jTable1.getColumnModel().getColumn(4).setPreferredWidth(0);
   
    }
    
    public javax.swing.JTable getjTable1() {
        return jTable1;
    }


   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAdicionar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        spnTombamento = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tombamento", "Estado de Conservação", "Localização", "Status", "ID"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnAdicionar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnExcluir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Buscar Tombamento:");

        spnTombamento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        spnTombamento.setModel(new javax.swing.SpinnerNumberModel());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnTombamento, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdicionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExcluir)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionar)
                    .addComponent(btnExcluir)
                    .addComponent(btnEditar)
                    .addComponent(jLabel2)
                    .addComponent(spnTombamento, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
    ExemplarAdicionar telaAdicionar = context.getBean(ExemplarAdicionar.class);
    telaAdicionar.setExemplarListar(this); // <-- passa a instância atual da tela

    JFrame frame = new JFrame("Adicionar Exemplar");
    frame.setContentPane(telaAdicionar);
    frame.pack(); // ajusta o tamanho
    frame.setLocationRelativeTo(null); // centraliza
    frame.setVisible(true);
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        int linhaSelecionada = jTable1.getSelectedRow();
if (linhaSelecionada == -1) {
    JOptionPane.showMessageDialog(this,
        "Selecione um exemplar para excluir!",
        "Aviso",
        JOptionPane.WARNING_MESSAGE);
    return;
}

// pega o id do exemplar na coluna oculta
Long idExemplar = (Long) jTable1.getValueAt(linhaSelecionada, 4);

try {
    // busca o exemplar pelo id
    Exemplar ex = exemplarController.findById(idExemplar);

    // confirma exclusão
    int opcao = JOptionPane.showConfirmDialog(this,
        "Deseja realmente excluir o exemplar?",
        "Confirmação",
        JOptionPane.YES_NO_OPTION);

    if (opcao == JOptionPane.YES_OPTION) {
        exemplarController.delete(ex); // exclui pelo objeto
        carregarExemplares(); // atualiza a tabela
        
        // mensagem de sucesso
        javax.swing.JOptionPane.showMessageDialog(this,
        "Exemplar excluído com sucesso!",
        "Sucesso",
        javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

} catch (Exception e) {
    JOptionPane.showMessageDialog(this,
        "Erro ao excluir exemplar: " + e.getMessage(),
        "Erro",
        JOptionPane.ERROR_MESSAGE);
}

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
      int linhaSelecionada = jTable1.getSelectedRow();

    if (linhaSelecionada == -1) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Selecione um exemplar para editar!",
            "Aviso",
            javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    // pega os dados da linha
    int tombamento = (Integer) jTable1.getValueAt(linhaSelecionada, 0);
    String conservacao = jTable1.getValueAt(linhaSelecionada, 1).toString();
    String local = jTable1.getValueAt(linhaSelecionada, 2).toString();
    String status = jTable1.getValueAt(linhaSelecionada, 3).toString();
    Long idExemplar = (Long) jTable1.getValueAt(linhaSelecionada, 4);

    ExemplarEditar editar = context.getBean(
    ExemplarEditar.class, tombamento, conservacao, local, status, idExemplar, this
);
editar.setVisible(true);



    }//GEN-LAST:event_btnEditarActionPerformed
    
    public void carregarExemplares() {
    try {
        List<Exemplar> exemplares = exemplarController.findAll();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // limpa a tabela antes de carregar

        for (Exemplar ex : exemplares) {
            // adiciona ID como coluna oculta no final
            model.addRow(new Object[]{
                ex.getNumeroTombamento(),
                ex.getConservacao(),
                ex.getLocalizacaoFisica(),
                ex.getStatus(),
                ex.getId() // coluna ID oculta
            });
        }

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Erro ao carregar exemplares: " + e.getMessage());
    }
}
private void filtrarPorTombamento() {
    int valor = (Integer) spnTombamento.getValue();
    if (valor == 0) {
        carregarExemplares();
        return;
    }

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    try {
        for (Exemplar ex : exemplarController.findAll()) {
            if (ex.getNumeroTombamento() == valor) {
                model.addRow(new Object[]{
                    ex.getNumeroTombamento(),
                    ex.getConservacao(),
                    ex.getLocalizacaoFisica(),
                    ex.getStatus(),
                    ex.getId()
                });
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao filtrar: " + e.getMessage());
    }
}



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JSpinner spnTombamento;
    // End of variables declaration//GEN-END:variables
}
