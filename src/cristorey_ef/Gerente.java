/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

/**
 *
 * @author coolg
 */
public class Gerente extends Usuario{
    
    private String area_gerencia;

    public String getArea_gerencia() {
        return area_gerencia;
    }

    public void setArea_gerencia(String area_gerencia) {
        this.area_gerencia = area_gerencia;
    }

    public Gerente(String area_gerencia, String nombre, String correo, String clave_ingreso, Documento documento) {
        super(nombre, correo, clave_ingreso, documento);
        this.area_gerencia = area_gerencia;
    }
    
    public void verDatos(){
        System.out.println("===DATOS GUÍA TURISTICO===\nCodigo usuario: "+this.codigo_usuario+"\nNombre: "+this.nombre+
                "Correo: "+this.correo+"\nArea gerencia: "+this.area_gerencia);
    }
    
    public void reporteDisponibilidad(PaqueteTuristicoControlador controlador){
        try {
            if (controlador == null || controlador.getPaquete().size() == 00) {
                System.out.println("No existen paquetes turisticos registrados");
                return;
            }
            
            System.out.println("===REPORTE DE CUPOS DISPONIBLES===");
            
            for (int i = 0; i < controlador.getPaquete().size(); i++) {
                PaqueteTuristico paquete = controlador.getPaquete().get(i);
                
                int cupos_ocupados = paquete.getCupos_maximos() - paquete.getCupos_disponibles();
                
                System.out.println("\nCodigo: "+paquete.getCodigo_paquete()+
                        "\nPaquete: "+paquete.getNombre_paquete()+
                        "\nHorario: "+paquete.getHorario()+
                        "\nCupos Totales: "+paquete.getCupos_maximos()+
                        "\nCupos Ocupados: "+cupos_ocupados+
                        "\nCupos Disponibles: "+paquete.getCupos_disponibles());
            }
        } catch (Exception e) {
            System.out.println("Error al generar reporte de disponibilidad: " + e.getMessage());
        }
    }
    
    public void estadisticasGerenciales(PaqueteTuristicoControlador paquete_controlador, ReservaControlador reserva_controlador){
        try {
            if (paquete_controlador == null || reserva_controlador == null) {
                System.out.println("Estadisticas vacias. No existen datos.");
                return;
            }
            
            int total_paquetes = paquete_controlador.getPaquete().size();
            int total_pasajeros= 0;
            int cupos_disponibles = 0;
            
            for (int i = 0; i < paquete_controlador.getPaquete().size(); i++) {
                PaqueteTuristico paquete = paquete_controlador.getPaquete().get(i);
                total_pasajeros += paquete.contarPasajeros();
                cupos_disponibles += paquete.getCupos_disponibles();
            }
            
            int total_reservas = reserva_controlador.reserva.size();

            System.out.println("===ESTADISTICAS GERENCIALES==="
                    + "\nTotal de paquetes turisticos: "+total_paquetes+
                    "\nTotal de pasajeros: "+total_pasajeros+
                    "\nCupos disponibles: "+cupos_disponibles+
                    "\nTotal de reservas: "+total_reservas);
            
        } catch (Exception e) {
            System.out.println("Error al mostrar estádisticas: "+e.getMessage());
        }
    }
    
    public void reporteVentas(ReservaControlador reserva_controlador){
        try {
            if (reserva_controlador == null || reserva_controlador.reserva.size() == 0) {
                System.out.println("No existen reservas registradas.");
                return;
            }
            
            double totalVentas=0;
            
            System.out.println("===REPORTE DE VENTAS===");
            for (int i = 0; i < reserva_controlador.reserva.size(); i++) {
                Reserva reserva = reserva_controlador.reserva.get(i);
                
                if (reserva.getEstado().equalsIgnoreCase("Activa")) {
                    totalVentas += reserva.getPrecio_final();
                    reserva.mostrarReserva();
                }
            }
            
            System.out.println("TOTAL DE VENTAS ACTIVAS: "+ totalVentas);
        } catch (Exception e) {
            System.out.println("Error al generar reporte de ventas: "+e.getMessage());
        }
    }
    
    public void historialReservas(ReservaControlador reserva_controlador){
        try {
            if (reserva_controlador == null) {
                System.out.println("Historial de reservas no disponible.");
                return;
            }
            
            System.out.println("===HISTORIAL DE RESERVAS===");
            reserva_controlador.listarReserva();
        } catch (Exception e) {
            System.out.println("Error al consultar el historial de reservas: "+e.getMessage());
        }
    }
    @Override
    public boolean validarAcceso(String correo, String clave) {
        return this.correo.equalsIgnoreCase(correo) 
                && this.clave_ingreso.equalsIgnoreCase(clave) 
                && !this.bloqueado;
    }
    
}
