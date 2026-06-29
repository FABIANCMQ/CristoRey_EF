/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

/**
 *
 * @author coolg
 */
public class PermisosControlador {
    public boolean modificarPasajero(Usuario user){
        if (user instanceof Administrador) {
            return true;
        }else if (user instanceof Planillero) {
            return true;
        }else{
            System.out.println("Accion no autorizada para su rol.");
            return false;
        }
    }
    
    public boolean verReportes(Usuario user){
        if (user instanceof Administrador || user instanceof Gerente) {
            return true;
        } else{
            System.out.println("Accion no autorizada para su rol.");
            return false;
        }
    }
    
    public boolean registrarVentas(Usuario user){
        if (user instanceof AsesorVentas) {
            return true;
        }else{
            System.out.println("Accion no autorizada para su rol.");
            return false;
        }
    }
}
