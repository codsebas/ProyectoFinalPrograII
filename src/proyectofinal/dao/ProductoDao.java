/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.dao;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nombreProducto);
            stmt.setInt(2, idCategoria);
            stmt.setDouble(3, precioNormal);
            stmt.setDouble(4, precioPromocion);
            stmt.setString(5, descripcion);

            if (rutaImagen != null && !rutaImagen.isEmpty()) {
                String projectDirectory = System.getProperty("user.dir");
            String imagenDir = projectDirectory + File.separator + "imagenes_productos";
            File dir = new File(imagenDir);
            if (!dir.exists()) {
                dir.mkdirs();  // Crear la carpeta si no existe
            }
            
            // Guardar la imagen en la carpeta del proyecto
            String nombreImagen = nombreProducto + ".png"; // O usa otro criterio para nombrar la imagen
            File imagenFile = new File(dir, nombreImagen);
            Files.copy(Paths.get(rutaImagen), imagenFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            stmt.setString(6, "imagenes_productos/" + nombreImagen); // Ruta relativa para almacenar en la base de datos
        } else {
            stmt.setNull(6, java.sql.Types.VARCHAR);
        }
        
            
            
            
       
            int filasAfectadas = stmt.executeUpdate();
            System.out.println("Filas afectadas: " + filasAfectadas);
            if (filasAfectadas > 0) {
                System.out.println("PRODUCTO AGREGADO");

                // Obtener el ID generado para el nuevo producto
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idProducto = generatedKeys.getInt(1);

                    String projectDirectory = System.getProperty("user.dir");

// Ruta de la carpeta "codigos_barras"
                    File barcodeDir = new File(projectDirectory + File.separator + "codigos_barras");

// Si la carpeta no existe, se crea
                    if (!barcodeDir.exists()) {
                        if (barcodeDir.mkdirs()) {
                            System.out.println("Directorio 'codigos_barras' creado exitosamente.");
                        } else {
                            System.out.println("Error al crear el directorio 'codigos_barras'.");
                        }
                    }

// Generar la ruta completa del archivo de código de barras
                    String barcodeFilePath = barcodeDir.getPath() + File.separator + nombreProducto + "_" + idProducto + ".png";
                    try {
                        generarCodigoBarras(String.valueOf(idProducto), barcodeFilePath);
                        System.out.println("Código de barras generado en: " + barcodeFilePath);

                        // Almacenar la imagen del código de barras en la base de datos
                        String updateSql = "UPDATE productos SET ruta_imagen_codigo_barras = ? WHERE id_producto = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            FileInputStream barcodeFis = new FileInputStream(barcodeFilePath);
                            updateStmt.setBinaryStream(1, barcodeFis, barcodeFis.available());
                            updateStmt.setInt(2, idProducto);
                            updateStmt.executeUpdate();
                            System.out.println("Código de barras almacenado en la base de datos.");
                        } catch (IOException e) {
                            System.out.println("Error al leer el archivo del código de barras: " + e.getMessage());
                        }

                        // Llamar al método para agregar stock en la tabla de inventarios
                        agregarStockInicial(idProducto);

                    } catch (Exception e) {
                        System.out.println("Error al generar el código de barras: " + e.getMessage());
                    }
                }
            } else {
                System.out.println("No se insertó ningún producto.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error addProducto: " + e.getMessage());
        
        } catch (IOException e){
            System.out.println("Error al copiar la imagen: "+ e.getMessage());
        }
        
        
        }
        
        
        
        
    

    public static void generarCodigoBarras(String texto, String rutaArchivo) {
        try {
            // Parámetros para el código de barras (tipo CODE_128)
            BitMatrix matrix = new MultiFormatWriter().encode(texto, BarcodeFormat.CODE_128, 300, 150);

            // La ruta donde se va a guardar la imagen
            Path ruta = FileSystems.getDefault().getPath(rutaArchivo);

            // Guardar la imagen
            MatrixToImageWriter.writeToPath(matrix, "png", ruta);
            System.out.println("Código de barras generado en: " + rutaArchivo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void agregarStockInicial(int productoId) {
        String sqlInsertInventario = "INSERT INTO inventarios (producto_id, stock_producto) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sqlInsertInventario)) {
            stmt.setInt(1, productoId);
            stmt.setInt(2, 1); // Establecer el stock inicial en 1
            stmt.executeUpdate();
            System.out.println("Stock inicial agregado para el producto ID: " + productoId);
        } catch (SQLException e) {
            System.out.println("Error al agregar stock inicial: " + e.getMessage());
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

    public boolean existeProductoConNombre(String nombreProducto) {
        String sql = "SELECT COUNT(*) FROM productos WHERE nombre_producto = ?";
        boolean existe = false;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreProducto); // Asignar el nombre del producto al query

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1); // Obtener el resultado de la consulta
                    if (count > 0) {
                        existe = true; // Si el conteo es mayor a 0, el producto ya existe
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en existeProductoConNombre: " + e.getMessage());
        }

        return existe;
    }
}
