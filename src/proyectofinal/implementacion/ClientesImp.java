package proyectofinal.implementacion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import proyectofinal.interfaces.IClientes;
import proyectofinal.modelos.ModeloCliente;
import proyectofinal.sql.Conector;
import proyectofinal.sql.QuerysClientes;

public class ClientesImp implements IClientes{

    Conector conector = new Conector();
    QuerysClientes sql = new QuerysClientes();
    PreparedStatement ps;
    ResultSet rs;
    
    @Override
    public boolean insertarCliente(ModeloCliente modelo) {
        boolean resultado = true;
        conector.conectar();
        ps = conector.preparar(sql.getINSERTAR_CLIENTE());

        try {
            ps.setString(1, modelo.getNIT());
            ps.setString(2, modelo.getNombre());
            return ps.execute();
        } catch (SQLException ex) {
            conector.mensaje("Error en la insersción", "Error", 1);
            return resultado;
        }
    }

    @Override
    public boolean eliminarCliente(String nit) {
        boolean resultado = true;
        conector.conectar();

        try {
            ps = conector.preparar(sql.getELIMINAR_CLIENTE());
            ps.setString(1, nit);
            System.out.println(ps);
            return ps.execute();
        } catch (SQLException ex) {
            conector.mensaje("Error al eliminar", "Error", 1);
            return resultado;
        }
    }

    @Override
    public boolean actualizarCliente(ModeloCliente modelo) {
        boolean resultado = true;
        conector.conectar();
        ps = conector.preparar(sql.getACTUALIZAR_CLIENTE());
        
        try {
            ps.setString(1, modelo.getNombre());
            ps.setString(2, modelo.getNIT());
            resultado = ps.execute();
        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error al actualizar", 1);
        }
        return resultado;
    }

    @Override
    public DefaultTableModel modeloCliente() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"Nit", "Nombre"});
        conector.conectar();

        try {
            ps = conector.preparar(sql.getSELECCIONAR_All_CLIENTES());
            System.out.println(ps);
            rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("nit_cliente"),
                    rs.getString("nombre_cliente")
                });
            }
            conector.desconectar();

        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error", 1);
            conector.desconectar();
        }
        return modelo;
    }

    @Override
    public DefaultTableModel modeloCliente(String nit) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"Nit", "Nombre"});
        conector.conectar();

        try {
            ps = conector.preparar(sql.getBUSCAR_CLIENTE_NIT());
            ps.setString(1, nit);
            System.out.println(ps);
            rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("nit_cliente"),
                    rs.getString("nombre_cliente")
                });
            }
            conector.desconectar();

        } catch (SQLException ex) {
            conector.mensaje("trono aqui", "error", 1);
            conector.mensaje(ex.getMessage(), "Error", 1);
            conector.desconectar();
        }
        return modelo;
    }

    @Override
    public ModeloCliente mostrarCliente(String nit) {
        ModeloCliente modelo = new ModeloCliente();
        conector.conectar();

        try {
            ps = conector.preparar(sql.getBUSCAR_CLIENTE_NIT());
            ps.setString(1, nit);
            System.out.println(ps);
            rs = ps.executeQuery();

            while (rs.next()) {
                modelo.setNIT(rs.getString(1));
                modelo.setNombre(rs.getString(2));
            }
            conector.desconectar();

        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error", 1);
            conector.desconectar();
        }
        return modelo;
    }
    
}
