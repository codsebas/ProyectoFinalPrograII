    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.implementacion;
import javax.swing.table.DefaultTableModel;
import proyectofinal.interfaces.IDetalleVentas;
import proyectofinal.modelos.ModeloDetalleVenta;
import proyectofinal.sql.QuerysDetalleVentas;
import proyectofinal.sql.Conector;
/**
 *
 * @author sebas
 */
public class DetalleVentasImp implements IDetalleVentas{

    @Override
    public boolean insertarDetalleVenta(ModeloDetalleVenta modelo) {
        return false;
    }

    @Override
    public boolean eliminarDetalleVenta(int factura) {
        return false;
    }

    @Override
    public boolean actualizarDetalleVEnta(ModeloDetalleVenta modelo) {
        return false;
    }

    @Override
    public DefaultTableModel modeloDetalleVenta() {
        return null;
    }

    @Override
    public DefaultTableModel modeloDetalleVenta(int factura) {
        return null;
    }

    @Override
    public ModeloDetalleVenta mostrarDetalleVenta(int factura) {
        return null;
    }
    
}
