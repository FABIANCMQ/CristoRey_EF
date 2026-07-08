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
import cristorey_ef.Usuario;
import cristorey_ef.UsuarioControlador;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author coolg
 */
public class Empleados extends javax.swing.JPanel {

    /**
     * Creates new form Empleados
     */
    
    
    private final UsuarioControlador uc;
    public Empleados(UsuarioControlador uc) {
        this.uc = uc;
        initComponents();
        tblEmpleados.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionUsuario();
            }
        });
        tblEmpleados.getTableHeader().setBackground(new java.awt.Color(255, 170, 44));
        tblEmpleados.getTableHeader().setForeground(java.awt.Color.WHITE);
        tblEmpleados.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer(){
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
            DefaultTableModel modelo = (DefaultTableModel) tblEmpleados.getModel();
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
            "SELECT codigo_usuario, nombre, correo, cargo, sueldo FROM usuario"
        );

        while (rs.next()) {
            modelo.addRow(new Object[]{
                rs.getString("codigo_usuario"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getString("cargo"),
                rs.getDouble("sueldo")
            });
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Error al cargar los empleados:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
        
      
    private void seleccionUsuario() {
            int fila = tblEmpleados.getSelectedRow();
    if (fila < 0) {
        btnGuardarSueldo.setEnabled(false);
        return;
    }

     DefaultTableModel modelo = (DefaultTableModel) tblEmpleados.getModel();
     String nombre = modelo.getValueAt(fila, 1).toString();
     String codigo = modelo.getValueAt(fila, 0).toString();

     lblNombre.setText(nombre);
     lblCodigo.setText(codigo);
     txtSueldo.setText("");
     btnGuardarSueldo.setEnabled(true);
        
        
    }
    private void guardarSueldo() {
            int fila = tblEmpleados.getSelectedRow();
    if (fila < 0) {
        return;
    }

    DefaultTableModel modelo = (DefaultTableModel) tblEmpleados.getModel();
    String codigo = modelo.getValueAt(fila, 0).toString();
    double sueldo;

    try {
        sueldo = Double.parseDouble(txtSueldo.getText().trim());
        if (sueldo < 0) {
            throw new NumberFormatException();
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Ingrese un sueldo numérico válido.",
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
            "UPDATE usuario SET sueldo = ? WHERE codigo_usuario = ?"
        );
        ps.setDouble(1, sueldo);
        ps.setString(2, codigo);
        ps.executeUpdate();

        JOptionPane.showMessageDialog(this, "Sueldo actualizado correctamente.");

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Error al actualizar el sueldo:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    actualizar();
}
        
    private void actualizar(){
        cargarTabla();
        lblNombre.setText("–--");
        lblCodigo.setText("–--");
        txtSueldo.setText(" ");
        btnGuardarSueldo.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel17 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmpleados = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnGuardarSueldo = new javax.swing.JButton();
        txtSueldo = new javax.swing.JTextField();
        btnActualizar = new javax.swing.JButton();

        setBackground(new java.awt.Color(252, 242, 226));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel17.setFont(new java.awt.Font("Forte", 0, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(80, 50, 22));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empleados.png"))); // NOI18N
        jLabel17.setText("Empleados");

        tblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Código", "Nombre", "Correo", "Cargo", "Sueldo (S/)"
            }
        ));
        tblEmpleados.setRowHeight(28);
        tblEmpleados.setShowHorizontalLines(true);
        tblEmpleados.setShowVerticalLines(true);
        jScrollPane1.setViewportView(tblEmpleados);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 164, 61)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Nombre:");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNombre.setText("---");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Codigo:");

        lblCodigo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCodigo.setText("---");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Nuevo sueldo (S/):");

        btnGuardarSueldo.setBackground(new java.awt.Color(255, 189, 105));
        btnGuardarSueldo.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarSueldo.setText("Guardar sueldo");
        btnGuardarSueldo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarSueldo.setEnabled(false);
        btnGuardarSueldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarSueldoActionPerformed(evt);
            }
        });

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                            .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardarSueldo))
                    .addComponent(txtSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblNombre))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lblCodigo)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnGuardarSueldo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActualizar)))
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(btnActualizar))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarSueldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarSueldoActionPerformed
        // TODO add your handling code here:
        guardarSueldo();
    }//GEN-LAST:event_btnGuardarSueldoActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        actualizar();
    }//GEN-LAST:event_btnActualizarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnGuardarSueldo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTable tblEmpleados;
    private javax.swing.JTextField txtSueldo;
    // End of variables declaration//GEN-END:variables

}
