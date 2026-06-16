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

    public Planillero(String area_trabajo, String nombre, String correo, String clave_ingreso, Documento documento) {
        super(nombre, correo, clave_ingreso, documento);
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
    
    public boolean alertaReinscripcion(String nrodoc, PasajeroControlador controlador){
        Pasajero psjr = controlador.buscarDocumento(nrodoc);
        if (psjr != null) {
            System.out.println("ALERTA: Pasajero ya registrado.");
            return true;
        }
        return false;
    }
    public void verDatos(){
        System.out.println("\n=== DATOS DEL PLANILLERO ===\nCódigo : " + this.codigo_usuario
        +"\nNombre : " + this.nombre+"\nCorreo : " + this.correo+"Área   : " + this.area_trabajo);
    }
    public void consultarPaquetes(PaqueteTuristico paquete){
        paquete.mostrarInfo();;
    }
    
    public void listarPasajerosPaquete(PaqueteTuristico paquete){
        paquete.listarPasajeros();
    }

    @Override
    public boolean validarAcceso(String correo, String clave) {
        return this.correo.equals(correo) && this.clave_ingreso.equals(clave)
          && !this.bloqueado;
    }
    
}
