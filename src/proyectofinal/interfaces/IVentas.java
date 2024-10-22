/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package proyectofinal.interfaces;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import proyectofinal.modelos.ModeloVenta;

/**
 *
 * @author sebas
 */
public interface IVentas {
    public boolean insertarVenta(ModeloVenta modelo);
    public boolean eliminarVenta(int factura);
    public boolean actualizarVenta(ModeloVenta modelo);
    public DefaultTableModel modeloVenta();
    public DefaultTableModel modeloVenta(int factura);
    public DefaultTableModel modeloProducto();
    public DefaultTableModel modeloProducto(int idProduto);
    public ModeloVenta mostrarVenta(int factura);
    public DefaultComboBoxModel mostrarCliente();
}
