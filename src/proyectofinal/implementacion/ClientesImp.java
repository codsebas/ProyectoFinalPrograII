package proyectofinal.implementacion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public boolean insertarEstadoCompra(ModeloCliente modelo) {
        return false;
    }

    @Override
    public boolean eliminarEstadoCompra(String nit) {
        return false;
    }

    @Override
    public boolean actualizarEstadoCompra(ModeloCliente modelo) {
        return false;
    }

    @Override
    public DefaultTableModel modeloEstadoCompra() {
        return null;
    }

    @Override
    public DefaultTableModel modeloEstadoCompra(String nit) {
        return null;
    }

    @Override
    public ModeloCliente mostrarEstadoCompra(String nit) {
        return null;
    }
    
}
