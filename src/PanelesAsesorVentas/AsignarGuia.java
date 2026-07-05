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


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

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
        setLayout(new java.awt.GridBagLayout());

        jLabelHeader.setFont(new java.awt.Font("Forte", 0, 24)); // NOI18N
        jLabelHeader.setForeground(new java.awt.Color(80, 50, 22));
        jLabelHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/usuarios-alt.png"))); // NOI18N
        jLabelHeader.setText("Asignar guía");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 30, 14, 0);
        add(jLabelHeader, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 170, 44), 2));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/usuario.png"))); // NOI18N
        jLabel1.setText("ASIGNACIÓN DE RECORRIDO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 20, 0, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Guía turístico");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 20, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 441;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 8, 0, 29);
        jPanel1.add(cbxGuia, gridBagConstraints);

        lblEstadoGuia.setForeground(new java.awt.Color(102, 102, 102));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(lblEstadoGuia, gridBagConstraints);

        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Paquete turístico");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(23, 20, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 441;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 8, 0, 29);
        jPanel1.add(cbxPaqueteAsignar, gridBagConstraints);

        btnAsignar.setBackground(new java.awt.Color(255, 189, 105));
        btnAsignar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAsignar.setForeground(new java.awt.Color(255, 255, 255));
        btnAsignar.setText("Asignar guia");
        btnAsignar.addActionListener(this::btnAsignarActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.ipadx = 496;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 20, 8, 29);
        jPanel1.add(btnAsignar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 32, 20, 30);
        add(jPanel1, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(80, 50, 22));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Mensajes.png"))); // NOI18N
        jLabel4.setText("Solicitudes de los guías");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 32, 8, 0);
        add(jLabel4, gridBagConstraints);

        lblNotificacion.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblNotificacion.setForeground(new java.awt.Color(102, 102, 102));
        lblNotificacion.setText("Sin solicitudes por ahora.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 32, 6, 30);
        add(lblNotificacion, gridBagConstraints);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(560, 130));

        txtHistorialSolicitudes.setEditable(false);
        txtHistorialSolicitudes.setLineWrap(true);
        txtHistorialSolicitudes.setRows(6);
        txtHistorialSolicitudes.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtHistorialSolicitudes);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 32, 20, 30);
        add(jScrollPane1, gridBagConstraints);
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
