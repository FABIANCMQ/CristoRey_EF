/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package PanelesGenerales;
import Conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import cristorey_ef.Pasajero;
import cristorey_ef.PasajeroControlador;
import cristorey_ef.Reserva;
import cristorey_ef.ReservaControlador;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class Pasajeros extends javax.swing.JPanel {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    /**
     * Creates new form Pasajeros
     */
    private PasajeroControlador pc;
    private ReservaControlador rc;

    public Pasajeros(PasajeroControlador pc, ReservaControlador rc) {
        initComponents();
        this.pc = pc;
        this.rc = rc;
        lblFecha.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy")));
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
        cargarTabla();
    }
    
    private void cargarTabla() {
        
     DefaultTableModel modelo = (DefaultTableModel) tblPasajeros.getModel();
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
            "SELECT p.nombre, p.ap_paterno, p.ap_materno, p.nro_doc, p.telefono, p.correo, "
            + "r.fecha_reserva, pt.nombre_paquete "
            + "FROM pasajero p "
            + "LEFT JOIN Reserva r ON p.nro_doc = r.nro_doc AND r.estado = 'Activa' "
            + "LEFT JOIN PaqueteTuristico pt ON r.codigo_paquete = pt.codigo_paquete "
            + "ORDER BY p.nro_doc"
        );

        while (rs.next()) {
            String nombre = rs.getString("nombre");
            String apPaterno = rs.getString("ap_paterno");
            String apMaterno = rs.getString("ap_materno");
            String nroDoc = rs.getString("nro_doc");
            String telefono = rs.getString("telefono");
            String correo = rs.getString("correo");

            java.sql.Date fechaSql = rs.getDate("fecha_reserva");
            String fecha = (fechaSql != null) ? fechaSql.toLocalDate().format(FORMATO_FECHA) : "-";

            String paquete = rs.getString("nombre_paquete");
            if (paquete == null) {
                paquete = "Sin paquete asignado";
            }

            modelo.addRow(new Object[]{
                nombre, apPaterno, apMaterno, nroDoc, telefono, correo, fecha, paquete
            });
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Error al cargar los pasajeros desde la base de datos:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
    private void agregarFilaPasajero(DefaultTableModel modelo, Pasajero p) {
        ArrayList<Reserva> reservas = rc.buscarReservasActivasPorDocumento( p.getDocumento().getNro_doc());
        for (int i = 0; i < reservas.size(); i++) { 
            Reserva r = reservas.get(i);
            
            if (!r.getEstado_aprobacion().equalsIgnoreCase("Aprobada")) {
                continue;
            }
            
            modelo.addRow(new Object[]{
                p.getNombre(),
                p.getAp_paterno(),
                p.getAp_materno(),
                p.getDocumento().getNro_doc(),
                p.getTelefono(),
                p.getCorreo(),
                r.getFecha_reserva().format(FORMATO_FECHA),
                r.getPaquete().getNombre_paquete()
            });
        }
    }
    
    private void buscarPasajero() {
    String dato = txtDato.getText().trim();

    if (dato.isEmpty()) {
        cargarTabla();
        return;
    }

    DefaultTableModel modelo = (DefaultTableModel) tblPasajeros.getModel();
    modelo.setRowCount(0);

    try (Connection con = ConexionBD.conectar()) {

        if (con == null) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo conectar a la base de datos.",
                    "Error de conexión", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PreparedStatement ps = con.prepareStatement(
            "SELECT p.nombre, p.ap_paterno, p.ap_materno, p.nro_doc, p.telefono, p.correo, "
            + "r.fecha_reserva, pt.nombre_paquete "
            + "FROM pasajero p "
            + "LEFT JOIN Reserva r ON p.nro_doc = r.nro_doc AND r.estado = 'Activa' "
            + "LEFT JOIN PaqueteTuristico pt ON r.codigo_paquete = pt.codigo_paquete "
            + "WHERE p.nombre LIKE ? OR p.nro_doc LIKE ? "
            + "ORDER BY p.nro_doc"
        );
        ps.setString(1, "%" + dato + "%");
        ps.setString(2, "%" + dato + "%");

        ResultSet rs = ps.executeQuery();
        boolean encontrado = false;

        while (rs.next()) {
            encontrado = true;
            String nombre = rs.getString("nombre");
            String apPaterno = rs.getString("ap_paterno");
            String apMaterno = rs.getString("ap_materno");
            String nroDoc = rs.getString("nro_doc");
            String telefono = rs.getString("telefono");
            String correo = rs.getString("correo");

            java.sql.Date fechaSql = rs.getDate("fecha_reserva");
            String fecha = (fechaSql != null) ? fechaSql.toLocalDate().format(FORMATO_FECHA) : "-";

            String paquete = rs.getString("nombre_paquete");
            if (paquete == null) {
                paquete = "Sin paquete asignado";
            }

            modelo.addRow(new Object[]{
                nombre, apPaterno, apMaterno, nroDoc, telefono, correo, fecha, paquete
            });
        }

        if (!encontrado) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró ningún pasajero con el nombre o documento ingresado.",
                    "Pasajero no encontrado", JOptionPane.WARNING_MESSAGE);
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Error al buscar el pasajero en la base de datos:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
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

        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPasajeros = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();
        lblFecha = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        txtDato = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 249, 236));

        jLabel17.setFont(new java.awt.Font("Forte", 0, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(80, 50, 22));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/PasajerosIcon.png"))); // NOI18N
        jLabel17.setText("Pasajeros");

        tblPasajeros.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblPasajeros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Apellido Paterno", "Apellido Materno", "Documento", "Telefono", "Correo", "Fecha", "Tour"
            }
        ));
        tblPasajeros.setRowHeight(28);
        tblPasajeros.setShowHorizontalLines(true);
        tblPasajeros.setShowVerticalLines(true);
        jScrollPane2.setViewportView(tblPasajeros);

        btnActualizar.setBackground(new java.awt.Color(255, 189, 105));
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        lblFecha.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblFecha.setText("Fecha actual");

        btnBuscar.setBackground(new java.awt.Color(255, 189, 105));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtDato, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(lblFecha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 313, Short.MAX_VALUE)
                                .addComponent(btnActualizar)))
                        .addGap(16, 16, 16))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnActualizar)
                            .addComponent(lblFecha))))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar)
                    .addComponent(txtDato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        cargarTabla();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        buscarPasajero();
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JTable tblPasajeros;
    private javax.swing.JTextField txtDato;
    // End of variables declaration//GEN-END:variables
}
