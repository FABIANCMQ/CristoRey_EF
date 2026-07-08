/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package PanelesAdministrador;

import Conexion.ConexionBD;
import cristorey_ef.ReservaControlador;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class Estadisticas extends javax.swing.JPanel {

    /**
     * Creates new form Estadisticas
     */
    private ReservaControlador rc;

    public Estadisticas(ReservaControlador rc) {
        initComponents();

        this.rc = rc;

        tblRanking.getTableHeader().setBackground(new java.awt.Color(255, 170, 44));
        tblRanking.getTableHeader().setForeground(java.awt.Color.WHITE);
        tblRanking.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
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

        lblMes.setText(LocalDate.now().format(
                DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy")
        ));

        cargarEstadisticas();
    }

    private void cargarEstadisticas() {
         DefaultTableModel modelo = (DefaultTableModel) tblRanking.getModel();
        modelo.setRowCount(0);

        try (Connection con = ConexionBD.conectar()) {

            if (con == null) {
                JOptionPane.showMessageDialog(this,
                        "No se pudo conectar a la base de datos.",
                        "Error de conexión",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 1. Ganancias del mes actual
            try (Statement st = con.createStatement();
                 ResultSet rsGanancias = st.executeQuery(
                         "SELECT ISNULL(SUM(precio_final), 0) AS total "
                         + "FROM Reserva "
                         + "WHERE estado = 'Activa' "
                         + "AND MONTH(fecha_reserva) = MONTH(GETDATE()) "
                         + "AND YEAR(fecha_reserva) = YEAR(GETDATE())"
                 )) {

                double ganancias = 0;

                if (rsGanancias.next()) {
                    ganancias = rsGanancias.getDouble("total");
                }

                lblGanancias.setText(String.format("S/ %.2f", ganancias));
            }

            // 2. Día más concurrido del mes actual
            try (Statement st2 = con.createStatement();
                 ResultSet rsDia = st2.executeQuery(
                         "SELECT TOP 1 fecha_reserva, COUNT(*) AS cantidad "
                         + "FROM Reserva "
                         + "WHERE estado = 'Activa' "
                         + "AND MONTH(fecha_reserva) = MONTH(GETDATE()) "
                         + "AND YEAR(fecha_reserva) = YEAR(GETDATE()) "
                         + "GROUP BY fecha_reserva "
                         + "ORDER BY cantidad DESC"
                 )) {

                if (rsDia.next()) {
                    java.sql.Date fechaSql = rsDia.getDate("fecha_reserva");

                    String diaFormateado = fechaSql.toLocalDate()
                            .format(DateTimeFormatter.ofPattern(
                                    "dd 'de' MMMM",
                                    new Locale("es", "ES")
                            ));

                    lblDiaConcurrido.setText(diaFormateado);
                } else {
                    lblDiaConcurrido.setText("Sin datos");
                }
            }

            // 3. Ranking de popularidad de paquetes turísticos del mes actual
            ArrayList<String> nombres = new ArrayList<>();
            ArrayList<String> destinos = new ArrayList<>();
            ArrayList<Integer> cantidades = new ArrayList<>();

            int totalReservas = 0;

            try (Statement st3 = con.createStatement();
                 ResultSet rsRanking = st3.executeQuery(
                         "SELECT pt.nombre_paquete, pt.destino, COUNT(*) AS cantidad "
                         + "FROM Reserva r "
                         + "INNER JOIN PaqueteTuristico pt "
                         + "ON r.codigo_paquete = pt.codigo_paquete "
                         + "WHERE r.estado = 'Activa' "
                         + "AND MONTH(r.fecha_reserva) = MONTH(GETDATE()) "
                         + "AND YEAR(r.fecha_reserva) = YEAR(GETDATE()) "
                         + "GROUP BY pt.nombre_paquete, pt.destino "
                         + "ORDER BY cantidad DESC"
                 )) {

                while (rsRanking.next()) {
                    String nombrePaquete = rsRanking.getString("nombre_paquete");
                    String destino = rsRanking.getString("destino");
                    int cantidad = rsRanking.getInt("cantidad");

                    nombres.add(nombrePaquete);
                    destinos.add(destino);
                    cantidades.add(cantidad);

                    totalReservas += cantidad;
                }
            }

            if (nombres.isEmpty()) {
                modelo.addRow(new Object[]{
                    "Sin reservas registradas este mes",
                    "-",
                    "-",
                    "-"
                });
            } else {
                for (int i = 0; i < nombres.size(); i++) {

                    double porcentaje = 0;

                    if (totalReservas > 0) {
                        porcentaje = (cantidades.get(i) * 100.0) / totalReservas;
                    }

                    modelo.addRow(new Object[]{
                        nombres.get(i),
                        destinos.get(i),
                        cantidades.get(i),
                        String.format("%.1f %%", porcentaje)
                    });
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar estadísticas desde la base de datos:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
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

        jLabel17 = new javax.swing.JLabel();
        lblMes = new javax.swing.JLabel();
        btnGenerarReporte = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRanking = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblGanancias = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblDiaConcurrido = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 249, 236));

        jLabel17.setFont(new java.awt.Font("Forte", 0, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(80, 50, 22));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Estadisticas.png"))); // NOI18N
        jLabel17.setText("Estadísticas");

        lblMes.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblMes.setText("Fecha actual");

        btnGenerarReporte.setBackground(new java.awt.Color(255, 189, 105));
        btnGenerarReporte.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGenerarReporte.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerarReporte.setText("Generar Reporte");
        btnGenerarReporte.setBorderPainted(false);
        btnGenerarReporte.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGenerarReporte.addActionListener(this::btnGenerarReporteActionPerformed);

        tblRanking.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblRanking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Paquete", "Destino", "Reservas", "% Popularidad"
            }
        ));
        tblRanking.setRowHeight(28);
        tblRanking.setShowHorizontalLines(true);
        tblRanking.setShowVerticalLines(true);
        jScrollPane1.setViewportView(tblRanking);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 164, 61), 2));
        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ganancias del mes");
        jPanel1.add(jLabel1);

        lblGanancias.setFont(new java.awt.Font("Forte", 0, 26)); // NOI18N
        lblGanancias.setForeground(new java.awt.Color(245, 164, 61));
        lblGanancias.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGanancias.setText("S/ 0.00");
        jPanel1.add(lblGanancias);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 223;
        gridBagConstraints.ipady = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(29, 36, 37, 0);
        jPanel3.add(jPanel1, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 164, 61), 2));
        jPanel2.setLayout(new java.awt.GridLayout(2, 1));

        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Día más concurrido");
        jPanel2.add(jLabel3);

        lblDiaConcurrido.setFont(new java.awt.Font("Forte", 0, 26)); // NOI18N
        lblDiaConcurrido.setForeground(new java.awt.Color(245, 164, 61));
        lblDiaConcurrido.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDiaConcurrido.setText("-");
        jPanel2.add(lblDiaConcurrido);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 332;
        gridBagConstraints.ipady = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(29, 18, 37, 31);
        jPanel3.add(jPanel2, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(156, 156, 156)
                        .addComponent(lblMes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGenerarReporte)))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMes)
                            .addComponent(btnGenerarReporte))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addGap(31, 31, 31))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerarReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarReporteActionPerformed
        cargarEstadisticas();
    }//GEN-LAST:event_btnGenerarReporteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerarReporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDiaConcurrido;
    private javax.swing.JLabel lblGanancias;
    private javax.swing.JLabel lblMes;
    private javax.swing.JTable tblRanking;
    // End of variables declaration//GEN-END:variables
}
