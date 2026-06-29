/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

/**
 *
 * @author coolg
 */
public class Auditoria {
    private String usuario;
    private String accion;
    private String detalle;
    private String fecha;

    public Auditoria(String usuario, String accion, String detalle, String fecha) {
        this.usuario = usuario;
        this.accion = accion;
        this.detalle = detalle;
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public void verAuditoria(){
        System.out.println("===REGISTRO AUDITORIA===\nUsuario: "+this.usuario+
                "\nAccion: "+this.accion+"\nDetalle: "+this.detalle+"\nFecha: "+this.fecha);
    }
    
}
