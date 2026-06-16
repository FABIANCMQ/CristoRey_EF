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
public class PaqueteTuristico {
    private String nombre_paquete;
    private String codigo_paquete;
    private String destino;
    private double costo;
    private String horario;
    private int cupos_disponibles;
    private int cupos_maximos;
    
    private ArrayList<Pasajero> listaPasajeros;

    public PaqueteTuristico(String nombre_paquete, String codigo_paquete, String destino, double costo, String horario, int cupos_disponibles, int cupos_maximos, ArrayList<Pasajero> listaPasajeros) {
        this.nombre_paquete = nombre_paquete;
        this.codigo_paquete = codigo_paquete;
        this.destino = destino;
        this.costo = costo;
        this.horario = horario;
        this.cupos_disponibles = cupos_disponibles;
        this.cupos_maximos = cupos_maximos;
        this.listaPasajeros = listaPasajeros;
    }
    
    
}
