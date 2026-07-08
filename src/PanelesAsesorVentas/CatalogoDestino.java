/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package PanelesAsesorVentas;

import Conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import cristorey_ef.AsesorVentas;
import cristorey_ef.PaqueteTuristico;
import cristorey_ef.PaqueteTuristicoControlador;
import cristorey_ef.UsuarioControlador;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class CatalogoDestino extends javax.swing.JPanel {

    private UsuarioControlador uc;    
    private final PaqueteTuristicoControlador ptc;
    AsesorVentas asesor = new AsesorVentas("","","", "","", "",0, null);
    
    public CatalogoDestino(UsuarioControlador uc, PaqueteTuristicoControlador ptc) {
        initComponents();
        this.uc = uc;
        this.ptc = ptc;

        tblCatalogo.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionPaquete();
            }
        });

        tblCatalogo.getTableHeader().setBackground(new java.awt.Color(255, 170, 44));
        tblCatalogo.getTableHeader().setForeground(java.awt.Color.WHITE);
        tblCatalogo.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer(){
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
    }
    
    private void buscarTour() {
     String destino = txtDestino.getText().trim();

    if (destino.isEmpty()) {
        cargarTabla();
        return;
    }

    DefaultTableModel modelo = (DefaultTableModel) tblCatalogo.getModel();
    modelo.setRowCount(0);
    int total = 0;

    try (Connection con = ConexionBD.conectar()) {

        if (con == null) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo conectar a la base de datos.",
                    "Error de conexión", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PreparedStatement ps = con.prepareStatement(
            "SELECT codigo_paquete, nombre_paquete, destino, costo FROM PaqueteTuristico "
            + "WHERE nombre_paquete LIKE ? OR destino LIKE ?"
        );
        ps.setString(1, "%" + destino + "%");
        ps.setString(2, "%" + destino + "%");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            modelo.addRow(new Object[]{
                rs.getString("codigo_paquete"),
                rs.getString("nombre_paquete"),
                rs.getString("destino"),
                "S/ " + rs.getDouble("costo")
            });
            total++;
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Error al buscar el destino:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (total == 0) {
        JOptionPane.showMessageDialog(this,
                "No se encontró ningún destino con el nombre ingresado",
                "Destino no encontrado", JOptionPane.WARNING_MESSAGE);
    }

    lblResultados.setText(total + " tour(s) encontrado(s)");
}
        

    private void cargarTabla() {
     DefaultTableModel modelo = (DefaultTableModel) tblCatalogo.getModel();
     modelo.setRowCount(0);
     int total = 0;

    try (Connection con = ConexionBD.conectar()) {

        if (con == null) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo conectar a la base de datos.",
                    "Error de conexión", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(
            "SELECT codigo_paquete, nombre_paquete, destino, costo FROM PaqueteTuristico"
        );

        while (rs.next()) {
            modelo.addRow(new Object[]{
                rs.getString("codigo_paquete"),
                rs.getString("nombre_paquete"),
                rs.getString("destino"),
                "S/ " + rs.getDouble("costo")
            });
            total++;
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Error al cargar el catálogo:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    lblResultados.setText(total + " tour(s) encontrado(s)");
    lblDisponibilidad.setText("Seleccione un paquete para ver su disponibilidad real de asientos.");
    lblDisponibilidad.setForeground(new Color(102, 102, 102));
}
        

    private void seleccionPaquete() {
            int fila = tblCatalogo.getSelectedRow();
    if (fila < 0) {
        return;
    }
    String codigo = (String) tblCatalogo.getModel().getValueAt(fila, 0);

    try (Connection con = ConexionBD.conectar()) {

        if (con == null) {
            lblDisponibilidad.setText("No se pudo conectar a la base de datos.");
            lblDisponibilidad.setForeground(new Color(127, 16, 16));
            return;
        }

        PreparedStatement ps = con.prepareStatement(
            "SELECT nombre_paquete, cupos_maximos, cupos_disponibles FROM PaqueteTuristico WHERE codigo_paquete = ?"
        );
        ps.setString(1, codigo);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String nombre = rs.getString("nombre_paquete");
            int cuposMax = rs.getInt("cupos_maximos");
            int cuposDisp = rs.getInt("cupos_disponibles");

            String dispo = nombre + " Disponibilidad real: "
                    + cuposDisp + " / " + cuposMax + " asientos";

            boolean tieneCupos = cuposDisp > 0;
            if (!tieneCupos) {
                dispo = dispo + "  (SIN CUPOS)";
            }

            lblDisponibilidad.setText(dispo);
            lblDisponibilidad.setForeground(tieneCupos ? new Color(27, 94, 32) : new Color(127, 16, 16));
        } else {
            lblDisponibilidad.setText("No se pudo obtener la disponibilidad del paquete.");
            lblDisponibilidad.setForeground(new Color(127, 16, 16));
        }

    } catch (Exception ex) {
        lblDisponibilidad.setText("Error al consultar disponibilidad: " + ex.getMessage());
        lblDisponibilidad.setForeground(new Color(127, 16, 16));
    }
}
        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelHeader = new javax.swing.JLabel();
        jLabelBuscar = new javax.swing.JLabel();
        txtDestino = new javax.swing.JTextField();
        lblResultados = new javax.swing.JLabel();
        lblDisponibilidad = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCatalogo = new javax.swing.JTable();
        btnBuscar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 249, 236));

        jLabelHeader.setFont(new java.awt.Font("Forte", 0, 24)); // NOI18N
        jLabelHeader.setForeground(new java.awt.Color(80, 50, 22));
        jLabelHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/catalogoDestino.png"))); // NOI18N
        jLabelHeader.setText("Catálogo de destinos");

        jLabelBuscar.setForeground(new java.awt.Color(102, 102, 102));
        jLabelBuscar.setText("Buscar por destino:");

        txtDestino.setToolTipText("Escriba el nombre de un destino…");

        lblResultados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblResultados.setForeground(new java.awt.Color(80, 50, 22));
        lblResultados.setText("0 tour(s) encontrado(s)");

        lblDisponibilidad.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblDisponibilidad.setForeground(new java.awt.Color(102, 102, 102));
        lblDisponibilidad.setText("Seleccione un paquete para ver su disponibilidad real de asientos.");

        tblCatalogo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código", "Nombre", "Descripción", "Precio vigente"
            }
        ));
        tblCatalogo.setRowHeight(28);
        tblCatalogo.setShowHorizontalLines(true);
        tblCatalogo.setShowVerticalLines(true);
        jScrollPane2.setViewportView(tblCatalogo);

        btnBuscar.setBackground(new java.awt.Color(255, 189, 105));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);

        btnActualizar.setBackground(new java.awt.Color(255, 189, 105));
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelBuscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscar))
                            .addComponent(jLabelHeader)
                            .addComponent(lblDisponibilidad)
                            .addComponent(lblResultados)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 669, Short.MAX_VALUE)
                        .addComponent(btnActualizar)))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelHeader)
                    .addComponent(btnActualizar))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelBuscar))
                    .addComponent(btnBuscar))
                .addGap(17, 17, 17)
                .addComponent(lblResultados)
                .addGap(15, 15, 15)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblDisponibilidad)
                .addContainerGap(58, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        buscarTour();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        cargarTabla();
    }//GEN-LAST:event_btnActualizarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JLabel jLabelBuscar;
    private javax.swing.JLabel jLabelHeader;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDisponibilidad;
    private javax.swing.JLabel lblResultados;
    private javax.swing.JTable tblCatalogo;
    private javax.swing.JTextField txtDestino;
    // End of variables declaration//GEN-END:variables
}
