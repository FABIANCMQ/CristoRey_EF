/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package PanelesAdministrador;

import Conexion.ConexionBD;
import cristorey_ef.UsuarioControlador;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class GestionarAccesos extends javax.swing.JPanel {

    /**
     * Creates new form GestionarAccesos
     */
    private UsuarioControlador uc;
    public GestionarAccesos(UsuarioControlador uc) {
        initComponents();

        this.uc = uc;

        tblUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionUsuario();
            }
        });

        tblUsuarios.getTableHeader().setBackground(new java.awt.Color(255, 170, 44));
        tblUsuarios.getTableHeader().setForeground(java.awt.Color.WHITE);
        tblUsuarios.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                javax.swing.JLabel c = (javax.swing.JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                c.setBackground(table.getTableHeader().getBackground());
                c.setForeground(table.getTableHeader().getForeground());
                c.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                c.setBorder(javax.swing.BorderFactory.createMatteBorder(
                        0, 0, 1, 1, java.awt.Color.LIGHT_GRAY));

                return c;
            }
        });

        cargarTabla();
    }

    private void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblUsuarios.getModel();
        modelo.setRowCount(0);

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                JOptionPane.showMessageDialog(this,
                        "No se pudo conectar a la base de datos.",
                        "Error de conexión",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(
                         "SELECT codigo_usuario, nombre, correo, cargo, bloqueado "
                         + "FROM usuario "
                         + "ORDER BY codigo_usuario"
                 )) {

                while (rs.next()) {

                    String codigo = rs.getString("codigo_usuario");
                    String nombre = rs.getString("nombre");
                    String correo = rs.getString("correo");
                    String cargo = rs.getString("cargo");
                    boolean bloqueado = rs.getBoolean("bloqueado");

                    String estado;

                    if (bloqueado) {
                        estado = "Bloqueado";
                    } else {
                        estado = "Activo";
                    }

                    modelo.addRow(new Object[]{
                        codigo,
                        nombre,
                        correo,
                        cargo,
                        estado
                    });
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar usuarios desde la base de datos:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private void seleccionUsuario() {
        int fila = tblUsuarios.getSelectedRow();

        if (fila < 0) {
            btnBloquear.setEnabled(false);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblUsuarios.getModel();

        String codigo = modelo.getValueAt(fila, 0).toString();
        String nombre = modelo.getValueAt(fila, 1).toString();
        String cargo = modelo.getValueAt(fila, 3).toString();
        String estado = modelo.getValueAt(fila, 4).toString();

        lblUsuario.setText(nombre);
        lblCargo.setText(cargo);

        if (esUsuarioProtegido(codigo, cargo)) {
            lblEstado.setText("Protegido");
            lblEstado.setForeground(new Color(100, 100, 100));
            btnBloquear.setText("No disponible");
            btnBloquear.setEnabled(false);
            return;
        }

        if (estado.equals("Bloqueado")) {
            lblEstado.setText("Bloqueado");
            lblEstado.setForeground(new Color(180, 30, 30));
            btnBloquear.setText("Desbloquear");
            btnBloquear.setBackground(new Color(46, 125, 50));
        } else {
            lblEstado.setText("Activo");
            lblEstado.setForeground(new Color(27, 94, 32));
            btnBloquear.setText("Bloquear");
            btnBloquear.setBackground(new Color(180, 30, 30));
        }

        btnBloquear.setEnabled(true);
    }
    private boolean esUsuarioProtegido(String codigo, String cargo) {

        if (codigo == null || cargo == null) {
            return false;
        }

        String codigoMayus = codigo.toUpperCase();
        String cargoMayus = cargo.toUpperCase();

        return codigoMayus.startsWith("ADM")
                || codigoMayus.startsWith("GER")
                || cargoMayus.equals("ADMINISTRADOR")
                || cargoMayus.equals("GERENTE");
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
        btnActualizar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblCargo = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        btnBloquear = new javax.swing.JButton();

        setBackground(new java.awt.Color(252, 242, 226));

        jLabel17.setFont(new java.awt.Font("Forte", 0, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(80, 50, 22));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/gestionarAccesos.png"))); // NOI18N
        jLabel17.setText("Gestión de accesos");

        btnActualizar.setBackground(new java.awt.Color(255, 189, 105));
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.setBorderPainted(false);
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        tblUsuarios.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Código", "Nombre", "Correo", "Cargo", "Estado"
            }
        ));
        tblUsuarios.setRowHeight(28);
        tblUsuarios.setShowHorizontalLines(true);
        tblUsuarios.setShowVerticalLines(true);
        jScrollPane2.setViewportView(tblUsuarios);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 164, 61)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Usuario:");

        lblUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblUsuario.setText("---");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Cargo:");

        lblCargo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCargo.setText("---");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Estado:");

        lblEstado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblEstado.setText("---");

        btnBloquear.setBackground(new java.awt.Color(180, 30, 30));
        btnBloquear.setForeground(new java.awt.Color(255, 255, 255));
        btnBloquear.setText("Bloquear");
        btnBloquear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBloquear.setEnabled(false);
        btnBloquear.addActionListener(this::btnBloquearActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblCargo, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                    .addComponent(lblUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBloquear)
                .addGap(33, 33, 33))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblCargo)
                    .addComponent(btnBloquear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblEstado))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActualizar)))
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(btnActualizar))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:\
        cargarTabla();

        lblUsuario.setText("---");
        lblCargo.setText("---");
        lblEstado.setText("---");
        lblEstado.setForeground(Color.GRAY);
        btnBloquear.setText("Bloquear");
        btnBloquear.setBackground(new Color(180, 30, 30));
        btnBloquear.setEnabled(false);
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnBloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBloquearActionPerformed
        // TODO add your handling code here:
        int fila = tblUsuarios.getSelectedRow();

        if (fila == -1) {
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblUsuarios.getModel();

        String codigo = modelo.getValueAt(fila, 0).toString();
        String nombre = modelo.getValueAt(fila, 1).toString();
        String cargo = modelo.getValueAt(fila, 3).toString();
        String estadoActual = modelo.getValueAt(fila, 4).toString();

        if (esUsuarioProtegido(codigo, cargo)) {
            JOptionPane.showMessageDialog(this,
                    "Este usuario está protegido y no puede ser bloqueado.",
                    "Acción no permitida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean nuevoEstadoBloqueado = !estadoActual.equals("Bloqueado");

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                JOptionPane.showMessageDialog(this,
                        "No se pudo conectar a la base de datos.",
                        "Error de conexión",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE usuario SET bloqueado = ? WHERE codigo_usuario = ?"
            )) {

                ps.setBoolean(1, nuevoEstadoBloqueado);
                ps.setString(2, codigo);
                ps.executeUpdate();
            }

            if (nuevoEstadoBloqueado) {
                JOptionPane.showMessageDialog(this,
                        nombre + " fue bloqueado correctamente.",
                        "Acceso bloqueado",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        nombre + " fue desbloqueado correctamente.",
                        "Acceso restaurado",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar el acceso del usuario:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        cargarTabla();

        if (fila < tblUsuarios.getRowCount()) {
            tblUsuarios.setRowSelectionInterval(fila, fila);
            seleccionUsuario();
        } 
    }//GEN-LAST:event_btnBloquearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBloquear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCargo;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblUsuarios;
    // End of variables declaration//GEN-END:variables
}
