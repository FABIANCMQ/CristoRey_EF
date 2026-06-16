/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

/**
 *
 * @author coolg
 */
public abstract class Usuario {
    protected String codigo_usuario;
    protected String nombre;
    protected String correo;
    protected String clave_ingreso;
    protected boolean bloquedo;
    private static int contador =0;
    
    protected Documento documento;

    public String getCodigo_usuario() {
        return codigo_usuario;
    }

    public void setCodigo_usuario(String codigo_usuario) {
        this.codigo_usuario = codigo_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave_ingreso() {
        return clave_ingreso;
    }

    public void setClave_ingreso(String clave_ingreso) {
        this.clave_ingreso = clave_ingreso;
    }

    public boolean isBloquedo() {
        return bloquedo;
    }

    public void setBloquedo(boolean bloquedo) {
        this.bloquedo = bloquedo;
    }

    public Usuario(String codigo_usuario, String nombre, String correo, String clave_ingreso, boolean bloquedo, Documento documento) {
        this.codigo_usuario ="U" + String.format("%05d", ++contador);
        this.nombre = nombre;
        this.correo = correo;
        this.clave_ingreso = clave_ingreso;
        this.bloquedo = bloquedo;
        this.documento = documento;
    }

    public void identificarUsuario(){
        System.out.println("===IDENTIFICACION DEL USUARIO===}"
                + "\nCodigo Usuario: "+this.codigo_usuario+"\nNombre: "+this.nombre);
    }
    public void IniciarSesion(){
        System.out.println("Bienvenido " + nombre);
    }
    public void bloquearEdicion(){
           this.bloquedo = true;
    }
    public boolean puedeEditar(){
        return !this.bloquedo;
    } 
    
    public abstract boolean validarAcceso(String correo, String clave);
}
