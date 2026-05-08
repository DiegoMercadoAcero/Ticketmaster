package org.ticketmaster.ticketmaster.repository;

import org.ticketmaster.ticketmaster.model.Evento;
import org.ticketmaster.ticketmaster.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventoRepository {

    // Ahora le pasamos la ubicación (ej. "Cineteca Nacional") y el tipo (ej. "Cine")
    public List<Evento> obtenerEventos(String ubicacion, String tipoEvento) {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM eventos WHERE ubicacion = ? AND tipo_evento = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ubicacion);
            pstmt.setString(2, tipoEvento);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                eventos.add(new Evento(
                        rs.getInt("id_evento"),
                        rs.getString("nombre"),
                        rs.getString("tipo_evento"),
                        rs.getString("ubicacion"),
                        rs.getInt("capacidad"),
                        rs.getDouble("precio"),
                        rs.getString("horarios")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener eventos: " + e.getMessage());
        }
        return eventos;
    }
}