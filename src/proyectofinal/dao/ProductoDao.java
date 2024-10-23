/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import proyectofinal.modelos.ModeloProducto;
import proyectofinal.sql.Conector;

/**
 *
 * @author gerso
 */
public class ProductoDao {

    Connection conn = new Conector().conectar();

    public List<ModeloProducto> getProductos() {
        List<ModeloProducto> productos = new ArrayList<>();
         String sql = "SELECT p.id_producto, p.nombre_producto, p.descripcion, p.precio_normal, p.precio_promocion, "
                 + "p.categoria_id, c.descripcion_categoria, p.ruta_imagen_producto "
                 + "FROM productos p JOIN categoria_productos c ON p.categoria_id = c.id_categoria";

    try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int idProducto = rs.getInt("id_producto");
            String nombreProducto = rs.getString("nombre_producto");
            String descripcion = rs.getString("descripcion");
            int idCategoria = rs.getInt("categoria_id");
            String descripcionCategoria = rs.getString("descripcion_categoria");
            double precioNormalProducto = rs.getDouble("precio_normal");
            double precioPromocion = rs.getDouble("precio_promocion");
            String rutaImagen = rs.getString("ruta_imagen_producto"); // Asegúrate de incluir esta línea

            ModeloProducto producto = new ModeloProducto(idProducto, nombreProducto, descripcion, idCategoria,
                    descripcionCategoria, precioNormalProducto, precioPromocion, rutaImagen, null); // Asignar la ruta de la imagen
            productos.add(producto);
        }
    } catch (SQLException e) {
        System.out.println("Error getProductos: " + e.getMessage());
    }

    return productos;
}
    // Agregar un nuevo producto
    public void addProducto(String nombreProducto, int idCategoria, double precioNormal, double precioPromocion,
                        String descripcion, String rutaImagen) {
    
String sql = "INSERT INTO productos (nombre_producto, categoria_id, precio_normal, precio_promocion, descripcion, ruta_imagen_producto) VALUES (?, ?, ?, ?, ?, ?)";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, nombreProducto);
        stmt.setInt(2, idCategoria);
        stmt.setDouble(3, precioNormal);
        stmt.setDouble(4, precioPromocion);
        stmt.setString(5, descripcion);
        
        
        if (rutaImagen != null && !rutaImagen.isEmpty()) {
            stmt.setString(6, rutaImagen); 
        } else {
            stmt.setNull(6, java.sql.Types.VARCHAR); 
        }

        int filasAfectadas = stmt.executeUpdate();
        System.out.println("Filas afectadas: " + filasAfectadas);
        if (filasAfectadas > 0) {
            System.out.println("PRODUCTO AGREGADO");
        } else {
            System.out.println("No se insertó ningún producto.");
        }
    } catch (SQLException e) {
        System.out.println("Error addProducto: " + e.getMessage());
    }
}
    
    
    public ModeloProducto getOneProducto(int pIdProducto) {
        ModeloProducto producto = null;
        String sql = "SELECT p.id_producto, p.nombre_producto, p.descripcion, p.precio_normal, p.precio_promocion, "
                + "c.descripcion_categoria FROM productos p JOIN categoria_productos c ON p.categoria_id = c.id_categoria WHERE p.id_producto = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pIdProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idProducto = rs.getInt("id_producto");
                    String nombreProducto = rs.getString("nombre_producto");
                    String descripcion = rs.getString("descripcion");
                    double precioNormalProducto = rs.getDouble("precio_normal");
                    double precioPromocion = rs.getDouble("precio_promocion");
                    String descripcionCategoria = rs.getString("descripcion_categoria");

                    producto = new ModeloProducto(idProducto, nombreProducto, descripcion, pIdProducto,
                            descripcionCategoria, precioNormalProducto,
                            precioPromocion, null, null);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getOneProducto: " + e.getMessage());
        }

        return producto;
    }

    // Actualizar un producto
    public void updateProducto(int pIdProducto, String pNombreProducto, int pIdCategoria, 
                           double pPrecioNormal, double pPrecioPromocion, String pDescripcion, 
                           String pRutaImagen) {
    String sql = "UPDATE productos SET nombre_producto = ?, categoria_id = ?, precio_normal = ?, "
               + "precio_promocion = ?, descripcion = ?, ruta_imagen_producto = ? WHERE id_producto = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, pNombreProducto);
        stmt.setInt(2, pIdCategoria);
        stmt.setDouble(3, pPrecioNormal);
        stmt.setDouble(4, pPrecioPromocion);
        stmt.setString(5, pDescripcion);
        stmt.setString(6, pRutaImagen); // Actualizar la ruta de la imagen
        stmt.setInt(7, pIdProducto);
        stmt.executeUpdate();
        System.out.println("PRODUCTO ACTUALIZADO");
    } catch (SQLException e) {
        System.out.println("Error updateProducto: " + e.getMessage());
    }
}

    // Eliminar un producto
    public void deleteProducto(int pIdProducto) {
        String sql = "DELETE FROM productos WHERE id_producto = ?";

        try {
            // Verificar si el producto existe antes de eliminarlo
            ModeloProducto productoBD = this.getOneProducto(pIdProducto);
            if (productoBD != null) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, pIdProducto);
                    stmt.executeUpdate();
                    System.out.println("PRODUCTO ELIMINADO");
                }
            } else {
                System.out.println("EL PRODUCTO A ELIMINAR NO EXISTE");
            }
        } catch (SQLException e) {
            System.out.println("Error deleteProducto: " + e.getMessage());
        }
    }

}
