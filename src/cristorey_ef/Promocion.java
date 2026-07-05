/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

import java.time.LocalDate;

/**
 * Promocion de temporada asociada a un PaqueteTuristico. Tiene una
 * ventana de vigencia (fecha_inicio - fecha_fin) y un criterio de
 * elegibilidad simple basado en la edad del pasajero (apoyado en
 * Documento, que ya guarda la edad). Un rango de edad en 0 significa
 * "sin restriccion" en ese extremo.
 *
 * @author Usuario
 */
public class Promocion {
    private String nombre;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private double porcentaje_descuento;
    private int edad_minima;
    private int edad_maxima;

    public Promocion(String nombre, LocalDate fecha_inicio, LocalDate fecha_fin,
            double porcentaje_descuento, int edad_minima, int edad_maxima) {
        this.nombre = nombre;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.porcentaje_descuento = porcentaje_descuento;
        this.edad_minima = edad_minima;
        this.edad_maxima = edad_maxima;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public double getPorcentaje_descuento() {
        return porcentaje_descuento;
    }

    public void setPorcentaje_descuento(double porcentaje_descuento) {
        this.porcentaje_descuento = porcentaje_descuento;
    }

    public int getEdad_minima() {
        return edad_minima;
    }

    public void setEdad_minima(int edad_minima) {
        this.edad_minima = edad_minima;
    }

    public int getEdad_maxima() {
        return edad_maxima;
    }

    public void setEdad_maxima(int edad_maxima) {
        this.edad_maxima = edad_maxima;
    }

    public boolean estaVigente() {
        LocalDate hoy = LocalDate.now();
        return !hoy.isBefore(fecha_inicio) && !hoy.isAfter(fecha_fin);
    }

    public boolean aplicable(Pasajero pasajero) {
        if (pasajero == null || pasajero.getDocumento() == null) {
            return false;
        }
        int edad = pasajero.getDocumento().getEdad();
        boolean cumpleMinimo = edad_minima <= 0 || edad >= edad_minima;
        boolean cumpleMaximo = edad_maxima <= 0 || edad <= edad_maxima;
        return cumpleMinimo && cumpleMaximo;
    }

    public String describirCriterio() {
        if (edad_minima > 0 && edad_maxima > 0) {
            return "Edad entre " + edad_minima + " y " + edad_maxima + " años";
        } else if (edad_minima > 0) {
            return "Edad desde " + edad_minima + " años";
        } else if (edad_maxima > 0) {
            return "Edad hasta " + edad_maxima + " años";
        }
        return "Aplica a todas las edades";
    }
}
