/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cristorey_ef;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author coolg
 */
public class UsuarioControlador {
    private ArrayList<Usuario> usuario;

    public UsuarioControlador() {
        usuario = new ArrayList<>();
        
    }
    private void cargarUsuarios(){
        // Crearemos un Administrador
        
        Documento doc1 = new Documento("DNI","98765432",LocalDate.of(2024,1,1),LocalDate.of(2032,1,1),"Nacional",35);
        
    }
    
}
