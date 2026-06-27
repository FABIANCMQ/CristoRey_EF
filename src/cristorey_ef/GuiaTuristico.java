/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

/**
 *
 * @author coolg
 */
public class GuiaTuristico extends Usuario{
    
    private String recorrido_asignado;

    public GuiaTuristico(String recorrido_asignado, String nombre, String correo, String clave_ingreso, Documento documento) {
        super(nombre, correo, clave_ingreso, documento);
        this.recorrido_asignado = recorrido_asignado;
    }

    public String getRecorrido_asignado() {
        return recorrido_asignado;
    }

    public void setRecorrido_asignado(String recorrido_asignado) {
        this.recorrido_asignado = recorrido_asignado;
    }
    
    public void verDatos(){
        System.out.println("===DATOS GUÍA TURISTICO===\nCodigo usuario: "+this.codigo_usuario+"\nNombre: "+this.nombre+
                "Correo: "+this.correo+"\nRecorrido Asignado: "+this.recorrido_asignado);
    }
    public void consultarListaParticipantes(PaqueteTuristico paquete){
        try {
            if (paquete==null) {
                System.out.println("No se encontró el recorrido turístico");
                return;
            }
            
            System.out.println("\n===LISTA DE PARTICIPANTES DEL RECORRIDO===\nPaquete: "+paquete.getNombre_paquete()
                    +"\nHorario: "+paquete.getHorario());
            
            if (paquete.getListaPasajeros().size()==0) {
                System.out.println("No existen pasajeros asignados a este recorrido");
                return;
            }else{
                paquete.listarPasajeros();
            }
        } catch (Exception e) {
            System.out.println("Error al consultar lista de participantes: "+e.getMessage());
        }
    }
    
    public void verificarLista(PaqueteTuristico paquete){
        try {
            if (paquete==null) {
                System.out.println("No se encontro el paquete turistico");
                return;
            }
            
            if (paquete.getListaPasajeros() == null) {
                System.out.println("No existe lista de pasajeros");
                return;
            }
            
            System.out.println("===VERIFICACION DE LISTA===");
            if (paquete.getListaPasajeros().size() == paquete.contarPasajeros()) {
                System.out.println("Lista actualizada y funcionando correctamente");
            }else{
                System.out.println("Se detectaron inconsistencias en la lista.");
            }
        } catch (Exception e) {
            System.out.println("Error al verificar la lista: "+e.getMessage());
        }
    }
    
    public void generarListaPreviaViaje(PaqueteTuristico paquete){
        try {
            if (paquete == null) {
                System.out.println("No se encontró el paquete turistico");
                return;
            }
            
            System.out.println("===LISTA PREVIA AL VIAJE===\nRecorrido: "+paquete.getNombre_paquete()+
                    "\nDestino: "+paquete.getDestino()+"\nHorario: "+paquete.getHorario()+
                    "\nTotal Inscritos: "+paquete.contarPasajeros());
            
            if (paquete.getListaPasajeros().size()==0) {
                System.out.println("No hay pasajeros registrados para este viaje");
            }else{
                paquete.listarPasajeros();
            }
        } catch (Exception e) {
            System.out.println("Error al generar lista previa: "+e.getMessage());
        }
    }
    
    public void modificarPasajero(){
        System.out.println("Accion no autorizada para su rol");
    }
    @Override
    public boolean validarAcceso(String correo, String clave) {
        return this.correo.equals(correo) && 
                this.clave_ingreso.equals(clave)
                && !this.bloqueado;
    }
    
}
