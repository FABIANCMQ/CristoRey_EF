/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

/**
 *
 * @author coolg
 */
public class Pasajero {
    private String ap_paterno;
    private String ap_materno;
    private String nombre;
    private String telefono;
    private String correo;
    
    private Documento documento;

    public Pasajero(String ap_paterno, String ap_materno, String nombre, String telefono, String correo, Documento documento) {
        this.ap_paterno = ap_paterno;
        this.ap_materno = ap_materno;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.documento = documento;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
    
    
    public String getAp_paterno() {
        return ap_paterno;
    }

    public void setAp_paterno(String ap_paterno) {
        this.ap_paterno = ap_paterno;
    }

    public String getAp_materno() {
        return ap_materno;
    }

    public void setAp_materno(String ap_materno) {
        this.ap_materno = ap_materno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String unificarDatos(){
        return nombre + " "+ap_paterno+" "+ap_materno;
    }
    
    public void verDatos(){
        System.out.println("Pasajero:\nApellido Paterno: "+this.ap_paterno+
                "\tApellido Materno: "+this.ap_materno+
                "\nNombre: "+this.nombre+"\tTelefono: "+this.telefono+
                "\nCorreo Electronico: "+this.correo);
    }
}
