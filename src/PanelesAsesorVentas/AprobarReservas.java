/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package PanelesAsesorVentas;

import cristorey_ef.PaqueteTuristico;
import cristorey_ef.Promocion;
import cristorey_ef.Reserva;
import cristorey_ef.ReservaControlador;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AprobarReservas extends javax.swing.JPanel {

    private final ReservaControlador rc;
    private Reserva seleccionada;

    public AprobarReservas(ReservaControlador rc) {
        initComponents();
        this.rc = rc;
        
        tblPendientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionReserva();
            }
        });

        tblPendientes.getTableHeader().setBackground(new java.awt.Color(255, 170, 44));
        tblPendientes.getTableHeader().setForeground(java.awt.Color.WHITE);
        tblPendientes.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer(){
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
    }

    public void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblPendientes.getModel();
        modelo.setRowCount(0);

        ArrayList<Reserva> listaActivas = rc.reservasActivas();
        for (int i = 0; i < listaActivas.size(); i++) {
            Reserva r = listaActivas.get(i);
            modelo.addRow(new Object[]{
                r.getCodigo_reserva(),
                r.getPasajero().unificarDatos(),
                r.getPaquete().getNombre_paquete(),
                "S/" + r.getPrecio_final(),
                r.getEstado_aprobacion()
            });
        }
        lblPendientes.setText(modelo.getRowCount() + " reserva(s) activa(s)");
        limpiar();
    }

    private void limpiar() {
        seleccionada = null;
        lblPromocion.setText("Seleccione una reserva para evaluar promociones vigentes.");
        lblPromocion.setForeground(new Color(102, 102, 102));
        lblPrecioFinal.setText("Precio final estimado: --");
        btnAprobar.setEnabled(false);
        btnRechazarReserva.setEnabled(false);
        btnCancelarInscripcion.setEnabled(false);
    }

    private void seleccionReserva() {
        int fila = tblPendientes.getSelectedRow();
        if (fila < 0) {
            limpiar();
            return;
        }

        String codigo = (String) tblPendientes.getModel().getValueAt(fila, 0);
        seleccionada = rc.buscarReserva(codigo);
        if (seleccionada == null) {
            limpiar();
            return;
        }

        PaqueteTuristico paquete = seleccionada.getPaquete();
        Promocion promocion = paquete.promocionVigenteAplicable(seleccionada.getPasajero());
        double descuento;
        if (promocion != null) {
            descuento = promocion.getPorcentaje_descuento();
        } else {
            descuento = 0;
        }
        double precioFinal = rc.calcularPrecioFinal(paquete.getCosto(), descuento);

        if (promocion != null) {
            lblPromocion.setText("Promoción vigente aplicable: \"" + promocion.getNombre() + "\" (-"
                    + promocion.getPorcentaje_descuento() + "%) · " + promocion.describirCriterio());
            lblPromocion.setForeground(new Color(27, 94, 32));
        } else {
            lblPromocion.setText("El paquete no tiene promociones vigentes aplicables a este pasajero.");
            lblPromocion.setForeground(new Color(102, 102, 102));
        }

        lblPrecioFinal.setText("Precio final estimado: S/"+precioFinal+"(precio normal: S/"+paquete.getCosto()+")");

        boolean esPendiente = seleccionada.getEstado_aprobacion().equalsIgnoreCase("Pendiente");
        boolean esAprobada = seleccionada.getEstado_aprobacion().equalsIgnoreCase("Aprobada");

        btnAprobar.setEnabled(esPendiente);
        btnRechazarReserva.setEnabled(esPendiente);
        btnCancelarInscripcion.setEnabled(esAprobada);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jProgressBar1 = new javax.swing.JProgressBar();
        jLabelHeader = new javax.swing.JLabel();
        lblPendientes = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblPromocion = new javax.swing.JLabel();
        lblPrecioFinal = new javax.swing.JLabel();
        btnAprobar = new javax.swing.JButton();
        btnRechazarReserva = new javax.swing.JButton();
        btnCancelarInscripcion = new javax.swing.JButton();
        btnActualizar2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPendientes = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 249, 236));

        jLabelHeader.setFont(new java.awt.Font("Forte", 0, 24)); // NOI18N
        jLabelHeader.setForeground(new java.awt.Color(80, 50, 22));
        jLabelHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/aprobarReserva.png"))); // NOI18N
        jLabelHeader.setText("Reservas por aprobar");

        lblPendientes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPendientes.setForeground(new java.awt.Color(80, 50, 22));
        lblPendientes.setText("0 reserva(s) pendiente(s) de aprobación");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 170, 44), 2));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/porcentaje-de-insignias.png"))); // NOI18N
        jLabel1.setText("VALIDACIÓN Y PROMOCIONES");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 241, 0, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        lblPromocion.setForeground(new java.awt.Color(102, 102, 102));
        lblPromocion.setText("Seleccione una reserva para evaluar promociones vigentes.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 221, 0, 0);
        jPanel1.add(lblPromocion, gridBagConstraints);

        lblPrecioFinal.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblPrecioFinal.setForeground(new java.awt.Color(80, 50, 22));
        lblPrecioFinal.setText("Precio final estimado: --");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 221, 0, 0);
        jPanel1.add(lblPrecioFinal, gridBagConstraints);

        btnAprobar.setBackground(new java.awt.Color(76, 175, 80));
        btnAprobar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAprobar.setForeground(new java.awt.Color(255, 255, 255));
        btnAprobar.setText("Aprobar reserva");
        btnAprobar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAprobar.setEnabled(false);
        btnAprobar.addActionListener(this::btnAprobarActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 129, 0, 0);
        jPanel1.add(btnAprobar, gridBagConstraints);

        btnRechazarReserva.setBackground(new java.awt.Color(196, 90, 60));
        btnRechazarReserva.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRechazarReserva.setForeground(new java.awt.Color(255, 255, 255));
        btnRechazarReserva.setText("Rechazar reserva");
        btnRechazarReserva.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRechazarReserva.setEnabled(false);
        btnRechazarReserva.addActionListener(this::btnRechazarReservaActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 29, 17, 0);
        jPanel1.add(btnRechazarReserva, gridBagConstraints);

        btnCancelarInscripcion.setBackground(new java.awt.Color(196, 90, 60));
        btnCancelarInscripcion.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnCancelarInscripcion.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelarInscripcion.setText("Cancelar inscripción");
        btnCancelarInscripcion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelarInscripcion.addActionListener(this::btnCancelarInscripcionActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 27, 17, 115);
        jPanel1.add(btnCancelarInscripcion, gridBagConstraints);

        btnActualizar2.setBackground(new java.awt.Color(255, 189, 105));
        btnActualizar2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnActualizar2.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar2.setText("Actualizar");
        btnActualizar2.setBorderPainted(false);
        btnActualizar2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizar2.addActionListener(this::btnActualizar2ActionPerformed);

        tblPendientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Código reserva", "Pasajero", "Paquete", "Precio actual", "Estado"
            }
        ));
        tblPendientes.setRowHeight(28);
        tblPendientes.setShowHorizontalLines(true);
        tblPendientes.setShowVerticalLines(true);
        jScrollPane2.setViewportView(tblPendientes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPendientes)
                            .addComponent(jLabelHeader))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActualizar2)))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelHeader)
                    .addComponent(btnActualizar2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPendientes)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizar2ActionPerformed
        // TODO add your handling code here:
        cargarTabla();
        limpiar();
    }//GEN-LAST:event_btnActualizar2ActionPerformed

    private void btnAprobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAprobarActionPerformed
        // TODO add your handling code here:
        if (seleccionada == null) {
            return;
        }
        boolean aprobada = rc.aprobarReserva(seleccionada.getCodigo_reserva());
        if (aprobada) {
            JOptionPane.showMessageDialog(this,
                    "Reserva " + seleccionada.getCodigo_reserva() + " aprobada.\n"
                    + "Precio final cobrado: S/ " + seleccionada.getPrecio_final());
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo aprobar la reserva.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        cargarTabla();
        seleccionReserva();
    }//GEN-LAST:event_btnAprobarActionPerformed

    private void btnRechazarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRechazarReservaActionPerformed
        // TODO add your handling code here:
        if (seleccionada == null) {
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Confirma rechazar la reserva " + seleccionada.getCodigo_reserva()
                + " de " + seleccionada.getPasajero().unificarDatos() + "\nSe liberará el cupo.",
                "Confirmar rechazo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        boolean rechazada = rc.rechazarReserva(seleccionada.getCodigo_reserva());
        if (rechazada) {
            JOptionPane.showMessageDialog(this, "Reserva rechazada. El cupo quedó liberado.");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo rechazar la reserva.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        cargarTabla();
        seleccionReserva();
    }//GEN-LAST:event_btnRechazarReservaActionPerformed

    private void btnCancelarInscripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarInscripcionActionPerformed
        // TODO add your handling code here:
        if (seleccionada == null) {
           return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Confirma cancelar la reserva ya aprobada " + seleccionada.getCodigo_reserva()
            + " de " + seleccionada.getPasajero().unificarDatos() + "?\nSe liberará el cupo",
            "Confirmar cancelación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        boolean cancelada = rc.cancelarReserva(seleccionada.getCodigo_reserva());
        if (cancelada) {
            JOptionPane.showMessageDialog(this, "Reserva cancelada correctamente. El cupo quedó liberado.");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo cancelar la reserva.",
                   "Error", JOptionPane.ERROR_MESSAGE);
        }
        cargarTabla();
        seleccionReserva();
    }//GEN-LAST:event_btnCancelarInscripcionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar2;
    private javax.swing.JButton btnAprobar;
    private javax.swing.JButton btnCancelarInscripcion;
    private javax.swing.JButton btnRechazarReserva;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelHeader;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblPendientes;
    private javax.swing.JLabel lblPrecioFinal;
    private javax.swing.JLabel lblPromocion;
    private javax.swing.JTable tblPendientes;
    // End of variables declaration//GEN-END:variables
}
