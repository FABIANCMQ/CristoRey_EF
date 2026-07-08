/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package PanelesGerente;

import Conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import cristorey_ef.PaqueteTuristico;
import cristorey_ef.PaqueteTuristicoControlador;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author coolg
 */
public class Tarifas extends javax.swing.JPanel {
    
    /**
     * Creates new form Tarifas
     */
    
    private final PaqueteTuristicoControlador ptc;
    private ArrayList<PaqueteTuristico> paquetes;
    public Tarifas(PaqueteTuristicoControlador ptc) {
        initComponents();
        this.ptc = ptc;
        this.paquetes = ptc.getPaquete();
        
        tblPaquetes.getTableHeader().setBackground(new java.awt.Color(255, 170, 44));
        tblPaquetes.getTableHeader().setForeground(java.awt.Color.WHITE);
        tblPaquetes.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer(){
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                javax.swing.JLabel c = (javax.swing.JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                c.setBackground(table.getTableHeader().getBackground());
                c.setForeground(table.getTableHeader().getForeground());
                c.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                c.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, java.awt.Color.LIGHT_GRAY));

                return c;
            }
        });
        cargarTabla();
        cargarPaquetes();
    }
    
    private void cargarTabla() {
            DefaultTableModel modelo = (DefaultTableModel) tblPaquetes.getModel();
    modelo.setRowCount(0);

    try (Connection con = ConexionBD.conectar()) {

        if (con == null) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo conectar a la base de datos.",
                    "Error de conexión", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(
            "SELECT codigo_paquete, nombre_paquete, horario, cupos_maximos, costo FROM PaqueteTuristico"
        );

        while (rs.next()) {
            modelo.addRow(new Object[]{
                rs.getString("codigo_paquete"),
                rs.getString("nombre_paquete"),
                rs.getString("horario"),
                rs.getInt("cupos_maximos"),
                rs.getDouble("costo")
            });
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Error al cargar las tarifas:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
        
       
    
    private void cargarPaquetes() {
            javax.swing.DefaultComboBoxModel<PaqueteTuristico> modelo = new javax.swing.DefaultComboBoxModel<>();

    try (Connection con = ConexionBD.conectar()) {

        if (con == null) {
            return;
        }

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(
            "SELECT codigo_paquete, nombre_paquete, destino, costo, horario, cupos_maximos, cupos_disponibles "
            + "FROM PaqueteTuristico"
        );

        while (rs.next()) {
            PaqueteTuristico paq = new PaqueteTuristico(
                rs.getString("nombre_paquete"),
                rs.getString("codigo_paquete"),
                rs.getString("destino"),
                rs.getDouble("costo"),
                rs.getString("horario"),
                rs.getInt("cupos_maximos"),
                rs.getInt("cupos_disponibles")
            );
            modelo.addElement(paq);
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Error al cargar los paquetes:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    cbxPaquete.setModel(modelo);
    cbxPaquete.setRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof PaqueteTuristico) {
                PaqueteTuristico paq = (PaqueteTuristico) value;
                setText(paq.getCodigo_paquete() + " - " + paq.getNombre_paquete()
                        + " (" + paq.getHorario() + ")");
            }
            return this;
        }
    });

    mostrarCostoActual();
}
        
      
    private void mostrarCostoActual() {
            PaqueteTuristico paquete = (PaqueteTuristico) cbxPaquete.getSelectedItem();
    if (paquete == null) {
        lblCostoActual.setText("S/---");
        return;
    }
    lblCostoActual.setText(String.format("S/%.2f", paquete.getCosto()));
}
        

    private void aplicarTarifa() {
            PaqueteTuristico paquete = (PaqueteTuristico) cbxPaquete.getSelectedItem();
    double nuevoPrecio;

    if (paquete == null) {
        JOptionPane.showMessageDialog(this, "Seleccione un paquete turístico.",
                "Dato incompleto", JOptionPane.WARNING_MESSAGE);
        return;
    }
    try {
        nuevoPrecio = Double.parseDouble(txtPrecioNuevo.getText().trim());
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Ingrese un precio numérico válido",
                "Dato inválido", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try (Connection con = ConexionBD.conectar()) {

        if (con == null) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo conectar a la base de datos.",
                    "Error de conexión", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PreparedStatement ps = con.prepareStatement(
            "UPDATE PaqueteTuristico SET costo = ? WHERE codigo_paquete = ?"
        );
        ps.setDouble(1, nuevoPrecio);
        ps.setString(2, paquete.getCodigo_paquete());
        ps.executeUpdate();

        paquete.setCosto(nuevoPrecio);

        JOptionPane.showMessageDialog(this, "Tarifa actualizada. Nuevo precio: S/" + paquete.getCosto());

        mostrarCostoActual();
        cargarTabla();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Error al actualizar la tarifa:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
        
    
    /**
     * This method is called from within the constructor to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPaquetes = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();
        pnlPrecionuevo = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lblCostoActual = new javax.swing.JLabel();
        btnAplicarTarifa = new javax.swing.JButton();
        cbxPaquete = new javax.swing.JComboBox<>();
        lblPorcentaje = new javax.swing.JLabel();
        txtPrecioNuevo = new javax.swing.JTextField();

        setBackground(new java.awt.Color(252, 242, 226));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel17.setFont(new java.awt.Font("Forte", 0, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(80, 50, 22));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/tarifas.png"))); // NOI18N
        jLabel17.setText("Tarifas por temporada");

        tblPaquetes.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblPaquetes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Código", "Paquete Turístico", "Horario", "Cupos Máx.", "Precio"
            }
        ));
        tblPaquetes.setMaximumSize(new java.awt.Dimension(30, 350));
        tblPaquetes.setMinimumSize(new java.awt.Dimension(30, 350));
        tblPaquetes.setPreferredSize(new java.awt.Dimension(30, 350));
        tblPaquetes.setRowHeight(28);
        tblPaquetes.setShowHorizontalLines(true);
        tblPaquetes.setShowVerticalLines(true);
        jScrollPane2.setViewportView(tblPaquetes);

        btnActualizar.setBackground(new java.awt.Color(255, 189, 105));
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.setBorderPainted(false);
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        pnlPrecionuevo.setBackground(new java.awt.Color(255, 255, 255));
        pnlPrecionuevo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 170, 44), 2));
        pnlPrecionuevo.setLayout(new java.awt.GridBagLayout());

        jLabel18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/porcentaje-de-insignias.png"))); // NOI18N
        jLabel18.setText("NUEVA TARIFA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 77, 0, 0);
        pnlPrecionuevo.add(jLabel18, gridBagConstraints);

        jLabel19.setForeground(new java.awt.Color(102, 102, 102));
        jLabel19.setText("Paquete turístico");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 81, 0, 0);
        pnlPrecionuevo.add(jLabel19, gridBagConstraints);

        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setText("Precio actual:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 56, 0, 0);
        pnlPrecionuevo.add(jLabel20, gridBagConstraints);

        lblCostoActual.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblCostoActual.setForeground(new java.awt.Color(80, 50, 22));
        lblCostoActual.setText("S/---");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 12, 0, 159);
        pnlPrecionuevo.add(lblCostoActual, gridBagConstraints);

        btnAplicarTarifa.setBackground(new java.awt.Color(255, 189, 105));
        btnAplicarTarifa.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnAplicarTarifa.setForeground(new java.awt.Color(255, 255, 255));
        btnAplicarTarifa.setText("Aplicar nueva tarifa");
        btnAplicarTarifa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAplicarTarifa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarTarifaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.ipadx = 323;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 147, 26, 159);
        pnlPrecionuevo.add(btnAplicarTarifa, gridBagConstraints);

        cbxPaquete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxPaqueteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        pnlPrecionuevo.add(cbxPaquete, gridBagConstraints);

        lblPorcentaje.setForeground(new java.awt.Color(102, 102, 102));
        lblPorcentaje.setText("Precio nuevo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 81, 0, 0);
        pnlPrecionuevo.add(lblPorcentaje, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 12, 0, 0);
        pnlPrecionuevo.add(txtPrecioNuevo, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlPrecionuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(pnlPrecionuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        cargarTabla();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnAplicarTarifaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarTarifaActionPerformed
        // TODO add your handling code here:
        aplicarTarifa();
    }//GEN-LAST:event_btnAplicarTarifaActionPerformed

    private void cbxPaqueteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxPaqueteActionPerformed
        // TODO add your handling code here:
        mostrarCostoActual();
    }//GEN-LAST:event_cbxPaqueteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAplicarTarifa;
    private javax.swing.JComboBox<PaqueteTuristico> cbxPaquete;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCostoActual;
    private javax.swing.JLabel lblPorcentaje;
    private javax.swing.JPanel pnlPrecionuevo;
    private javax.swing.JTable tblPaquetes;
    private javax.swing.JTextField txtPrecioNuevo;
    // End of variables declaration//GEN-END:variables

}
