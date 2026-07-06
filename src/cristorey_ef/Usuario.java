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
    protected String cargo;
    protected String nombre;
    protected String correo;
    protected String clave_ingreso;
    protected double sueldo;
    protected boolean bloqueado;
    
    public Documento documento;

    public Usuario(String codigo_usuario, String cargo, String nombre, String correo, String clave_ingreso, double sueldo, Documento documento) {
        this.codigo_usuario = codigo_usuario;
        this.cargo = cargo;
        this.nombre = nombre;
        this.correo = correo;
        this.clave_ingreso = clave_ingreso;
        this.sueldo = sueldo;
        this.documento = documento;
        this.bloqueado = false;
    }
    
    

    public String getCodigo_usuario() {
        return codigo_usuario;
    }

    public void setCodigo_usuario(String codigo_usuario) {
        this.codigo_usuario = codigo_usuario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
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

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
    
    
    public boolean validarAcceso(String correo, String clave) {
        return this.correo.equals(correo) && this.clave_ingreso.equals(clave);
    }
}
