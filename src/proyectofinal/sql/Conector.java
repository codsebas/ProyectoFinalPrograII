package proyectofinal.sql;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conector {

    // DEFINIR LAS PROPIEDADES DE LA CONEXIÓN
    private static final String CLASE = "com.mysql.cj.jdbc.Driver";
    private final String HOST = "sql.freedb.tech";
    private final String USUARIO = "freedb_zoldy";
    private final String CLAVE = "V*NhnmhtDkT2f!R";
    private final String BASEDATOS = "freedb_laTorre";
    private final String URL;

    private Connection link;
    private PreparedStatement ps;

    
    public Conector() {
        this.URL = "jdbc:mysql://sql.freedb.tech/freedb_laTorre?serverTimezone=UTC";
    }

    public Connection conectar() {
        try {
            Class.forName(CLASE).getDeclaredConstructor().newInstance();
            this.link = DriverManager.getConnection(this.URL, this.USUARIO, this.CLAVE);
            System.out.println("Conexion establecida correctamente");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return this.link;
    }

    public void desconectar() {
        try {
            this.link.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public PreparedStatement preparar(String sql) {
        try {
            ps = link.prepareStatement(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ps;
    }

    public void mensaje(String mensaje, String titulo, int tipoMensaje) {
        JOptionPane.showMessageDialog(null, mensaje, titulo, tipoMensaje);
    }

}
