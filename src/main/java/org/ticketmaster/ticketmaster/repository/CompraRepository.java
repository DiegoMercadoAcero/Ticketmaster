package org.ticketmaster.ticketmaster.repository;

import org.ticketmaster.ticketmaster.util.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CompraRepository {

    public List<String> procesarCompra(String usuario, int idEvento, int cantidad, String metodo, double precioUnitario) {
        Connection conn = null;
        List<String> codigosGenerados = new ArrayList<>();

        try {
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false); // Iniciamos transacción

            // 1. Validar disponibilidad
            String sqlCapacidad = "SELECT (capacidad - (SELECT IFNULL(SUM(cantidad), 0) FROM compras WHERE id_evento = ?)) as disponible FROM eventos WHERE id_evento = ?";
            PreparedStatement psCap = conn.prepareStatement(sqlCapacidad);
            psCap.setInt(1, idEvento);
            psCap.setInt(2, idEvento);
            ResultSet rsCap = psCap.executeQuery();

            if (rsCap.next() && rsCap.getInt("disponible") >= cantidad) {
                // 2. Insertar Compra
                String sqlCompra = "INSERT INTO compras (usuario, id_evento, cantidad, total, metodo_pago) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement psCompra = conn.prepareStatement(sqlCompra, Statement.RETURN_GENERATED_KEYS);
                psCompra.setString(1, usuario);
                psCompra.setInt(2, idEvento);
                psCompra.setInt(3, cantidad);
                psCompra.setDouble(4, cantidad * precioUnitario);
                psCompra.setString(5, metodo);
                psCompra.executeUpdate();

                ResultSet rsKeys = psCompra.getGeneratedKeys();
                if (rsKeys.next()) {
                    int idCompra = rsKeys.getInt(1);

                    // 3. Generar Boletos con códigos únicos
                    String sqlBoleto = "INSERT INTO boletos (id_compra, codigo_unico) VALUES (?, ?)";
                    PreparedStatement psBoleto = conn.prepareStatement(sqlBoleto);

                    for (int i = 0; i < cantidad; i++) {
                        String codigo = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                        psBoleto.setInt(1, idCompra);
                        psBoleto.setString(2, codigo);
                        psBoleto.executeUpdate();
                        codigosGenerados.add(codigo);
                    }
                }
                conn.commit(); // Todo bien, guardamos
                return codigosGenerados;
            } else {
                conn.rollback(); // No hay cupo
                return null;
            }
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            e.printStackTrace();
            return null;
        }
    }
}