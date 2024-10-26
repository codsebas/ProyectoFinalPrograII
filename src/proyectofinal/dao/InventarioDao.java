/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import proyectofinal.modelos.UsuarioActual;
import proyectofinal.sql.Conector;

/**
 *
 * @author gerso
 */
public class InventarioDao {
    
    Connection conn = new Conector().conectar();

    // Método para actualizar el stock de un producto
    // Método para actualizar el stock de un producto
     public void agregarStock(int productoId, int cantidadAgregar, String usuario) {
        // Obtener el stock actual
        int stockActual = getStock(productoId);

        // Sumar la cantidad al stock actual
        int nuevoStock = stockActual + cantidadAgregar;

        // Actualizar el stock en la tabla inventarios
        String sqlUpdateStock = "UPDATE inventarios SET stock_producto = ? WHERE producto_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sqlUpdateStock)) {
            stmt.setInt(1, nuevoStock);
            stmt.setInt(2, productoId);
            stmt.executeUpdate();
            System.out.println("STOCK ACTUALIZADO");

            // Insertar la modificación en la tabla detalle_inventarios
            String usuarioActual = UsuarioActual.usuarioActual;
            String sqlInsertDetalle = "INSERT INTO detalle_inventarios (producto_id, usuario_user, cantidad_modificada, tipo_modificacion, motivo_modificacion) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmtDetalle = conn.prepareStatement(sqlInsertDetalle)) {
                stmtDetalle.setInt(1, productoId);
                stmtDetalle.setString(2, usuarioActual);
                stmtDetalle.setInt(3, cantidadAgregar);
                stmtDetalle.setString(4, "entrada");
                stmtDetalle.setString(5, "rebastecimiento");
                stmtDetalle.executeUpdate();
                System.out.println("DETALLE DE INVENTARIO ACTUALIZADO");
            } catch (SQLException e) {
                System.out.println("Error al registrar en detalle_inventarios: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el stock: " + e.getMessage());
        }
    }

    // Método para obtener el stock actual de un producto
    public int getStock(int productoId) {
       String sql = "SELECT stock_producto FROM inventarios WHERE producto_id = ?";
    int stock = 0; // Cambia el valor por defecto a 0

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, productoId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                stock = rs.getInt("stock_producto");
            } else {
                System.out.println("No se encontró el producto con ID: " + productoId);
                stock = 0; // O maneja este caso como desees
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener el stock: " + e.getMessage());
    }

    return stock;
}
    
    public void registrarModificacionInventario(int productoId, int cantidad, String motivo) {
    // Aquí va la lógica para insertar en la tabla detalle_inventarios
    String query = "INSERT INTO detalle_inventarios (producto_id, cantidad_modificada, tipo_modificacion, motivo_modificacion, fecha_modificacion) VALUES (?, ?, 'incremento', ?, NOW())";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, productoId);
        stmt.setInt(2, cantidad);
        stmt.setString(3, motivo);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}