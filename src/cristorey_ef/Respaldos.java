/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

/**
 *
 * @author coolg
 */
public class Respaldos {
    public void generarRespaldos(PasajeroControlador pasajero_controlador,
            PaqueteTuristicoControlador paquete_controlador, 
            ReservaControlador reserva_controlador){
        try {
            if (pasajero_controlador == null || paquete_controlador == null || reserva_controlador == null) {
                System.out.println("No se puede generar respaldos. Faltan datos");
                return;
            }
            
            System.out.println("===RESPALDO AUTOMATICO===\nPasajeros respaldados: "+pasajero_controlador.pasajero.size()+
                    "\nPaquete Turistico respaldados: "+paquete_controlador.paquete.size()+
                    "\nReservas respaldadas: "+reserva_controlador.reserva.size());
            
            System.out.println("Respaldo generado correctamente");
        } catch (Exception e) {
            System.out.println("Error al generar respaldo: "+e.getMessage());
        }
    }
    
}
