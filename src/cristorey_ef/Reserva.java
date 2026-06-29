/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

/**
 *
 * @author coolg
 */
public class Reserva {
    private String codigo_reserva;
    private Pasajero pasajero;
    private PaqueteTuristico paquete;
    private double precio_final;
    private String estado;
    private static int contador = 0;

    public Reserva(Pasajero pasajero, PaqueteTuristico paquete, double precio_final) {
        this.codigo_reserva = "R"+String.format("%05d", ++contador);
        this.pasajero = pasajero;
        this.paquete = paquete;
        this.precio_final = precio_final;
        this.estado = "Activa";
    }

    public String getCodigo_reserva() {
        return codigo_reserva;
    }

    public void setCodigo_reserva(String codigo_reserva) {
        this.codigo_reserva = codigo_reserva;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public PaqueteTuristico getPaquete() {
        return paquete;
    }

    public void setPaquete(PaqueteTuristico paquete) {
        this.paquete = paquete;
    }

    public double getPrecio_final() {
        return precio_final;
    }

    public void setPrecio_final(double precio_final) {
        this.precio_final = precio_final;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Reserva.contador = contador;
    }
    
    public void mostrarReserva(){
        System.out.println("===DATOS RESERVA===\n"
                + "Codigo Reserva: "+this.codigo_reserva+"\nPasajero: "+this.pasajero.unificarDatos()+
                "\nPaquete: "+this.paquete.getNombre_paquete()+"\nDestino: "+this.paquete.getDestino()+
                "\nPrecio Final: S/ "+this.precio_final+"\nEstado: "+this.estado);
    }
}
