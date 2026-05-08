package org.ticketmaster.ticketmaster.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    // Asegúrate de que el nombre "ticketmaster_demo" coincida con tu base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/ticketmaster_demo";
    private static final String USER = "root"; // Tu usuario de MySQL (por defecto es root)
    private static final String PASSWORD = "Diego1164$"; // Tu contraseña de MySQL (déjalo en blanco si usas XAMPP)

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}