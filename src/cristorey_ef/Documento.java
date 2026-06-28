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
        if(tipo_doc.equalsIgnoreCase("DNI")
           ||tipo_doc.equalsIgnoreCase("CE")){
            this.tipo_doc = tipo_doc;
        }else{
            System.out.println("Tipo de documento invalido.");
        }
        
    }

    public String getNro_doc() {
        return nro_doc;
    }

    public void setNro_doc(String nro_doc) {
        if(tipo_doc.equalsIgnoreCase("DNI") && nro_doc.length()==8){
            this.nro_doc = nro_doc;
        }else if (tipo_doc.equalsIgnoreCase("CE") && nro_doc.length()==9){
            this.nro_doc = nro_doc;
        }else{
            System.out.println("Número de documento invalido para "+this.nro_doc);
        }
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
        if(edad < 1 || edad > 100){
            System.out.println("Edad invalida");
        }else{
            this.edad = edad;
        }
    }


    public void validarVigencia(){
        LocalDate fecha_actual = LocalDate.now();
        
        if(tipo_doc.equalsIgnoreCase("DNI") && edad >= 60){
            System.out.println("Estado: VIGENTE - NO CADUCA");
        }else if (fecha_actual.isBefore(fecha_vencimiento)) {
            System.out.println("Estado: VIGENTE");
        }else{
            System.out.println("Estado: VENCIDO");
        }
    }
    
    public boolean documentoVigente(){
        LocalDate fecha_actual = LocalDate.now();
        
        if (tipo_doc.equalsIgnoreCase("DNI") && edad >= 60) {
            return true;
        }
        if (fecha_vencimiento == null) {
            return false;   
        }
        
        return fecha_actual.isBefore(fecha_vencimiento);
    }
    
    public boolean documentacionCompleta(){
        if (tipo_doc == null || tipo_doc.trim().isEmpty()) {
            return false;
        }
        if (nro_doc == null || nro_doc.trim().isEmpty()) {
            return false;
        }
        if (fecha_emision == null || fecha_vencimiento == null) {
            return false;
        }
        return true;
    }
    
}
