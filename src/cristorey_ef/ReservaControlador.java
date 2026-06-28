/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

import java.util.ArrayList;

/**
 *
 * @author coolg
 */
public class ReservaControlador {
    ArrayList<Reserva> reserva = new ArrayList();
    
    public boolean registrarReserva(Pasajero pasajero, PaqueteTuristico paquete, double descuento){
        try{
            if (pasajero == null || paquete == null) {
                System.out.println("No se puede registrar la reserva. Datos incompletos.");
                return false;
            }
            if (!paquete.tieneCupos()) {
                System.out.println("No se puede registrar la reserva. No existen cupos disponibles.");
                return false;
            }
            
            double precio_final = calcularPrecioFinal(paquete.getCosto(), descuento);
            
            Reserva nueva_reserva = new Reserva(pasajero, paquete, precio_final);
            reserva.add(nueva_reserva);
            
            paquete.agregarPasajero(pasajero);
            
            System.out.println("Reserva Registrada Correctamente.");
            return true;
        }catch(Exception e){
            System.out.println("Error al registrar  reserva: "+e.getMessage());
            return false;
        }
    }
    
    public double calcularPrecioFinal(double precio, double descuento){
        try {
            if (descuento < 0 || descuento > 100) {
                System.out.println("Descuento invalido. No se aplicara descuento");
                descuento = 0;
            }
            
            return precio - (precio*descuento / 100);
            
        } catch (Exception e) {
            System.out.println("Error al calcular precio final.");
            return precio;
        }
    }
    
    public Reserva buscarReserva(String codigo_reserva){
        for (int i = 0; i < reserva.size(); i++) {
            if (reserva.get(i).getCodigo_reserva().equalsIgnoreCase(codigo_reserva)) {
                return reserva.get(i);
            }
        }
        return null;
    }
    
    public Reserva buscarReservaActiva(String nro_doc, String codigo_paquete){
        for (int i = 0; i < reserva.size(); i++) {
            Reserva r = reserva.get(i);
            
            if (r.getPasajero().getDocumento().getNro_doc().equalsIgnoreCase(nro_doc)
                    && r.getPaquete().getCodigo_paquete().equalsIgnoreCase(codigo_paquete)
                    && r.getEstado().equalsIgnoreCase("Activa")) {
                return r;
            }
        }
        return null;
    }
    
    public void listarReserva(){
        if (reserva.size()==0) {
            System.out.println("No existen reservar registradas");
        }else{
            for (int i = 0; reserva.size() < 10; i++) {
                reserva.get(i).mostrarReserva();
            }
        }
    }
    
    public void historialCliente(String nro_doc){
        boolean cliente_encontrado = false;
        for (int i = 0; i < reserva.size(); i++) {
            Reserva r = reserva.get(i);
            if (r.getPasajero().getDocumento().getNro_doc().equalsIgnoreCase(nro_doc)) {
                r.mostrarReserva();
                cliente_encontrado = true;
            }
        }
        
        if(!cliente_encontrado){
            System.out.println("El cliente no tiene historial de reservas.");
        }
    }
    
    public boolean cancelarReserva(String codigo_reserva){
        try {
            Reserva reserva = buscarReserva(codigo_reserva);
            if (reserva == null) {
                System.out.println("No se encontró la reserva.");
                return false;
            }
            
            if(reserva.getEstado().equalsIgnoreCase("Cancelada")){
                System.out.println("La reserva ya se encuentra cancelada.");
                return false;
            }
            
            reserva.setEstado("Cancelada");
            reserva.getPaquete().eliminarPasajero(reserva.getPasajero());
            
            System.out.println("Reserva cancelada correctamente.");
            return true;
        } catch (Exception e) {
            System.out.println("Error al cancelar la reserva "+ e.getMessage());
            return false;
        }
    }
}
