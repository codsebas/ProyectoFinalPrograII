package proyectofinal.interfaces;
import proyectofinal.modelos.ModeloCliente;

import javax.swing.table.DefaultTableModel;

public interface IClientes {
    public boolean insertarCliente(ModeloCliente modelo);
    public boolean eliminarCliente(String  nit);
    public boolean actualizarCliente(ModeloCliente modelo);
    public DefaultTableModel modeloCliente();
    public DefaultTableModel modeloCliente(String nit);
    public ModeloCliente mostrarCliente(String nit);
}