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
    ArrayList<Usuario> usuario = new ArrayList();

    public UsuarioControlador() {
        usuario = new ArrayList<>();
        cargarUsuarios();
    }
    private void cargarUsuarios(){
        // Crearemos un Administrador
        
        Documento doc1 = new Documento("DNI","12345678",LocalDate.of(2024, 1, 1),
        LocalDate.of(2032, 1, 1),"Nacional",35);
        usuario.add(new Administrador("Administrativo",
                "Carlos Medina",
                "admin@cristorey.com",
                "admin123",
                doc1
            )
        );
        
        //Creamos un planillero 
        Documento doc2 = new Documento("DNI","87654321",LocalDate.of(2025, 2, 12),
        LocalDate.of(2033, 2, 12),"Nacional",28);

        usuario.add(
            new Planillero(
                "Operaciones",
                "Luis Torres",
                "planillero@cristorey.com",
                "plan123",
                doc2
            )
        );
        
    }

    public ArrayList<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(ArrayList<Usuario> usuario) {
        this.usuario = usuario;
    }
    public Usuario buscarNombre(String nombre){
        for (int i = 0; i < usuario.size(); i++) {
            if (usuario.get(i).getNombre().equalsIgnoreCase(nombre)) {
                return usuario.get(i);
            }
        }
        return null;
    }
}
