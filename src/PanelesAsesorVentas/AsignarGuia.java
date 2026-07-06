/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package PanelesAsesorVentas;

import cristorey_ef.GuiaTuristico;
import cristorey_ef.PaqueteTuristico;
import cristorey_ef.PaqueteTuristicoControlador;
import cristorey_ef.Usuario;
import cristorey_ef.UsuarioControlador;
import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class AsignarGuia extends javax.swing.JPanel {

    private final UsuarioControlador uc;
    private final PaqueteTuristicoControlador ptc;
    private final SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

    public AsignarGuia(UsuarioControlador uc, PaqueteTuristicoControlador ptc) {
        initComponents();
        this.uc = uc;
        this.ptc = ptc;

        cargarGuias();
        cargarPaquetes();

        actualizarEstadoGuia();
    }

    private void cargarGuias() {
        javax.swing.DefaultComboBoxModel<GuiaTuristico> modelo = new javax.swing.DefaultComboBoxModel<>();
        for (Usuario usuario : uc.getUsuario()) {
            if (usuario instanceof GuiaTuristico) {
                modelo.addElement((GuiaTuristico) usuario);
            }
        }
        cbxGuia.setModel(modelo);
        cbxGuia.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof GuiaTuristico) {
                    setText(((GuiaTuristico) value).getNombre());
                }
                return this;
            }
        });
        for (java.awt.event.ActionListener al : cbxGuia.getActionListeners()) {
            cbxGuia.removeActionListener(al);
        }
        cbxGuia.addActionListener(e -> actualizarEstadoGuia());
    }

    private void cargarPaquetes() {
        javax.swing.DefaultComboBoxModel<PaqueteTuristico> modelo = new javax.swing.DefaultComboBoxModel<>();
        for (PaqueteTuristico paq : ptc.getPaquete()) {
            modelo.addElement(paq);
        }
        cbxPaqueteAsignar.setModel(modelo);
        cbxPaqueteAsignar.setRenderer(new DefaultListCellRenderer() {
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
    }

    private void actualizarEstadoGuia() {
        GuiaTuristico guia = (GuiaTuristico) cbxGuia.getSelectedItem();
        if (guia == null) {
            lblEstadoGuia.setText("Seleccione un guía.");
            return;
        }
        if (guia.tieneAsignacion()) {
            lblEstadoGuia.setText("Estado: ya tiene asignado \"" + guia.getRecorrido_asignado() + "\".");
            lblEstadoGuia.setForeground(new Color(27, 94, 32));
        } else if (guia.tieneSolicitudPendiente()) {
            lblEstadoGuia.setText("Estado: solicitó ser asignado a \"" + guia.getRecorrido_solicitado() + "\".");
            lblEstadoGuia.setForeground(new Color(196, 130, 40));
            PaqueteTuristico solicitado = ptc.buscarCodigo(guia.getRecorrido_solicitado());
            if (solicitado != null) {
                cbxPaqueteAsignar.setSelectedItem(solicitado);
            }
        } else {
            lblEstadoGuia.setText("Estado: sin recorrido asignado ni solicitudes pendientes.");
            lblEstadoGuia.setForeground(new Color(102, 102, 102));
        }
    }

    public void cargar() {
        cargarGuias();
        cargarPaquetes();
        actualizarEstadoGuia();
        actualizarNotificacion();
    }
    
    private void actualizarNotificacion() {
        GuiaTuristico pendiente = null;
        for (int i = 0; i < uc.getUsuario().size(); i++) {
            Usuario u = uc.getUsuario().get(i);
            if (u instanceof GuiaTuristico && ((GuiaTuristico) u).tieneSolicitudPendiente()) {
                pendiente = (GuiaTuristico) u;
                break;
            }
        }
        if (pendiente != null) {
            PaqueteTuristico p = ptc.buscarCodigo(pendiente.getRecorrido_solicitado());
            String nombre;
        if (p != null) {
            nombre = p.getNombre_paquete();
        } else {
            nombre = pendiente.getRecorrido_solicitado();
        }
            lblNotificacion.setText( pendiente.getNombre() + " pidió ser asignado a \"" + nombre + "\".");
            lblNotificacion.setForeground(new Color(196, 130, 40));
        } else {
            lblNotificacion.setText("Sin solicitudes por ahora.");
            lblNotificacion.setForeground(new Color(102, 102, 102));
        }
    }
    
    private void registrarHistorial(String mensaje) {
        String hora = formatoHora.format(new java.util.Date());
        txtHistorialSolicitudes.insert("[" + hora + "] " + mensaje + "\n", 0);
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxGuia = new javax.swing.JComboBox<>();
        lblEstadoGuia = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbxPaqueteAsignar = new javax.swing.JComboBox<>();
        btnAsignar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lblNotificacion = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtHistorialSolicitudes = new javax.swing.JTextArea();

        setBackground(new java.awt.Color(255, 249, 236));

        jLabelHeader.setFont(new java.awt.Font("Forte", 0, 24)); // NOI18N
        jLabelHeader.setForeground(new java.awt.Color(80, 50, 22));
        jLabelHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/asignarGuia.png"))); // NOI18N
        jLabelHeader.setText("Asignar guía");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 170, 44), 2));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/usuario.png"))); // NOI18N
        jLabel1.setText("ASIGNACIÓN DE RECORRIDO");

        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Guía turístico");

        lblEstadoGuia.setForeground(new java.awt.Color(102, 102, 102));

        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Paquete turístico");

        btnAsignar.setBackground(new java.awt.Color(255, 189, 105));
        btnAsignar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAsignar.setForeground(new java.awt.Color(255, 255, 255));
        btnAsignar.setText("Asignar guia");
        btnAsignar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAsignar.addActionListener(this::btnAsignarActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEstadoGuia)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(27, 27, 27)
                                .addComponent(cbxGuia, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(8, 8, 8)
                                .addComponent(cbxPaqueteAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(112, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblEstadoGuia)
                .addGap(8, 8, 8)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2))
                    .addComponent(cbxGuia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3))
                    .addComponent(cbxPaqueteAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(btnAsignar)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(80, 50, 22));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/mensajes.png"))); // NOI18N
        jLabel4.setText("Solicitudes de los guías");

        lblNotificacion.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblNotificacion.setForeground(new java.awt.Color(102, 102, 102));
        lblNotificacion.setText("Sin solicitudes por ahora.");

        jScrollPane1.setPreferredSize(new java.awt.Dimension(560, 130));

        txtHistorialSolicitudes.setEditable(false);
        txtHistorialSolicitudes.setLineWrap(true);
        txtHistorialSolicitudes.setRows(6);
        txtHistorialSolicitudes.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtHistorialSolicitudes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNotificacion)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabelHeader)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabelHeader)
                .addGap(14, 14, 14)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNotificacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAsignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarActionPerformed
        // TODO add your handling code here:
        GuiaTuristico guia = (GuiaTuristico) cbxGuia.getSelectedItem();
        PaqueteTuristico paquete = (PaqueteTuristico) cbxPaqueteAsignar.getSelectedItem();

        if (guia == null || paquete == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un guía y un paquete turístico.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (guia.tieneAsignacion()) {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    guia.getNombre() + " ya tiene asignado \"" + guia.getRecorrido_asignado() + "\".\n"
                    + "¿Desea reemplazarlo por \"" + paquete.getNombre_paquete() + "\"?",
                    "Confirmar reasignación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }
        }

        guia.setRecorrido_asignado(paquete.getCodigo_paquete());
        guia.limpiarSolicitud();
        registrarHistorial(guia.getNombre() + " fue asignado a \"" + paquete.getNombre_paquete() + "\".");

        JOptionPane.showMessageDialog(this,
                paquete.getNombre_paquete() + " asignado correctamente a " + guia.getNombre() + ".");
        actualizarEstadoGuia();
    }//GEN-LAST:event_btnAsignarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsignar;
    private javax.swing.JComboBox<cristorey_ef.GuiaTuristico> cbxGuia;
    private javax.swing.JComboBox<cristorey_ef.PaqueteTuristico> cbxPaqueteAsignar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelHeader;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEstadoGuia;
    private javax.swing.JLabel lblNotificacion;
    private javax.swing.JTextArea txtHistorialSolicitudes;
    // End of variables declaration//GEN-END:variables
}
