/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

import java.util.ArrayList;

/**
 *
 * @author coolg
 */
public class AuditoriaControlador {
    ArrayList<Auditoria> audi = new ArrayList();
    
    public void registrarAuditoria(Usuario user, String accion, String detalle){
        try {
            if (user == null) {
                System.out.println("No existe usuario. No se puede crear una auditoria");
                return;
            }
            if (accion == null || accion.trim().isEmpty()) {
                System.out.println("La accion no puede estar vacia.");
                return;
            }
            if (detalle == null || detalle.trim().isEmpty()) {
                System.out.println("El detalle no pede estar vacio.");
                return;
            }
            
            Auditoria a = new Auditoria(user.getNombre(), accion, detalle, "Fecha generada por el sistema");
            
            audi.add(a);
            System.out.println("Auditoria registrada correctamente");
            
        } catch (Exception e) {
            System.out.println("Error al registrar auditoria: "+e.getMessage());
        }
    }
    
    public void listarAuditoria(){
        if (audi.size() == 0) {
            System.out.println("No existen registros de auditoria.");
        }else{
            for (int i = 0; i < audi.size(); i++) {
                audi.get(i).verAuditoria();
            }
        }
    }
}
