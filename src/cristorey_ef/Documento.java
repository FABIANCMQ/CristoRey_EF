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
public class Documento {
    private String tipo_doc;
    private String nro_doc;
    private LocalDate fecha_emision;
    private LocalDate fecha_vencimiento;
    private String tipo_residencia;
    private int edad;

    public Documento(String tipo_doc, String nro_doc, LocalDate fecha_emision, LocalDate fecha_vencimiento, String tipo_residencia, int edad) {
        this.tipo_doc = tipo_doc;
        this.nro_doc = nro_doc;
        this.fecha_emision = fecha_emision;
        this.fecha_vencimiento = fecha_vencimiento;
        this.tipo_residencia = tipo_residencia;
        this.edad = edad;
    }
    public String getTipo_doc() {
        return tipo_doc;
    }

    public void setTipo_doc(String tipo_doc) {
        this.tipo_doc = tipo_doc;
    }

    public String getNro_doc() {
        return nro_doc;
    }

    public void setNro_doc(String nro_doc) {
        this.nro_doc = nro_doc;
    }

    public LocalDate getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(LocalDate fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public LocalDate getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(LocalDate fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public String getTipo_residencia() {
        return tipo_residencia;
    }

    public void setTipo_residencia(String tipo_residencia) {
        this.tipo_residencia = tipo_residencia;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    
    
    public boolean validarVigencia() {
        return tipo_doc.equalsIgnoreCase("DNI") && edad >= 60;
    }

    public boolean numDocValido() {
        if (tipo_doc.equalsIgnoreCase("DNI")) {
            return nro_doc.matches("\\d{8}");
        }

        if (tipo_doc.equalsIgnoreCase("CE")) {
            return nro_doc.matches("\\d{9}");
        }

        return false;
    }

    public boolean edadValida() {
        return edad >= 1 && edad <= 110;
    }

    public boolean documentoVigente(){
        if (validarVigencia()) {
            return true;
        }

        return LocalDate.now().isBefore(fecha_vencimiento);
    }
}
