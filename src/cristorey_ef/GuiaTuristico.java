/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

/**
 *
 * @author coolg
 */
public class GuiaTuristico extends Usuario{
    
    private String recorrido_asignado;
    private String recorrido_solicitado;

    public GuiaTuristico(String recorrido_asignado,String codigo_usuario, String cargo, String nombre, String correo, String clave_ingreso, Documento documento) {
        super(codigo_usuario, cargo, nombre, correo, clave_ingreso, documento);
        this.recorrido_asignado = recorrido_asignado;
    }

    public String getRecorrido_asignado() {
        return recorrido_asignado;
    }

    public void setRecorrido_asignado(String recorrido_asignado) {
        this.recorrido_asignado = recorrido_asignado;
    }

    public String getRecorrido_solicitado() {
        return recorrido_solicitado;
    }
    
    public boolean tieneAsignacion() {
        return recorrido_asignado != null && !recorrido_asignado.isEmpty();
    }

    public boolean tieneSolicitudPendiente() {
        return recorrido_solicitado != null && !recorrido_solicitado.isEmpty();
    }
    
    public boolean solicitarAsignacion(String codigoPaquete) {
        if (tieneAsignacion()) {
            return false;
        }
        if (tieneSolicitudPendiente()) {
            return false;
        }
        
        this.recorrido_solicitado = codigoPaquete;
        
        return true;
    }
    
    public void limpiarSolicitud() {
        this.recorrido_solicitado = null;
    }
}
