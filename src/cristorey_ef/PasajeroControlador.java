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
    public void listarPasajero(){
        for(int i=0; i<pasajero.size();i++){
            pasajero.get(i).verDatos();
        }
    }
    
    public ArrayList<Pasajero> getPasajero() {
        return pasajero;
    }
    
    public Pasajero buscarNombre(String nombre){
        for (int i = 0; i < pasajero.size(); i++) {
            if (pasajero.get(i).getNombre().equalsIgnoreCase(nombre)) {
                return pasajero.get(i);
            }
        }
        return null;
    }
    
    public Pasajero buscarDocumento(String nrodoc){
        for (int i = 0; i < pasajero.size(); i++) {
            if (pasajero.get(i).getDocumento().getNro_doc().equalsIgnoreCase(nrodoc)) {
                return pasajero.get(i);
            }
        }
        return null;
    }
}
