

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package PanelesGuiaTuristico;

import cristorey_ef.GuiaTuristico;
import cristorey_ef.Pasajero;
import cristorey_ef.PaqueteTuristico;
import cristorey_ef.PaqueteTuristicoControlador;
import cristorey_ef.UsuarioControlador;
import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * @author Usuario
 */
public class RecorridoTuristico extends javax.swing.JPanel {

    private UsuarioControlador uc;
    private final PaqueteTuristicoControlador ptc;
    private final SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
    private GuiaTuristico guia;

    public RecorridoTuristico(UsuarioControlador uc, PaqueteTuristicoControlador ptc) {
        initComponents();
        this.uc = uc;
        this.ptc = ptc;
        tblPasajeros.getTableHeader().setBackground(new java.awt.Color(255, 170, 44));
        tblPasajeros.getTableHeader().setForeground(java.awt.Color.WHITE);
        tblPasajeros.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer(){
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
        
        cargarComboPaquetes();

        PaqueteTuristico paqueteAsignado = buscarPaqueteAsignado();

        if (paqueteAsignado != null) {
            cbxPaqueteGuia.setSelectedItem(paqueteAsignado);
            lblTourAsignado.setText("Tour asignado: " + paqueteAsignado.getNombre_paquete()
                    + " (" + paqueteAsignado.getHorario() + ")");
            registrarNotificacion("Panel iniciado");
        } else {
            lblTourAsignado.setText("Tour asignado: no se encontró \"" + guia.getRecorrido_asignado() + "\"");
            lblTourAsignado.setForeground(new Color(127, 16, 16));
            if (cbxPaqueteGuia.getItemCount() > 0) {
                cbxPaqueteGuia.setSelectedIndex(0);
            }
        }

        cargarTabla((PaqueteTuristico) cbxPaqueteGuia.getSelectedItem());
    }
    
    public void setGuiaActual(GuiaTuristico guia) {
        this.guia = guia;
        cargarTabla((PaqueteTuristico) cbxPaqueteGuia.getSelectedItem());
    }
    
    public void refrescarPantalla(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            registrarNotificacion(mensaje);
            cargarComboPaquetes(); 
            cbxPaqueteGuia.setSelectedItem(cbxPaqueteGuia.getSelectedItem());
            cargarTabla((PaqueteTuristico) cbxPaqueteGuia.getSelectedItem());
        });
    }

    private PaqueteTuristico buscarPaqueteAsignado() {
        String recorrido = guia.getRecorrido_asignado();
        if (recorrido.isEmpty()) {
            return null;
        }
        for (PaqueteTuristico paquete : ptc.getPaquete()) {
            if (paquete.getNombre_paquete().equalsIgnoreCase(recorrido)
                    || paquete.getCodigo_paquete().equalsIgnoreCase(recorrido)) {
                return paquete;
            }
        }
        return null;
    }

    private void cargarComboPaquetes() {
        javax.swing.DefaultComboBoxModel<PaqueteTuristico> modelo = new javax.swing.DefaultComboBoxModel<>();
        
        List<PaqueteTuristico> listaPaquetes = ptc.getPaquete();
        for (int i = 0; i < listaPaquetes.size(); i++) {
            PaqueteTuristico paq = listaPaquetes.get(i);
            modelo.addElement(paq);
        }
        
        cbxPaqueteGuia.setModel(modelo);
        
        cbxPaqueteGuia.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof PaqueteTuristico) {
                    PaqueteTuristico paq = (PaqueteTuristico) value;
                    setText(paq.getCodigo_paquete() + " - " + paq.getNombre_paquete()
                            + " (" + paq.contarPasajeros() + " inscritos)");
                }
                return this;
            }
        });
    }

    private void cargarTabla(PaqueteTuristico paquete) {
        DefaultTableModel modelo = (DefaultTableModel) tblPasajeros.getModel();
        modelo.setRowCount(0);

        if (paquete == null) {
            lblTotalInscritos.setText("Total inscritos: --");
            return;
        }

        List<Pasajero> lista = paquete.getListaPasajeros();

        for (int i = 0; i < lista.size(); i++) {
            Pasajero p = lista.get(i);
            modelo.addRow(new Object[]{
                p.getDocumento().getNro_doc(),
                p.unificarDatos(),
                p.getTelefono()
            });
        }
        lblTotalInscritos.setText("Total inscritos: " + paquete.contarPasajeros()
                + " / " + paquete.getCupos_maximos());
    }

    private void registrarNotificacion(String mensaje) {
        String hora = formatoHora.format(new Date());
        lblNotificacion.setText(mensaje);
        lblNotificacion.setForeground(new Color(27, 94, 32));
        txtHistorialNotificaciones.insert("[" + hora + "] " + mensaje + "\n", 0);
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
        lblTourAsignado = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cbxPaqueteGuia = new javax.swing.JComboBox<>();
        lblTotalInscritos = new javax.swing.JLabel();
        lblNotificacion = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtHistorialNotificaciones = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPasajeros = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 249, 236));
        setLayout(new java.awt.GridBagLayout());

        jLabelHeader.setFont(new java.awt.Font("Forte", 0, 24)); // NOI18N
        jLabelHeader.setForeground(new java.awt.Color(80, 50, 22));
        jLabelHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Pasajeros.png"))); // NOI18N
        jLabelHeader.setText("Recorrido turistico asignado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 30, 0, 0);
        add(jLabelHeader, gridBagConstraints);

        lblTourAsignado.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblTourAsignado.setForeground(new java.awt.Color(27, 94, 32));
        lblTourAsignado.setText("Tour asignado: --");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 32, 0, 0);
        add(lblTourAsignado, gridBagConstraints);

        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Consultar pasajeros del paquete:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 32, 0, 0);
        add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 327;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 2, 0, 0);
        add(cbxPaqueteGuia, gridBagConstraints);

        lblTotalInscritos.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblTotalInscritos.setForeground(new java.awt.Color(80, 50, 22));
        lblTotalInscritos.setText("Total inscritos: --");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 8, 0, 30);
        add(lblTotalInscritos, gridBagConstraints);

        lblNotificacion.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblNotificacion.setForeground(new java.awt.Color(102, 102, 102));
        lblNotificacion.setText("Sin notificaciones por ahora.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 32, 0, 0);
        add(lblNotificacion, gridBagConstraints);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(520, 120));

        txtHistorialNotificaciones.setEditable(false);
        txtHistorialNotificaciones.setLineWrap(true);
        txtHistorialNotificaciones.setRows(6);
        txtHistorialNotificaciones.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txtHistorialNotificaciones);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 676;
        gridBagConstraints.ipady = 130;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 32, 16, 30);
        add(jScrollPane2, gridBagConstraints);

        tblPasajeros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "N Documento", "Nombre completo", "Teléfono"
            }
        ));
        tblPasajeros.setRowHeight(28);
        tblPasajeros.setShowHorizontalLines(true);
        tblPasajeros.setShowVerticalLines(true);
        jScrollPane3.setViewportView(tblPasajeros);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 676;
        gridBagConstraints.ipady = 190;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(18, 32, 0, 30);
        add(jScrollPane3, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<cristorey_ef.PaqueteTuristico> cbxPaqueteGuia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelHeader;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblNotificacion;
    private javax.swing.JLabel lblTotalInscritos;
    private javax.swing.JLabel lblTourAsignado;
    private javax.swing.JTable tblPasajeros;
    private javax.swing.JTextArea txtHistorialNotificaciones;
    // End of variables declaration//GEN-END:variables
}
