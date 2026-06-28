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
        cargarUsuarios();
    }
    private void cargarUsuarios(){
        // Crearemos un Administrador
        Documento doc1 = new Documento("DNI", "12345678",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2032, 1, 1),
                "Nacional", 35);
        usuario.add(new Administrador("Administrativo", "Carlos Medina",
                "admin@cristorey.com", "admin123", doc1));
        
        //Creamos un planillero 
        Documento doc2 = new Documento("DNI", "87654321",
                LocalDate.of(2025, 2, 12),
                LocalDate.of(2033, 2, 12),
                "Nacional", 28);
        usuario.add(new Planillero("Operaciones", "Luis Torres",
                "planillero@cristorey.com", "plan123", doc2));
        
        //Crearemos un Guia Turistico
        Documento doc3 = new Documento("DNI", "44556677",
                LocalDate.of(2023, 3, 10),
                LocalDate.of(2031, 3, 10),
                "Nacional", 32);
        usuario.add(new GuiaTuristico("Recorridos Cajamarca", "Ana Robles",
                "guia@cristorey.com", "guia123", doc3));
        
        //Crearemos un Gerente
        Documento doc4 = new Documento("DNI", "11223344",
                LocalDate.of(2022, 4, 20),
                LocalDate.of(2030, 4, 20),
                "Nacional", 40);
        usuario.add(new Gerente("Gerencia General", "Ramirez Salazar",
                "gerente@cristorey.com", "gerente123", doc4));
        
        //Crearemos un asesor de ventas
        Documento doc5 = new Documento("DNI", "99887766",
                LocalDate.of(2024, 5, 18),
                LocalDate.of(2032, 5, 18),
                "Nacional", 26);
        usuario.add(new AsesorVentas("Sede Cajamarca", "Carlos Rojas",
                "asesor@cristorey.com", "asesor123", doc5));
    }
    
    public boolean registrarUsuario(Usuario nuevo_usuario){
        try {
            if (nuevo_usuario == null) {
                System.out.println("Ingresar datos. No se puede registrar un usuario vacio.");
                return false;
            }
            
            if (nuevo_usuario.getNombre() == null || nuevo_usuario.getNombre().trim().isEmpty()) {
                System.out.println("El nombre no puede estar vacio.");
                return false;
            }
            if (nuevo_usuario.getCorreo() == null || nuevo_usuario.getCorreo().trim().isEmpty()) {
                System.out.println("El correo no puede estar vacio.");
                return false;
            }

            if (nuevo_usuario.getClave_ingreso() == null || nuevo_usuario.getClave_ingreso().trim().isEmpty()) {
                System.out.println("La clave no puede estar vacia.");
                return false;
            }
            
            usuario.add(nuevo_usuario);
            System.out.println("Usuario registrado exitosamente");
            return true;
        
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: "+e.getMessage());
            return false;   
        }
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
