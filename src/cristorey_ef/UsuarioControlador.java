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
    
    private static int contador = 0;

    public UsuarioControlador() {
        cargarUsuarios();
    }
    private void cargarUsuarios(){
        // Crearemos un Administrador
        Documento doc1 = new Documento("DNI", "12345678",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2032, 1, 1),
                "Nacional", 35);
        usuario.add(new Administrador("Adsoluto",
                "U" + String.format("%05d", ++contador),
                "Administrador",
                "Mariela Medina",
                "admin@cristorey.com",
                "admin123",
                3500.0,
                doc1));
        
        //Creamos un planillero 
        Documento doc2 = new Documento("DNI", "87654321",
                LocalDate.of(2025, 2, 12),
                LocalDate.of(2033, 2, 12),
                "Nacional", 28);
        usuario.add(new Planillero("Operaciones",
                "U" + String.format("%05d", ++contador),
                "Planillero",
                "Diego Torres",
                "planillero@cristorey.com",
                "plan123",
                1800.0,
                doc2));
        
        //Crearemos un Guia Turistico
        Documento doc3 = new Documento("DNI", "44556677",
                LocalDate.of(2023, 3, 10),
                LocalDate.of(2031, 3, 10),
                "Nacional", 32);
        usuario.add(new GuiaTuristico(null,
                "U" + String.format("%05d", ++contador),
                "Guía turistico" ,
                "Lucía Robles",
                "guia@cristorey.com",
                "guia123",
                1600.0,
                doc3));
        
        //Crearemos un Gerente
        Documento doc4 = new Documento("DNI", "11223344",
                LocalDate.of(2022, 4, 20),
                LocalDate.of(2030, 4, 20),
                "Nacional", 40);
        usuario.add(new Gerente("Gerencia General",
                "U" + String.format("%05d", ++contador),
                "Gerente", 
                "Mike Ramirez",
                "gerente@cristorey.com", 
                "gerente123", 
                5500.0,
                0,
                doc4));
        
        //Crearemos un asesor de ventas
        Documento doc5 = new Documento("DNI", "99887766",
                LocalDate.of(2024, 5, 18),
                LocalDate.of(2032, 5, 18),
                "Nacional", 26);
        usuario.add(new AsesorVentas("Sede Cajamarca",
                "U" + String.format("%05d", ++contador),
                "Asesor de ventas",
                "Carlos Rojas",
                "asesor@cristorey.com", 
                "asesor123",
                2200.0,
                doc5));
    }
    
    public ArrayList<Usuario> getUsuario() {
        return usuario;
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
            
            if (buscarCorreo(nuevo_usuario.getCorreo()) != null) {
                System.out.println("Ya existe un usuario con ese correo.");
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
    
    public Usuario buscarCorreo(String correo){
        for (int i = 0; i < usuario.size(); i++) {
            if (usuario.get(i).getCorreo().equalsIgnoreCase(correo)) {
                return usuario.get(i);
            }
        }
        return null;
    }

    public Usuario buscarNombre(String nombre){
        for (int i = 0; i < usuario.size(); i++) {
            if (usuario.get(i).getNombre().equalsIgnoreCase(nombre)) {
                return usuario.get(i);
            }
        }
        return null;
    }
    
    public Usuario buscarCargo(String cargo){
        for (int i = 0; i < usuario.size(); i++) {
            if (usuario.get(i).getCargo().equalsIgnoreCase(cargo)) {
                return usuario.get(i);
            }
        }
        return null;
    }
    
    public Usuario buscarCodigo(String codigo) {
        for (int i = 0; i < usuario.size(); i++) {
            if (usuario.get(i).getCodigo_usuario().equalsIgnoreCase(codigo)) {
                return usuario.get(i);
            }
        }
        return null;
    }
    
    public boolean asignarSueldo(String codigo, double nuevoSueldo) {
        Usuario user = buscarCodigo(codigo);
        if (user != null) {
            user.setSueldo(nuevoSueldo);
            return true;
        }
        return false;
    }
    
    public String obtenerRol(Usuario user){
        if (user instanceof Administrador) {
            return "Administrador";
        }else if (user instanceof Planillero) {
            return "Planillero";
        }else if (user instanceof GuiaTuristico) {
            return "Guia Turistico";
        }else if (user instanceof Gerente) {
            return "Gerente";
        }else if (user instanceof AsesorVentas) {
            return "Asesor de Ventas";
        }else{
            return "Usuario";
        }
    }
    
}
