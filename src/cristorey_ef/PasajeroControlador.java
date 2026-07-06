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
public class PasajeroControlador {
    ArrayList<Pasajero> pasajero = new ArrayList();
    
    public void registrarPasajero(Pasajero nuevo_pasajero){
        pasajero.add(nuevo_pasajero);
    }
    
    public ArrayList<Pasajero> getPasajero() {
        return pasajero;
    }
    
    public Pasajero buscarDocumento(String nroDoc){
        for (int i = 0; i < pasajero.size(); i++) {
            if (pasajero.get(i).getDocumento().getNro_doc().equalsIgnoreCase(nroDoc)) {
                return pasajero.get(i);
            }
        }
        return null;
    }
    
    public Pasajero buscarPasajero(String dato){
        if (dato == null) {
            return null;
        }
        String q = dato.trim().toLowerCase();
        if (q.isEmpty()) {
            return null;
        }

        for (int i = 0; i < pasajero.size(); i++) {
            Pasajero p = pasajero.get(i);

            boolean coincideNombre = p.unificarDatos().toLowerCase().contains(q);

            boolean coincideDocumento = p.getDocumento() != null
                    && p.getDocumento().getNro_doc() != null
                    && p.getDocumento().getNro_doc().equalsIgnoreCase(dato.trim());

            if (coincideNombre || coincideDocumento) {
                return p;
            }
        }
        return null;
    }
}
