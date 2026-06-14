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
    public void registrarPasajero(Pasajero nuevo_estudiante){
        psjr.add(nuevo_estudiante);
    }
    public void listarPasajero(){
        for(int i=0; i<psjr.size();i++){
            psjr.get(i).verDatos();
        }
    }
}
