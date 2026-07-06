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

    public Administrador(String nivel_acceso, String codigo_usuario, String cargo, String nombre, String correo, String clave_ingreso, double sueldo, Documento documento) {
        super(codigo_usuario, cargo, nombre, correo, clave_ingreso, sueldo, documento);
        this.nivel_acceso = nivel_acceso;
    }
    
    public String getNivel_acceso() {
        return nivel_acceso;
    }

    public void setNivel_acceso(String nivel_acceso) {
        this.nivel_acceso = nivel_acceso;
    }
    
    
    
    public void bloquearUsuario(Usuario usuario){
        if (usuario instanceof Administrador) {
            return;
        }
        usuario.setBloqueado(true);
    }
    public void desbloquearUsuario(Usuario usuario){
        usuario.setBloqueado(false);
    }
}
