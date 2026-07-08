/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class ConexionBD {

    private static final String URL = "jdbc:sqlserver://localhost:1433;"
            + "databaseName=CristoReyDB;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";

    private static final String USER = "sa";
    private static final String PASSWORD = "1234";

    public static Connection conectar() {
        Connection con = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a CristoReyBD");

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "No se encontró el driver JDBC de SQL Server:\n" + e.getMessage(),
                    "Error de Driver",
                    JOptionPane.ERROR_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al conectar con SQL Server:\n" + e.getMessage(),
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
        }

        return con;
    }
}
