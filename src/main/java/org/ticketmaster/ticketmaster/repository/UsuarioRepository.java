package org.ticketmaster.ticketmaster.repository;

import org.mindrot.jbcrypt.BCrypt;
import org.ticketmaster.ticketmaster.model.Usuario;
import org.ticketmaster.ticketmaster.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepository {

    public boolean registrarUsuario(Usuario usuario) {
        // Ahora guardamos los 5 datos
        String sql = "INSERT INTO usuarios (username, correo, nombre, apellido, contrasena) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getCorreo());
            pstmt.setString(3, usuario.getNombre());
            pstmt.setString(4, usuario.getApellido());

            String contrasenaHasheada = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
            pstmt.setString(5, contrasenaHasheada);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar: " + e.getMessage());
            return false;
        }
    }

    // Le llamamos "identificador" porque puede ser el correo o el username
    public boolean validarCredenciales(String identificador, String passwordPlana) {
        // La clave está en buscar en ambas columnas separadas por un OR
        String sql = "SELECT contrasena FROM usuarios WHERE correo = ? OR username = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Pasamos el mismo texto a ambos signos de interrogación (?)
            pstmt.setString(1, identificador);
            pstmt.setString(2, identificador);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String contrasenaHasheadaDB = rs.getString("contrasena");
                return BCrypt.checkpw(passwordPlana, contrasenaHasheadaDB);
            }
            return false;

        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
            return false;
        }
    }

    public Usuario obtenerUsuario(String identificador) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? OR username = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, identificador);
            pstmt.setString(2, identificador);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getString("username"),
                        rs.getString("correo"),
                        rs.getString("contrasena"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}