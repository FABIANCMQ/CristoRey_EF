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
    private JLabel lblUsuarioActivo = new JLabel(" ");
    private JPanel panelPrincipal = new JPanel(cardLayout);

    public VentanaPrincipal() {
        setTitle("Agencia Cristo Rey");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new BorderLayout());
        lblUsuarioActivo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 13));
        lblUsuarioActivo.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 10, 6, 10));
        panelSuperior.add(lblUsuarioActivo, BorderLayout.WEST);
        
        panelPrincipal.add(crearPanelLogin(), "LOGIN");
        panelPrincipal.add(crearPanelAdmin(), "ADMIN");
        panelPrincipal.add(crearPanelPlanillero(), "PLANILLERO");
        panelPrincipal.add(crearPanelPasajeros(), "PASAJEROS");
        panelPrincipal.add(crearPanelPaquetes(), "PAQUETES");

        add(panelPrincipal, BorderLayout.CENTER);
        cardLayout.show(panelPrincipal, "LOGIN");
    }

    private JPanel crearPanelLogin() {
        JPanel panelExterno = new JPanel(new java.awt.GridBagLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 8));
        panel.setMaximumSize(new java.awt.Dimension(320, 160));

        JLabel lblCorreo     = new JLabel("Correo:");
        JTextField txtCorreo = new JTextField(18);
        JLabel lblClave      = new JLabel("Clave:");
        JTextField txtClave  = new JTextField(18);
        JButton btnIngresar  = new JButton("Ingresar");
        JLabel lblMensaje    = new JLabel("");

        btnIngresar.setPreferredSize(new java.awt.Dimension(110, 28));

        panel.add(lblCorreo);   panel.add(txtCorreo);
        panel.add(lblClave);    panel.add(txtClave);
        panel.add(btnIngresar); panel.add(lblMensaje);

        panelExterno.add(panel);

        btnIngresar.addActionListener(e -> {
            String correo = txtCorreo.getText();
            String clave  = txtClave.getText();
            Usuario encontrado = null;

            for (int i = 0; i < usuarioControlador.getUsuario().size(); i++) {
                if (usuarioControlador.getUsuario().get(i).validarAcceso(correo, clave)) {
                    encontrado = usuarioControlador.getUsuario().get(i);
                }
            }

            if (encontrado == null) {
                lblMensaje.setText("Credenciales incorrectas.");
            } else if (encontrado instanceof Administrador) {
                Administrador adm = (Administrador) encontrado;
                lblUsuarioActivo.setText("Administrador  |  " + adm.getNombre()
                        + "  |  DNI: " + adm.documento.getNro_doc());
                cardLayout.show(panelPrincipal, "ADMIN");
            } else if (encontrado instanceof Planillero) {
                Planillero pla = (Planillero) encontrado;
                lblUsuarioActivo.setText("Planillero  |  " + pla.getNombre()
                        + "  |  DNI: " + pla.documento.getNro_doc());
                cardLayout.show(panelPrincipal, "PLANILLERO");
            }
        });
        return panelExterno;
    }

    private JPanel crearPanelAdmin() {
        JPanel panelExterno = new JPanel(new java.awt.GridBagLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton btnReportePasajeros = new JButton("Reporte de Pasajeros");
        JButton btnReportePaquetes  = new JButton("Reporte de Paquetes");
        JButton btnEstadisticas     = new JButton("Ver Estadisticas");
        JButton btnControlMatutino  = new JButton("Control Matutino");
        JButton btnGestionAccesos   = new JButton("Gestionar Accesos");
        JButton btnVolver           = new JButton("Cerrar Sesion");

        java.awt.Dimension tamBoton = new java.awt.Dimension(220, 32);
        JButton[] botones = {btnReportePasajeros, btnReportePaquetes,
                             btnEstadisticas, btnControlMatutino,
                             btnGestionAccesos, btnVolver};
        for (int i = 0; i < botones.length; i++) {
            botones[i].setPreferredSize(tamBoton);
            botones[i].setMaximumSize(tamBoton);
            botones[i].setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            panel.add(botones[i]);
            panel.add(javax.swing.Box.createVerticalStrut(8));
        }

        panelExterno.add(panel);

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
            int total = 0;
            for (int i = 0; i < paqueteControlador.getPaquete().size(); i++) {
                total += paqueteControlador.getPaquete().get(i).contarPasajeros();
            }
            JOptionPane.showMessageDialog(this, "Total de pasajeros: " + total);
        });

        btnControlMatutino.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < paqueteControlador.getPaquete().size(); i++) {
                PaqueteTuristico p = paqueteControlador.getPaquete().get(i);
                sb.append(p.getNombre_paquete()).append(" -> ")
                  .append(p.getCupos_disponibles()).append(" cupos\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(),
                    "Control Matutino", JOptionPane.INFORMATION_MESSAGE);
        });

        btnGestionAccesos.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Usuarios del sistema:\n\n");
            for (int i = 1; i < usuarioControlador.getUsuario().size(); i++) {
                Usuario u = usuarioControlador.getUsuario().get(i);
                String estado = u.isBloqueado() ? "BLOQUEADO" : "ACTIVO";
                sb.append(i).append(". ").append(u.getNombre())
                  .append("  [").append(estado).append("]\n");
            }
            sb.append("\nIngrese el número del usuario a modificar:");

            String entrada = JOptionPane.showInputDialog(this, sb.toString(),
                    "Gestionar Accesos", JOptionPane.QUESTION_MESSAGE);
            if (entrada == null || entrada.trim().isEmpty()) return;

            int indice;
            try {
                indice = Integer.parseInt(entrada.trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un número válido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (indice < 1 || indice >= usuarioControlador.getUsuario().size()) {
                JOptionPane.showMessageDialog(this, "Número de usuario inválido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario seleccionado = usuarioControlador.getUsuario().get(indice);
            String[] opciones = seleccionado.isBloqueado()
                    ? new String[]{"Desbloquear"}
                    : new String[]{"Bloquear"};

            int accion = JOptionPane.showOptionDialog(this,
                    "Usuario: " + seleccionado.getNombre()
                    + "\nEstado actual: " + (seleccionado.isBloqueado() ? "BLOQUEADO" : "ACTIVO"),
                    "Gestionar Acceso",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opciones, opciones[0]);

            if (accion == 0) {
                if (seleccionado.isBloqueado()) {
                    admin.desbloquearUsuario(seleccionado);
                    JOptionPane.showMessageDialog(this,
                            seleccionado.getNombre() + " ha sido DESBLOQUEADO.",
                            "Acceso concedido", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    admin.BloquearUsuario(seleccionado);
                    JOptionPane.showMessageDialog(this,
                            seleccionado.getNombre() + " ha sido BLOQUEADO.",
                            "Acceso revocado", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnVolver.addActionListener(e -> {
            lblUsuarioActivo.setText(" ");
            cardLayout.show(panelPrincipal, "LOGIN");
        });

        return panelExterno;
    }

    private JPanel crearPanelPlanillero() {
        JPanel panelExterno = new JPanel(new java.awt.GridBagLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton btnRegistrarPasajero = new JButton("Registrar Pasajero");
        JButton btnAsignarPaquete    = new JButton("Asignar Pasajero a Paquete");
        JButton btnVerPaquetes       = new JButton("Ver Paquetes");
        JButton btnVolver            = new JButton("Cerrar Sesion");

        java.awt.Dimension tamBoton = new java.awt.Dimension(220, 32);
        JButton[] botones = {btnRegistrarPasajero, btnAsignarPaquete, btnVerPaquetes, btnVolver};
        for (int i = 0; i < botones.length; i++) {
            botones[i].setPreferredSize(tamBoton);
            botones[i].setMaximumSize(tamBoton);
            botones[i].setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            panel.add(botones[i]);
            panel.add(javax.swing.Box.createVerticalStrut(8));
        }

        panelExterno.add(panel);

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

        btnVolver.addActionListener(e -> {
            lblUsuarioActivo.setText(" ");
            cardLayout.show(panelPrincipal, "LOGIN");
        });

        return panelExterno;
    }
    private JPanel crearPanelPasajeros() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnas = {"Nombre", "Ap. Paterno", "Ap. Materno", "Telefono", "Documento"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);

        JPanel panelFormulario = new JPanel(new GridLayout(8, 2, 5, 5));
        JTextField txtNombre    = new JTextField();
        JTextField txtApPaterno = new JTextField();
        JTextField txtApMaterno = new JTextField();
        JTextField txtTelefono  = new JTextField();
        JTextField txtCorreo    = new JTextField();
        JTextField txtTipoDoc   = new JTextField();
        JTextField txtNroDoc    = new JTextField();

        panelFormulario.add(new JLabel("Nombre:"));            panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Ap. Paterno:"));       panelFormulario.add(txtApPaterno);
        panelFormulario.add(new JLabel("Ap. Materno:"));       panelFormulario.add(txtApMaterno);
        panelFormulario.add(new JLabel("Telefono:"));          panelFormulario.add(txtTelefono);
        panelFormulario.add(new JLabel("Correo:"));            panelFormulario.add(txtCorreo);
        panelFormulario.add(new JLabel("Tipo Doc (DNI/CE):")); panelFormulario.add(txtTipoDoc);
        panelFormulario.add(new JLabel("Nro Documento:"));     panelFormulario.add(txtNroDoc);

        JButton btnGuardar = new JButton("Registrar");
        JButton btnVolver  = new JButton("Volver");
        panelFormulario.add(btnGuardar);
        panelFormulario.add(btnVolver);

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(panelFormulario, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> {
            // 1. Validar que ningún campo esté vacío
            if (txtNombre.getText().trim().isEmpty()
                    || txtApPaterno.getText().trim().isEmpty()
                    || txtApMaterno.getText().trim().isEmpty()
                    || txtTelefono.getText().trim().isEmpty()
                    || txtCorreo.getText().trim().isEmpty()
                    || txtTipoDoc.getText().trim().isEmpty()
                    || txtNroDoc.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe completar todos los campos antes de registrar.",
                        "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tipoDoc = txtTipoDoc.getText().trim();
            String nroDoc  = txtNroDoc.getText().trim();

            // 2. Validar tipo de documento
            if (!tipoDoc.equalsIgnoreCase("DNI") && !tipoDoc.equalsIgnoreCase("CE")) {
                JOptionPane.showMessageDialog(this,
                        "Tipo de documento inválido. Use DNI o CE.",
                        "Error de documento", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. Validar longitud según tipo
            if (tipoDoc.equalsIgnoreCase("DNI") && nroDoc.length() != 8) {
                JOptionPane.showMessageDialog(this,
                        "El DNI debe tener exactamente 8 dígitos.",
                        "Error de documento", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tipoDoc.equalsIgnoreCase("CE") && nroDoc.length() != 9) {
                JOptionPane.showMessageDialog(this,
                        "El CE debe tener exactamente 9 caracteres.",
                        "Error de documento", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 4. Verificar que no exista ya ese número de documento
            if (pasajeroControlador.buscarDocumento(nroDoc) != null) {
                JOptionPane.showMessageDialog(this,
                        "Ya existe un pasajero registrado con ese número de documento.",
                        "Pasajero duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 5. Todo válido → registrar
            Documento doc = new Documento(tipoDoc, nroDoc,
                    LocalDate.now(), LocalDate.now().plusYears(8), "Nacional", 25);
            Pasajero nuevo = new Pasajero(
                    txtApPaterno.getText().trim(), txtApMaterno.getText().trim(),
                    txtNombre.getText().trim(), txtTelefono.getText().trim(),
                    txtCorreo.getText().trim(), doc);
            pasajeroControlador.registrarPasajero(nuevo);
            modelo.addRow(new Object[]{
                txtNombre.getText().trim(), txtApPaterno.getText().trim(),
                txtApMaterno.getText().trim(), txtTelefono.getText().trim(), nroDoc
            });

            txtNombre.setText("");    txtApPaterno.setText("");
            txtApMaterno.setText(""); txtTelefono.setText("");
            txtCorreo.setText("");    txtTipoDoc.setText("");
            txtNroDoc.setText("");
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
        btnVolver.setPreferredSize(new java.awt.Dimension(100, 28));
        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "PLANILLERO"));

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(btnVolver, BorderLayout.SOUTH);

        return panel;
    }
}
