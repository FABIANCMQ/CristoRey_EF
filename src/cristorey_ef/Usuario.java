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
    private String codigo_usuario;
    private String nombre;
    private String correo;
    private String clave_ingreso;
    private boolean bloquedo;

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

    public Usuario() {
        int contador = 0;
        this.codigo_usuario = "U" + String.format("%05d", ++contador);
    }
    
    
    
    public void identificarUsuario(){
        System.out.println("===IDENTIFICACION DEL USUARIO===}"
                + "\nCodigo Usuario: "+this.codigo_usuario+"\nNombre: "+this.nombre);
    }
    public void IniciarSesion(){
        System.out.println("===INICIO DE SESION==="
                + "\nNombre: "+nombre+""
                + "\nCorreo: "+correo+""
                + "\nClave: "+clave_ingreso);
    }
    public void bloquearEdicion(String rol){
        if(rol.equalsIgnoreCase("Administrador")){
            this.bloquedo=true;
            System.out.println("Usuario bloqueado exitosamente.");
        }else{
            System.out.println("Acceso Denegado. Solo el administrador puede acceder a esta función.");
        }
    }
    public void desbloquearEdicion(String rol){
        if (rol.equalsIgnoreCase("Administrador")) {
            this.bloquedo=false;
            System.out.println("Usuario desbloqueado exitosamente");
        }else{
            System.out.println("Acceso Denegado. Solo el administrador puede acceder a esta función.");
        }
    }
    public boolean puedeEditar(){
        return !this.bloquedo;
    } 
    
    public abstract void validarAcceso(String correo, String clave);
}
