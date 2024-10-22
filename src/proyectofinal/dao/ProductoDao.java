/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import proyectofinal.modelos.ModeloProducto;
import proyectofinal.sql.Conector;

/**
 *
 * @author gerso
 */
public class ProductoDao {

    public void registrarProducto(int categoriaId, String nombreProducto, double precioNormal, double precioPromocion, String descripcion, String rutaImagenProducto, String rutaImagenCodigoBarras) {
    String sql = "INSERT INTO productos (categoria_id, nombre_producto, precio_normal, precio_promocion, descripcion, ruta_imagen_producto, ruta_imagen_codigo_barras) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = new Conector().conectar();  
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, categoriaId);
        stmt.setString(2, nombreProducto);
        stmt.setDouble(3, precioNormal);
        stmt.setDouble(4, precioPromocion);
        stmt.setString(5, descripcion);
        stmt.setString(6, rutaImagenProducto);
        stmt.setString(7, rutaImagenCodigoBarras);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    public List<ModeloProducto> obtenerTodosLosProductos() {
        List<ModeloProducto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection conn = new Conector().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ModeloProducto producto = new ModeloProducto();
                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setCategoriaId(rs.getInt("categoria_id"));
                producto.setNombreProducto(rs.getString("nombre_producto"));
                producto.setPrecioNormal(rs.getDouble("precio_normal"));
                producto.setPrecioPromocion(rs.getDouble("precio_promocion"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setRutaImagenProducto(rs.getString("ruta_imagen_producto"));
                producto.setRutaImagenCodigoBarras(rs.getString("ruta_imagen_codigo_barras"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    // Método para obtener un producto por ID
    public ModeloProducto obtenerProductoPorId(int idProducto) {
        ModeloProducto producto = null;
        String sql = "SELECT * FROM productos WHERE id_producto = ?";

        try (Connection conn = new Conector().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                producto = new ModeloProducto();
                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setCategoriaId(rs.getInt("categoria_id"));
                producto.setNombreProducto(rs.getString("nombre_producto"));
                producto.setPrecioNormal(rs.getDouble("precio_normal"));
                producto.setPrecioPromocion(rs.getDouble("precio_promocion"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setRutaImagenProducto(rs.getString("ruta_imagen_producto"));
                producto.setRutaImagenCodigoBarras(rs.getString("ruta_imagen_codigo_barras"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return producto;
    }

    // Método para actualizar un producto
    public void actualizarProducto(ModeloProducto producto) {
        String sql = "UPDATE productos SET categoria_id = ?, nombre_producto = ?, precio_normal = ?, precio_promocion = ?, descripcion = ?, ruta_imagen_producto = ?, ruta_imagen_codigo_barras = ? WHERE id_producto = ?";

        try (Connection conn = new Conector().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, producto.getCategoriaId());
            stmt.setString(2, producto.getNombreProducto());
            stmt.setDouble(3, producto.getPrecioNormal());
            stmt.setDouble(4, producto.getPrecioPromocion());
            stmt.setString(5, producto.getDescripcion());
            stmt.setString(6, producto.getRutaImagenProducto());
            stmt.setString(7, producto.getRutaImagenCodigoBarras());
            stmt.setInt(8, producto.getIdProducto());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un producto por ID
    public void eliminarProducto(int idProducto) {
        String sql = "DELETE FROM productos WHERE id_producto = ?";

        try (Connection conn = new Conector().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}