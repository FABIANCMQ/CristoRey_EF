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
    ArrayList<Pasajero> psjr = new ArrayList();
    public void registrarPasajero(Pasajero nuevo_pasajero){
        psjr.add(nuevo_pasajero);
    }
    public void listarPasajero(){
        for(int i=0; i<psjr.size();i++){
            psjr.get(i).verDatos();
        }
    }
    
    public Pasajero buscarNombre(String nombre){
        for (int i = 0; i < psjr.size(); i++) {
            if (psjr.get(i).getNombre().equalsIgnoreCase(nombre)) {
                return psjr.get(i);
            }
        }
        return null;
    }
    
    public Pasajero buscarDocumento(String nrodoc){
        for (int i = 0; i < psjr.size(); i++) {
            if (psjr.get(i).getDocumento().getNro_doc().equalsIgnoreCase(nrodoc)) {
                return psjr.get(i);
            }
        }
        return null;
    }
}
