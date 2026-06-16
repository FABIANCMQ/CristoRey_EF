/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

/**
 *
 * @author coolg
 */
public class Administrador extends Usuario {
    private String nivel_acceso;

    public Administrador(String nivel_acceso, String codigo_usuario, String nombre, String correo, String clave_ingreso, boolean bloquedo, Documento documento) {
        super(codigo_usuario, nombre, correo, clave_ingreso, bloquedo, documento);
        this.nivel_acceso = nivel_acceso;
    }

    public String getNivel_acceso() {
        return nivel_acceso;
    }

    public void setNivel_acceso(String nivel_acceso) {
        this.nivel_acceso = nivel_acceso;
    }
    
    public void BloquearUsuario(Usuario usuario){
        usuario.setBloquedo(true);
        System.out.println("Usuario Bloqueado Exitosamente.");
    }
    public void desbloquearUsuario(Usuario usuario){
        usuario.setBloquedo(false);
        System.out.println("Usuario Desbloqueado Exitosamente.");
    }
    
    @Override
    public boolean validarAcceso(String correo, String clave) {
        return this.correo.equals(correo) && this.clave_ingreso.equals(clave) && !this.bloquedo;
    }

    
}
