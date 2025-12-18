package br.com.ifba.biblioteca.reserva.view;

import br.com.ifba.biblioteca.exemplar.controller.ExemplarController;
import br.com.ifba.biblioteca.exemplar.entity.Exemplar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

//Tela responsável por listar e permitir a seleção de Exemplares.
//Atua como apoio ao módulo de Reserva, permitindo que o usuário selecione um exemplar disponível para vinculação à reserva.
@Component
@Scope("prototype") //garantir que a cada abertura da tela seja criada uma nova instância, evitando o compartilhamento de estado entre janelas
public class BuscarExemplar extends javax.swing.JFrame {
    
    // Controller responsável pelo acesso aos exemplares
    private ExemplarController exemplarController;

    // Referência da tela pai (ReservaAdicionar)
    // Usada para retornar o exemplar selecionado
    private ReservaAdicionar telaPai;

    // Logger padrão do Swing, para registro de eventos da tela
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(BuscarExemplar.class.getName());

    /**
     * Define a tela pai para retorno do exemplar selecionado
     */
    public void setTelaPai(ReservaAdicionar telaPai) {
        this.telaPai = telaPai;
    }

    /**
     * Construtor da tela
     */
    public BuscarExemplar(ReservaAdicionar telaPai,
                          ExemplarController exemplarController) {

        this.telaPai = telaPai;
        this.exemplarController = exemplarController;

        initComponents();

        // Fecha apenas esta janela (não encerra a aplicação)
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        // Centraliza a tela em relação à tela pai
        setLocationRelativeTo(telaPai);

        // Listener do spinner para filtrar por número de tombamento
        spnTombamento.addChangeListener(e -> filtrarPorTombamento());

        // Esconde a coluna ID (boa prática para não exibir ID ao usuário)
        jTable1.getColumnModel().getColumn(4).setMinWidth(0);
        jTable1.getColumnModel().getColumn(4).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(4).setWidth(0);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(0);

        // Carrega todos os exemplares ao abrir a tela
        carregarExemplares();
    }

    //Retorna a tabela de exemplares
    public javax.swing.JTable getjTable1() {
        return jTable1;
    }


   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        spnTombamento = new javax.swing.JSpinner();
        btnSelecionar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();

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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Buscar Tombamento:");

        spnTombamento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        spnTombamento.setModel(new javax.swing.SpinnerNumberModel());

        btnSelecionar.setText("Selecionar");
        btnSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSelecionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(spnTombamento, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(309, 309, 309)
                            .addComponent(btnBuscar))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(spnTombamento, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBuscar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelecionar)
                    .addComponent(btnCancelar))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    //Seleciona o exemplar escolhido e retorna para a tela pai
    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarActionPerformed
        int linha = jTable1.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um exemplar!");
            return;
        }

        // Recupera o ID do exemplar da coluna oculta
        Long idExemplar = (Long) jTable1.getValueAt(linha, 4);

        // Busca o exemplar completo através do Controller
        Exemplar exemplar = exemplarController.findById(idExemplar);

        if (exemplar == null) {
            JOptionPane.showMessageDialog(this, "Exemplar não encontrado!");
            return;
        }

        // Retorna o exemplar selecionado para a tela pai
        telaPai.setExemplarSelecionado(exemplar);

        // Fecha a tela
        this.dispose();
    }//GEN-LAST:event_btnSelecionarActionPerformed
    
    //Carrega todos os exemplares cadastrados
    public void carregarExemplares() {
        try {
            List<Exemplar> exemplares = exemplarController.findAll();

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            for (Exemplar ex : exemplares) {
                model.addRow(new Object[]{
                    ex.getNumeroTombamento(),
                    ex.getConservacao(),
                    ex.getLocalizacaoFisica(),
                    ex.getStatus(),
                    ex.getId() // coluna ID (oculta)
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar exemplares: " + e.getMessage());
        }
    }
    
    //Filtra exemplares pelo número de tombamento
    private void filtrarPorTombamento() {
        int valor = (Integer) spnTombamento.getValue();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        try {
            List<Exemplar> exemplares = exemplarController.findAll();

            // Se for 0, carrega todos
            if (valor == 0) {
                carregarExemplares();
                return;
            }

            for (Exemplar ex : exemplares) {
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
            JOptionPane.showMessageDialog(this,
                "Erro ao filtrar exemplares: " + e.getMessage());
        }
   }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSelecionar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JSpinner spnTombamento;
    // End of variables declaration//GEN-END:variables
}


