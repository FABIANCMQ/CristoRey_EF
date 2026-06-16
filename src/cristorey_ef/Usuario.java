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
    protected boolean bloqueado;
    private static int contador =0;
    
    protected Documento documento;

    public Usuario(String nombre, String correo, String clave_ingreso, Documento documento) {
        this.codigo_usuario ="U" + String.format("%05d", ++contador);
        this.nombre = nombre;
        this.correo = correo;
        this.clave_ingreso = clave_ingreso;
        this.documento = documento;
        this.bloqueado = false;
    }
    
    

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

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public int getContador() {
        return contador;
    }
    
    
    
    public void IniciarSesion(){
        System.out.println("Bienvenido " + nombre);
    }
    
    public void identificarUsuario(){
        System.out.println("===IDENTIFICACION DEL USUARIO===\nCodigo Usuario: "
                +this.codigo_usuario+"\nNombre: "+this.nombre);
    }
   
    public void bloquearEdicion() {
        this.bloqueado = true;
    }
    public boolean puedeEditar(){
        return !this.bloqueado;
    } 
    
    public abstract boolean validarAcceso(String correo, String clave);
    
}
