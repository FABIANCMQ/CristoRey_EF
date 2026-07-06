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
    private double meta_venta_mensual;
    
    public Gerente(String area_gerencia, String codigo_usuario, String cargo, String nombre, String correo, String clave_ingreso, double sueldo,double meta_venta_mensual, Documento documento) {
        super(codigo_usuario, cargo, nombre, correo, clave_ingreso, sueldo, documento);
        this.area_gerencia = area_gerencia;
        this.meta_venta_mensual = meta_venta_mensual;
    }

    public String getArea_gerencia() {
        return area_gerencia;
    }

    public void setArea_gerencia(String area_gerencia) {
        this.area_gerencia = area_gerencia;
    }

    public double getMeta_venta_mensual() {
        return meta_venta_mensual;
    }

    public void setMeta_venta_mensual(double meta_ventas_mensual) {
        this.meta_venta_mensual = meta_ventas_mensual;
    }
}
