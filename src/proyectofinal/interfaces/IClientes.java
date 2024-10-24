package proyectofinal.interfaces;
import proyectofinal.modelos.ModeloCliente;

import javax.swing.table.DefaultTableModel;

public interface IClientes {
    public boolean insertarEstadoCompra(ModeloCliente modelo);
    public boolean eliminarEstadoCompra(String  nit);
    public boolean actualizarEstadoCompra(ModeloCliente modelo);
    public DefaultTableModel modeloEstadoCompra();
    public DefaultTableModel modeloEstadoCompra(String nit);
    public ModeloCliente mostrarEstadoCompra(String nit);
}
