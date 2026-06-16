/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cristorey_ef;

import java.time.LocalDate;
import java.util.Scanner;

/**
 *
 * @author coolg
 */
public class CristoRey_EF {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);

        UsuarioControlador usuarioControlador = new UsuarioControlador();
        PasajeroControlador pasajeroControlador = new PasajeroControlador();
        PaqueteTuristicoControlador paqueteControlador = new PaqueteTuristicoControlador();

        int opcion;
        boolean ejecutando = true;

        while (ejecutando) {
            System.out.println("\n========== AGENCIA CRISTO REY ==========");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registrar pasajero");
            System.out.println("3. Buscar pasajero por nombre");
            System.out.println("4. Buscar pasajero por documento");
            System.out.println("5. Listar todos los pasajeros");
            System.out.println("6. Ver paquetes turisticos");
            System.out.println("7. Asignar pasajero a paquete");
            System.out.println("8. Ver info de un paquete");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            if (opcion == 1) {

                System.out.print("Ingrese su correo: ");
                String correo = sc.nextLine();
                System.out.print("Ingrese su clave: ");
                String clave = sc.nextLine();

                Usuario usuarioEncontrado = null;
                boolean validacion = false;

                for (int i = 0; i < usuarioControlador.getUsuario().size(); i++) {
                    if (usuarioControlador.getUsuario().get(i).validarAcceso(correo, clave)) {
                        usuarioEncontrado = usuarioControlador.getUsuario().get(i);
                        validacion = true;
                    }
                }

                if (validacion == false) {
                    System.out.println("Credenciales incorrectas o usuario bloqueado.");

                } else {
                    usuarioEncontrado.IniciarSesion();

                    if (usuarioEncontrado instanceof Administrador) {
                        Administrador admin = (Administrador) usuarioEncontrado;

                        int opAdmin;
                        boolean enMenuAdmin = true;

                        while (enMenuAdmin) {
                            System.out.println("\n--- MENU ADMINISTRADOR ---");
                            System.out.println("1. Generar reporte de pasajeros");
                            System.out.println("2. Generar reporte de paquetes");
                            System.out.println("3. Ver estadisticas");
                            System.out.println("4. Control de viajes diarios");
                            System.out.println("5. Reporte control matutino");
                            System.out.println("6. Validar datos");
                            System.out.println("7. Volver");
                            System.out.print("Seleccione una opcion: ");
                            opAdmin = sc.nextInt();
                            
                            sc.nextLine();

                            if (opAdmin == 1) {
                                admin.generarReportePasajero(pasajeroControlador);
                            } else if (opAdmin == 2) {
                                admin.generarReportesPaquetes(paqueteControlador);
                            } else if (opAdmin == 3) {
                                admin.verEstadisticas(paqueteControlador);
                            } else if (opAdmin == 4) {
                                admin.controlViajesDiarios(paqueteControlador);
                            } else if (opAdmin == 5) {
                                admin.reporteControlMatutino(paqueteControlador);
                            } else if (opAdmin == 6) {
                                admin.validarDatos(pasajeroControlador);
                            } else if (opAdmin == 7) {
                                enMenuAdmin = false;
                            } else {
                                System.out.println("Opcion invalida.");
                            }
                        }

                    } else if (usuarioEncontrado instanceof Planillero) {
                        Planillero planillero = (Planillero) usuarioEncontrado;

                        int opPlanillero;
                        boolean enMenuPlanillero = true;

                        while (enMenuPlanillero) {
                            System.out.println("\n--- MENU PLANILLERO ---");
                            System.out.println("1. Ver mis datos");
                            System.out.println("2. Consultar paquete");
                            System.out.println("3. Asignar pasajero a paquete");
                            System.out.println("4. Listar pasajeros de un paquete");
                            System.out.println("5. Volver");
                            System.out.print("Seleccione una opcion: ");
                            opPlanillero = sc.nextInt();
                            sc.nextLine();

                            if (opPlanillero == 1) {
                                planillero.verDatos();
                            } else if (opPlanillero == 2) {
                                System.out.print("Ingrese el codigo del paquete (ej: CRT-001): ");
                                String codigoPaq = sc.nextLine();
                                PaqueteTuristico paquete = paqueteControlador.buscarPaquete(codigoPaq);
                                if (paquete != null) {
                                    planillero.consultarPaquetes(paquete);
                                } else {
                                    System.out.println("Paquete no encontrado.");
                                }
                            } else if (opPlanillero == 3) {
                                System.out.print("Ingrese el numero de documento del pasajero: ");
                                String nroDoc = sc.nextLine();
                                Pasajero pasajero = pasajeroControlador.buscarDocumento(nroDoc);
                                if (pasajero == null) {
                                    System.out.println("Pasajero no encontrado.");
                                } else {
                                    System.out.print("Ingrese el codigo del paquete (ej: CRT-001): ");
                                    String codigoPaq = sc.nextLine();
                                    PaqueteTuristico paquete = paqueteControlador.buscarPaquete(codigoPaq);
                                    if (paquete == null) {
                                        System.out.println("Paquete no encontrado.");
                                    } else {
                                        planillero.asignarPaquete(pasajero, paquete);
                                    }
                                }
                            } else if (opPlanillero == 4) {
                                System.out.print("Ingrese el codigo del paquete (ej: CRT-001): ");
                                String codigoPaq = sc.nextLine();
                                PaqueteTuristico paquete = paqueteControlador.buscarPaquete(codigoPaq);
                                if (paquete != null) {
                                    planillero.listarPasajerosPaquete(paquete);
                                } else {
                                    System.out.println("Paquete no encontrado.");
                                }
                            } else if (opPlanillero == 5) {
                                enMenuPlanillero = false;
                            } else {
                                System.out.println("Opcion invalida.");
                            }
                        }
                    }
                }

            } else if (opcion == 2) {

                System.out.print("Ingrese apellido paterno: ");
                String apPaterno = sc.nextLine();
                System.out.print("Ingrese apellido materno: ");
                String apMaterno = sc.nextLine();
                System.out.print("Ingrese nombre: ");
                String nombre = sc.nextLine();
                System.out.print("Ingrese telefono: ");
                String telefono = sc.nextLine();
                System.out.print("Ingrese correo: ");
                String correo = sc.nextLine();
                System.out.print("Ingrese tipo de documento (DNI/CE): ");
                String tipoDoc = sc.nextLine();
                System.out.print("Ingrese numero de documento: ");
                String nroDoc = sc.nextLine();
                System.out.print("Ingrese edad: ");
                int edad = Integer.parseInt(sc.nextLine());
                System.out.print("Ingrese tipo de residencia (Nacional/Extranjero): ");
                String tipoResidencia = sc.nextLine();

                Documento documento = new Documento(tipoDoc, nroDoc,
                        LocalDate.now(), LocalDate.now().plusYears(8),
                        tipoResidencia, edad);

                Pasajero nuevoPasajero = new Pasajero(apPaterno, apMaterno,
                        nombre, telefono, correo, documento);
                pasajeroControlador.registrarPasajero(nuevoPasajero);
                System.out.println("Pasajero registrado correctamente.");

            } else if (opcion == 3) {

                System.out.print("Ingrese el nombre del pasajero: ");
                String nombre = sc.nextLine();
                Pasajero encontrado = pasajeroControlador.buscarNombre(nombre);
                if (encontrado != null) {
                    encontrado.verDatos();
                } else {
                    System.out.println("Pasajero no encontrado.");
                }

            } else if (opcion == 4) {

                System.out.print("Ingrese el numero de documento: ");
                String nroDoc = sc.nextLine();
                Pasajero encontrado = pasajeroControlador.buscarDocumento(nroDoc);
                if (encontrado != null) {
                    encontrado.verDatos();
                } else {
                    System.out.println("Pasajero no encontrado.");
                }

            } else if (opcion == 5) {

                if (pasajeroControlador.psjr.size() == 0) {
                    System.out.println("No hay pasajeros registrados.");
                } else {
                    pasajeroControlador.listarPasajero();
                }

            } else if (opcion == 6) {

                System.out.println("\n===== PAQUETES TURISTICOS =====");
                paqueteControlador.mostrarPaquetes();

            } else if (opcion == 7) {

                System.out.print("Ingrese el numero de documento del pasajero: ");
                String nroDoc = sc.nextLine();
                Pasajero pasajero = pasajeroControlador.buscarDocumento(nroDoc);
                if (pasajero == null) {
                    System.out.println("Pasajero no encontrado.");
                } else {
                    System.out.print("Ingrese el codigo del paquete (ej: CRT-001): ");
                    String codigo = sc.nextLine();
                    PaqueteTuristico paquete = paqueteControlador.buscarPaquete(codigo);
                    if (paquete == null) {
                        System.out.println("Paquete no encontrado.");
                    } else {
                        paquete.agregarPasajero(pasajero);
                    }
                }

            } else if (opcion == 8) {

                System.out.print("Ingrese el codigo del paquete (ej: CRT-001): ");
                String codigo = sc.nextLine();
                PaqueteTuristico paquete = paqueteControlador.buscarPaquete(codigo);
                if (paquete != null) {
                    paquete.mostrarInfo();
                } else {
                    System.out.println("Paquete no encontrado.");
                }

            } else if (opcion == 9) {
                System.out.println("Hasta luego.");
                ejecutando = false;

            } else {
                System.out.println("Opcion invalida.");
            }
        }

    }
}
