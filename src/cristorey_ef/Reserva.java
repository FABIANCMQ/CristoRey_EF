/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

import java.time.LocalDate;

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
    private String estado_aprobacion;
    private LocalDate fecha_reserva;
    private LocalDate fecha_cancelacion;
    private static int contador = 0;

    public Reserva(Pasajero pasajero, PaqueteTuristico paquete, double precio_final) {
        this.codigo_reserva = "R"+String.format("%05d", ++contador);
        this.pasajero = pasajero;
        this.paquete = paquete;
        this.precio_final = precio_final;
        this.estado = "Activa";
        this.estado_aprobacion = "Pendiente";
        this.fecha_reserva = LocalDate.now();
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

    public String getEstado_aprobacion() {
        return estado_aprobacion;
    }

    public void setEstado_aprobacion(String estado_aprobacion) {
        this.estado_aprobacion = estado_aprobacion;
    }

    public LocalDate getFecha_reserva() {
        return fecha_reserva;
    }

    public void setFecha_reserva(LocalDate fecha_reserva) {
        this.fecha_reserva = fecha_reserva;
    }

    public LocalDate getFecha_cancelacion() {
        return fecha_cancelacion;
    }

    public void setFecha_cancelacion(LocalDate fecha_cancelacion) {
        this.fecha_cancelacion = fecha_cancelacion;
    }

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Reserva.contador = contador;
    }
}
