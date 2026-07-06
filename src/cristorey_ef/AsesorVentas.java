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

    public AsesorVentas(String sede,String codigo_usuario, String cargo, String nombre, String correo, String clave_ingreso,double sueldo, Documento documento) {
        super(codigo_usuario, cargo, nombre, correo, clave_ingreso, sueldo, documento);
        this.sede = sede;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }
}
