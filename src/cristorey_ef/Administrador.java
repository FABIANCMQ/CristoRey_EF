/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

/**
 *
 * @author coolg
 */
public class Administrador extends Usuario {
    private String nivel_acceso;

    public Administrador(String nivel_acceso, String nombre, String correo, String clave_ingreso, Documento documento) {
        super(nombre, correo, clave_ingreso, documento);
        this.nivel_acceso = nivel_acceso;
    }
    
    public String getNivel_acceso() {
        return nivel_acceso;
    }

    public void setNivel_acceso(String nivel_acceso) {
        this.nivel_acceso = nivel_acceso;
    }
    
    public void BloquearUsuario(Usuario usuario){
        usuario.setBloqueado(true);
        System.out.println("Usuario Bloqueado Exitosamente.");
    }
    public void desbloquearUsuario(Usuario usuario){
        usuario.setBloqueado(false);
        System.out.println("Usuario Desbloqueado Exitosamente.");
    }
    public void generarReportePasajero(PasajeroControlador controlador){
        System.out.println("\n===== REPORTE DE PASAJEROS =====");
        controlador.listarPasajero();
    }
    
    public void generarReportesPaquetes(PaqueteTuristicoControlador PaqControlador){
        System.out.println("\n===== REPORTE DE PAQUETES =====");
        PaqControlador.mostrarPaquetes();
    }
    public void verEstadisticas(PaqueteTuristicoControlador PaqControlador){
        int totalPasajeros = 0;
        for (int i = 0; i < PaqControlador.paquete.size(); i++) {
            PaqueteTuristico p = PaqControlador.paquete.get(i);
            totalPasajeros += p.contarPasajeros();
        }
        System.out.println("Total de pasajeros registrados: "+totalPasajeros);
    }
    public void controlViajesDiarios(PaqueteTuristicoControlador PaqControlador){
        for (int i = 0; i < PaqControlador.paquete.size(); i++) {
            PaqueteTuristico p = PaqControlador.paquete.get(i);
            
            System.out.println("\nPaquete: "+p.getNombre_paquete()+
                    "\nOcupación: "+p.porcentajeOcupacion());
        }
    }
    public void validarDatos(PasajeroControlador controlador){
        if (controlador.psjr.size()==0){
            System.out.println("No existen pasajeros registrados.");
        }
        else{
            System.out.println("Validacion Completada.");
        }
    }
    public void reporteControlMatutino(PaqueteTuristicoControlador PaqControlador){
        System.out.println("\n===== CONTROL MATUTINO =====");
        for (int i = 0; i < PaqControlador.paquete.size(); i++) {
            PaqueteTuristico p = PaqControlador.paquete.get(i);
            System.out.println(p.getNombre_paquete()+" -> "+p.getCupos_disponibles()+" cupos disponibles.");
        }
    }
    
    public void verDatos(){
        System.out.println("===DATOS ADMINISTRADOR===\nCodigo: "+this.codigo_usuario+
                "\nNombre: "+this.nombre+"\nCorreo: "+this.correo+"\nNivel de acceso: "+this.nivel_acceso+
                "\nDocumento: "+this.documento.getNro_doc());
    }
    
    @Override
    public boolean validarAcceso(String correo, String clave) {
        return this.correo.equals(correo) && this.clave_ingreso.equals(clave) && !this.bloqueado;
    }

    
}
