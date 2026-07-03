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

    public PaqueteTuristico(String nombre_paquete, String codigo_paquete, String destino, double costo, String horario, int cupos_disponibles, int cupos_maximos) {
        this.nombre_paquete = nombre_paquete;
        this.codigo_paquete = codigo_paquete;
        this.destino = destino;
        this.costo = costo;
        this.horario = horario;
        this.cupos_disponibles = cupos_disponibles;
        this.cupos_maximos = cupos_maximos;
        this.listaPasajeros = new ArrayList<>();
    }

    public PaqueteTuristico(ArrayList<Pasajero> listaPasajeros) {
        this.listaPasajeros = listaPasajeros;
    }



    public String getNombre_paquete() {
        return nombre_paquete;
    }

    public void setNombre_paquete(String nombre_paquete) {
        this.nombre_paquete = nombre_paquete;
    }

    public String getCodigo_paquete() {
        return codigo_paquete;
    }

    public void setCodigo_paquete(String codigo_paquete) {
        this.codigo_paquete = codigo_paquete;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getCupos_disponibles() {
        return cupos_disponibles;
    }

    public void setCupos_disponibles(int cupos_disponibles) {
        this.cupos_disponibles = cupos_disponibles;
    }

    public int getCupos_maximos() {
        return cupos_maximos;
    }

    public void setCupos_maximos(int cupos_maximos) {
        this.cupos_maximos = cupos_maximos;
    }

    public ArrayList<Pasajero> getListaPasajeros() {
        return listaPasajeros;
    }

    public void setListaPasajeros(ArrayList<Pasajero> listaPasajeros) {
        this.listaPasajeros = listaPasajeros;
    }

    public boolean reservarCupos(){
        if(cupos_disponibles <= 0){
            return false;
        }
        cupos_disponibles--;
        return true;
    }

    public boolean liberarCupos() {
        if (cupos_disponibles >= cupos_maximos) {
            return false;
        }

        cupos_disponibles++;
        return true;     
    }

    public boolean tieneCupos() {
        if(cupos_disponibles > 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean agregarPasajero(Pasajero pasajero) {
        if (pasajero == null)
            return false;

        if (!reservarCupos())
            return false;

        listaPasajeros.add(pasajero);
        return true;
    }

    public boolean eliminarPasajero(Pasajero pasajero) {
        if (!listaPasajeros.remove(pasajero))
            return false;

        liberarCupos();
        return true;
    }

    public void mostrarInfo() {
        System.out.println("INFORMACION DEL PAQUETE");
        System.out.println("Codigo             : " + codigo_paquete);
        System.out.println("Nombre             : " + nombre_paquete);
        System.out.println("Destino            : " + destino);
        System.out.println("Horario            : " + horario);
        System.out.println("Costo              : S/. " + costo);
        System.out.println("Cupos disponibles  : " + cupos_disponibles);
        System.out.println("Cupos maximos      : " + cupos_maximos);
    }

    public void listarPasajeros() {
        System.out.println("\nLISTA DE PASAJEROS");
        for (int i = 0; i < listaPasajeros.size(); i++) {
            System.out.println(listaPasajeros.get(i).unificarDatos());
        }
    }

    public int contarPasajeros() {
        return listaPasajeros.size();
    }
    public double porcentajeOcupacion() {
        int ocupados = cupos_maximos - cupos_disponibles;
        return (ocupados * 100.0) / cupos_maximos;
    }
}
