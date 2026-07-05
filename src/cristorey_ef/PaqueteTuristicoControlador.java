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
public class PaqueteTuristicoControlador {
    ArrayList<PaqueteTuristico> paquete = new ArrayList();

    public PaqueteTuristicoControlador(){
        cargarPaquetes();
    }
    
    private void cargarPaquetes(){
        paquete.add(
            new PaqueteTuristico("Granja Porcon","CRT-001","Huambocancha - Parque Forestal - Cooperativa Atahualpa Jerusalen - Mini Zoologico", 20.0,
            "9:30 am - 2:00 pm",30,30));    
        paquete.add( new PaqueteTuristico("Namora","CRT-002","Alameda de los Incas - Los Sapitos - Talleres de Guitarras - Laguna San Nicolas",
                20.0,"9:30 am - 3:00 pm", 30,30));
        paquete.add(new PaqueteTuristico("Cumbe Mayo","CRT-003","Mirador de Bellavista - Bosque de Piedras - Tunel de los Deseos - Canal de Cumbemayo",
                20.0,"9:30 am - 2:00 pm",30,30));

        paquete.add(new PaqueteTuristico("Castillo de Yanamarca", "CRT-004",
                "Castillo de Yanamarca - Cataratas de Llacanora",
                20.0, "3:30 pm - 7:00 pm", 30, 30));
        
        paquete.add(new PaqueteTuristico("Colpa", "CRT-005",
                "Ex Hacienda La Colpa - Laguna Artificial - Artesanos de Mollepampa",
                20.0, "3:30 pm - 7:00 pm", 30, 30));
        
        paquete.add(new PaqueteTuristico("Otuzco", "CRT-006",
                "Ventanillas de Otuzco - Puente Colgante - Jardin Botanico - Fundo Tres Molinos",
                20.0, "3:30 pm - 7:00 pm", 30, 30));
    }
    
    public ArrayList<PaqueteTuristico> getPaquete() {
        return paquete;
    }
    
    public PaqueteTuristico buscarNombre(String nombre){
        for (int i = 0; i < paquete.size(); i++) {
            if (paquete.get(i).getNombre_paquete().equalsIgnoreCase(nombre)) {
                return paquete.get(i);
            }   
        }
        return null;
    }
    
    public PaqueteTuristico buscarDestino(String destino){
        for (int i = 0; i < paquete.size(); i++) {
            if (paquete.get(i).getDestino().equalsIgnoreCase(destino)) {
                return paquete.get(i);
            }   
        }
        return null;
    }
    
    public PaqueteTuristico buscarCodigo(String codigo){
        for (int i = 0; i < paquete.size(); i++) {
            if (paquete.get(i).getCodigo_paquete().equalsIgnoreCase(codigo)) {
                return paquete.get(i);
            }   
        }
        return null;
    }
}
