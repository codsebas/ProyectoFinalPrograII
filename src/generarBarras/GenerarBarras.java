
package generarBarras;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import proyectofinal.sql.Conector;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author didhy
 */
public class GenerarBarras {

    // Método para obtener el ID desde la base de datos
    public static String obtenerIdDesdeMySQL() {
        String id = null;
        Conector conector = new Conector();
        Connection conexion = null;

        try {
            conexion = conector.conectar();
            String sql = "SELECT id FROM productos WHERE condiciones"; // Hace los cambios según nuestra vaina =3
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                id = rs.getString("id");  // Con esto nos aseguramos que el campo sea el correcto
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conexion != null) {
                conector.desconectar();
            }
        }
        return id;
    }

    // Método para generar el código de barras
    public static void generarCodigoBarras(String texto, String rutaArchivo) {
        try {
            // Parámetros para el código de barras (tipo CODE_128)
            BitMatrix matrix = new MultiFormatWriter().encode(texto, BarcodeFormat.CODE_128, 300, 150);
            
            // La ruta donde se va a guardar la imágen
            Path ruta = FileSystems.getDefault().getPath(rutaArchivo);
            
            // Guardar la imágen
            MatrixToImageWriter.writeToPath(matrix, "png", ruta);
            System.out.println("Código de barras generado en: " + rutaArchivo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String id = obtenerIdDesdeMySQL();  // Acá se obtiene el ID de la Base de Datos :)
        if (id != null) {
            generarCodigoBarras(id, "codigo_barras.png");  // Esto genera el código de barras con el ID xD
        } else {
            System.out.println("ID no encontrado.");
        }
    }
}