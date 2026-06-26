/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

/**
 *
 * @author coolg
 */
public class AsesorVentas extends Usuario{
    
    private String sede;    

    public AsesorVentas(String sede, String nombre, String correo, String clave_ingreso, Documento documento) {
        super(nombre, correo, clave_ingreso, documento);
        this.sede = sede;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }
    
    public void consultarCatalogo(PaqueteTuristicoControlador controlador){
        System.out.println("=== CATALOGO DE PAQUETES TURISTICOS ===");
        controlador.mostrarPaquetes();
    }
    
    public void verPaqueteSeleccionado(PaqueteTuristico paquete){
        if (paquete == null) {
            System.out.println("No se encontro el paquete seleccionado");
        }else{
            paquete.mostrarInfo();
        }
    }
    
    public void buscarViajePorDestino(PaqueteTuristicoControlador controlador, String destino){
        boolean encontrado = false;
        
        if (destino == null || destino.trim().isEmpty()) {
            System.out.println("Debe ingresar un destino para realizarla busqueda");
            return;
        }
        
        for (int i = 0; i < controlador.getPaquete().size(); i++) {
            PaqueteTuristico p = controlador.getPaquete().get(i);
            
            if (p.getDestino().toLowerCase().contains(destino.toLowerCase()) || 
                    p.getNombre_paquete().toLowerCase().contains(destino.toLowerCase())) {
                p.mostrarInfo();
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontraron viajes relacionados  con el destino ingresado.");
        }
    }
    
    public void procesarVenta(Pasajero pasajero, PaqueteTuristico paquete, ReservaControlador controlador_reserva, double descuento){
        controlador_reserva.registrarReserva(pasajero, paquete, descuento);
    }
    
    public void consultarHistoriaCliente(ReservaControlador controlador_resreva, String nro_doc){
        if (nro_doc == null || nro_doc.trim().isEmpty()) {
            System.out.println("Debe ingresar un numero de documento.");
            return;
        }
        
        controlador_resreva.historialCliente(nro_doc);
    }
    
    public void cancelarReserva(ReservaControlador controlador_reserva, String codigo_reserva){
        if (codigo_reserva == null || codigo_reserva.trim().isEmpty()) {
            System.out.println("Debe ingresar el codigo de reserva");
            return;
        }
        
        controlador_reserva.cancelarReserva(codigo_reserva);
    }
    
    public void verDatos(){
        System.out.println("===DATOS DEL ASESOR DE VENTAS===\nCodigo: "+this.codigo_usuario+
                "\nNombre: "+this.nombre+"\nCorreo: "+this.correo+"\nSede: "+this.sede);
    }

    @Override
    public boolean validarAcceso(String correo, String clave) {
        return this.correo.equals(correo) && this.clave_ingreso.equals(clave) && !this.bloqueado;
    }
    
}
