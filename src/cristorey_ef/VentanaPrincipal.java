/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author coolg
 */
public class VentanaPrincipal extends JFrame {

    private UsuarioControlador usuarioControlador = new UsuarioControlador();
    private PasajeroControlador pasajeroControlador = new PasajeroControlador();
    private PaqueteTuristicoControlador paqueteControlador = new PaqueteTuristicoControlador();

    private CardLayout cardLayout = new CardLayout();
    private JPanel panelPrincipal = new JPanel(cardLayout);

    public VentanaPrincipal() {
        setTitle("Agencia Cristo Rey");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panelPrincipal.add(crearPanelLogin(), "LOGIN");
        panelPrincipal.add(crearPanelAdmin(), "ADMIN");
        panelPrincipal.add(crearPanelPlanillero(), "PLANILLERO");
        panelPrincipal.add(crearPanelPasajeros(), "PASAJEROS");
        panelPrincipal.add(crearPanelPaquetes(), "PAQUETES");

        add(panelPrincipal, BorderLayout.CENTER);
        cardLayout.show(panelPrincipal, "LOGIN");
    }

    private JPanel crearPanelLogin() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel lblCorreo = new JLabel("Correo:");
        JTextField txtCorreo = new JTextField();
        JLabel lblClave = new JLabel("Clave:");
        JTextField txtClave = new JTextField();
        JButton btnIngresar = new JButton("Ingresar");
        JLabel lblMensaje = new JLabel("");

        panel.add(lblCorreo);   panel.add(txtCorreo);
        panel.add(lblClave);    panel.add(txtClave);
        panel.add(btnIngresar); panel.add(lblMensaje);

        btnIngresar.addActionListener(e -> {
            String correo = txtCorreo.getText();
            String clave = txtClave.getText();
            Usuario encontrado = null;

            for (int i = 0; i < usuarioControlador.getUsuario().size(); i++) {
                if (usuarioControlador.getUsuario().get(i).validarAcceso(correo, clave)) {
                    encontrado = usuarioControlador.getUsuario().get(i);
                }
            }

            if (encontrado == null) {
                lblMensaje.setText("Credenciales incorrectas.");
            } else if (encontrado instanceof Administrador) {
                cardLayout.show(panelPrincipal, "ADMIN");
            } else if (encontrado instanceof Planillero) {
                cardLayout.show(panelPrincipal, "PLANILLERO");
            }
        });

        return panel;
    }

    private JPanel crearPanelAdmin() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));

        JButton btnReportePasajeros  = new JButton("Reporte de Pasajeros");
        JButton btnReportePaquetes   = new JButton("Reporte de Paquetes");
        JButton btnEstadisticas      = new JButton("Ver Estadisticas");
        JButton btnControlMatutino   = new JButton("Control Matutino");
        JButton btnVolver            = new JButton("Cerrar Sesion");

        panel.add(btnReportePasajeros);
        panel.add(btnReportePaquetes);
        panel.add(btnEstadisticas);
        panel.add(btnControlMatutino);
        panel.add(btnVolver);

        Administrador admin = (Administrador) usuarioControlador.getUsuario().get(0);

        btnReportePasajeros.addActionListener(e -> {
            if (pasajeroControlador.psjr.size() == 0) {
                JOptionPane.showMessageDialog(this, "No hay pasajeros registrados.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pasajeroControlador.psjr.size(); i++) {
                    sb.append(pasajeroControlador.psjr.get(i).unificarDatos()).append("\n");
                }
                JOptionPane.showMessageDialog(this, sb.toString(),
                        "Reporte de Pasajeros", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnReportePaquetes.addActionListener(e -> {
            admin.generarReportesPaquetes(paqueteControlador);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < paqueteControlador.getPaquete().size(); i++) {
                PaqueteTuristico p = paqueteControlador.getPaquete().get(i);
                sb.append(p.getCodigo_paquete()).append(" - ")
                        .append(p.getNombre_paquete()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(),
                    "Reporte de Paquetes", JOptionPane.INFORMATION_MESSAGE);
        });

        btnEstadisticas.addActionListener(e -> {
            admin.verEstadisticas(paqueteControlador);
            int total = 0;
            for (int i = 0; i < paqueteControlador.getPaquete().size(); i++) {
                total += paqueteControlador.getPaquete().get(i).contarPasajeros();
            }
            JOptionPane.showMessageDialog(this, "Total de pasajeros: " + total);
        });

        btnControlMatutino.addActionListener(e -> {
            admin.reporteControlMatutino(paqueteControlador);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < paqueteControlador.getPaquete().size(); i++) {
                PaqueteTuristico p = paqueteControlador.getPaquete().get(i);
                sb.append(p.getNombre_paquete()).append(" -> ")
                        .append(p.getCupos_disponibles()).append(" cupos\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(),
                    "Control Matutino", JOptionPane.INFORMATION_MESSAGE);
        });

        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "LOGIN"));

        return panel;
    }

    private JPanel crearPanelPlanillero() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton btnRegistrarPasajero = new JButton("Registrar Pasajero");
        JButton btnAsignarPaquete    = new JButton("Asignar Pasajero a Paquete");
        JButton btnVerPaquetes       = new JButton("Ver Paquetes");
        JButton btnVolver            = new JButton("Cerrar Sesion");

        panel.add(btnRegistrarPasajero);
        panel.add(btnAsignarPaquete);
        panel.add(btnVerPaquetes);
        panel.add(btnVolver);

        Planillero planillero = (Planillero) usuarioControlador.getUsuario().get(1);

        btnRegistrarPasajero.addActionListener(e ->
                cardLayout.show(panelPrincipal, "PASAJEROS"));

        btnVerPaquetes.addActionListener(e ->
                cardLayout.show(panelPrincipal, "PAQUETES"));

        btnAsignarPaquete.addActionListener(e -> {
            String nroDoc = JOptionPane.showInputDialog(this, "Numero de documento del pasajero:");
            if (nroDoc == null) return;
            Pasajero pasajero = pasajeroControlador.buscarDocumento(nroDoc);
            if (pasajero == null) {
                JOptionPane.showMessageDialog(this, "Pasajero no encontrado.");
                return;
            }
            String codigo = JOptionPane.showInputDialog(this, "Codigo del paquete (ej: CRT-001):");
            if (codigo == null) return;
            PaqueteTuristico paquete = paqueteControlador.buscarPaquete(codigo);
            if (paquete == null) {
                JOptionPane.showMessageDialog(this, "Paquete no encontrado.");
                return;
            }
            planillero.asignarPaquete(pasajero, paquete);
            JOptionPane.showMessageDialog(this, "Pasajero asignado correctamente.");
        });

        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "LOGIN"));

        return panel;
    }

    private JPanel crearPanelPasajeros() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnas = {"Nombre", "Ap. Paterno", "Ap. Materno", "Telefono", "Documento"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);

        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 5, 5));
        JTextField txtNombre    = new JTextField();
        JTextField txtApPaterno = new JTextField();
        JTextField txtApMaterno = new JTextField();
        JTextField txtTelefono  = new JTextField();
        JTextField txtCorreo    = new JTextField();
        JTextField txtNroDoc    = new JTextField();

        panelFormulario.add(new JLabel("Nombre:"));        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Ap. Paterno:"));   panelFormulario.add(txtApPaterno);
        panelFormulario.add(new JLabel("Ap. Materno:"));   panelFormulario.add(txtApMaterno);
        panelFormulario.add(new JLabel("Telefono:"));      panelFormulario.add(txtTelefono);
        panelFormulario.add(new JLabel("Correo:"));        panelFormulario.add(txtCorreo);
        panelFormulario.add(new JLabel("Nro Documento:")); panelFormulario.add(txtNroDoc);

        JButton btnGuardar = new JButton("Registrar");
        JButton btnVolver  = new JButton("Volver");
        panelFormulario.add(btnGuardar);
        panelFormulario.add(btnVolver);

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(panelFormulario, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> {
            Documento doc = new Documento("DNI", txtNroDoc.getText(),
                    LocalDate.now(), LocalDate.now().plusYears(8), "Nacional", 25);
            Pasajero nuevo = new Pasajero(
                    txtApPaterno.getText(), txtApMaterno.getText(),
                    txtNombre.getText(), txtTelefono.getText(),
                    txtCorreo.getText(), doc);
            pasajeroControlador.registrarPasajero(nuevo);
            modelo.addRow(new Object[]{
                txtNombre.getText(), txtApPaterno.getText(),
                txtApMaterno.getText(), txtTelefono.getText(), txtNroDoc.getText()
            });
            txtNombre.setText("");    txtApPaterno.setText("");
            txtApMaterno.setText(""); txtTelefono.setText("");
            txtCorreo.setText("");    txtNroDoc.setText("");
            JOptionPane.showMessageDialog(this, "Pasajero registrado correctamente.");
        });

        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "PLANILLERO"));

        return panel;
    }

    private JPanel crearPanelPaquetes() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnas = {"Codigo", "Nombre", "Destino", "Costo", "Cupos Disp."};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);

        for (int i = 0; i < paqueteControlador.getPaquete().size(); i++) {
            PaqueteTuristico p = paqueteControlador.getPaquete().get(i);
            modelo.addRow(new Object[]{
                p.getCodigo_paquete(), p.getNombre_paquete(),
                p.getDestino(), "S/. " + p.getCosto(), p.getCupos_disponibles()
            });
        }

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "PLANILLERO"));

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(btnVolver, BorderLayout.SOUTH);

        return panel;
    }
}
