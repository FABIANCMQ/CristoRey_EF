/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

/**
 *
 * @author coolg
 */
public class Sesiones {
    
    private Usuario usuario_actual;
    private boolean sesion_activa;

    public Sesiones(Usuario usuario_actual, boolean sesion_activa) {
        this.usuario_actual = usuario_actual;
        this.sesion_activa = sesion_activa;
    }

    public Usuario getUsuario_actual() {
        return usuario_actual;
    }

    public void setUsuario_actual(Usuario usuario_actual) {
        this.usuario_actual = usuario_actual;
    }

    public boolean isSesion_activa() {
        return sesion_activa;
    }

    public void setSesion_activa(boolean sesion_activa) {
        this.sesion_activa = sesion_activa;
    }
    
    public boolean iniciarSesion(Usuario user){
        if (user == null) {
            System.out.println("No fue posible iniciar sesion.");
            return false; 
        }
        
        usuario_actual = user;
        sesion_activa = true;
        
        System.out.println("Bienvenido: "+user.getNombre());
        return true;
    }
    
    public void cerrarSesion(){
        if (!sesion_activa) {
            System.out.println("No hay sesiones activas.");
            return;
        }
        
        System.out.println("Hasta luego. "+usuario_actual.getNombre());
        
        usuario_actual = null;
        sesion_activa = false;
    }
    
    public void mostrarSesionActual(){
        if (!sesion_activa) {
            System.out.println("No hay sesiones activas.");
            return;
        }
        
        System.out.println("Usuario actual: "+usuario_actual.getNombre());
    }
}
