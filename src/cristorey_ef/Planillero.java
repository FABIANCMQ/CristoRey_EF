/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

/**
 *
 * @author coolg
 */
public class Planillero extends Usuario{
    
    private String area_trabajo;

    public String getArea_trabajo() {
        return area_trabajo;
    }

    public void setArea_trabajo(String area_trabajo) {
        this.area_trabajo = area_trabajo;
    }

    public Planillero(String area_trabajo,String codigo_usuario, String cargo, String nombre, String correo, String clave_ingreso, Documento documento) {
        super(codigo_usuario, cargo, nombre, correo, clave_ingreso, documento);
        this.area_trabajo = area_trabajo;
    }
    
    public void asignarPaquete(Pasajero pasajero, PaqueteTuristico paqTuristico){
        if (paqTuristico.tieneCupos()==true) {
            paqTuristico.agregarPasajero(pasajero);
            System.out.println("Asignación realizada correctamente.");
        }else{
            System.out.println("No existen cupos disponibles");
        }
    }
    
    public boolean alertaReinscripcion(String dato, PasajeroControlador controlador){
        Pasajero psjr = controlador.buscarPasajero(dato);
        if (psjr != null) {
            System.out.println("ALERTA: Pasajero ya registrado.");
            return true;
        }
        return false;
    }
    
    public void listarPasajerosPaquete(PaqueteTuristico paquete){
        paquete.listarPasajeros();
    }
    
}
