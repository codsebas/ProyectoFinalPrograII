/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package proyectofinal.interfaces;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import proyectofinal.modelos.ModeloDetalleVenta;

/**
 *
 * @author sebas
 */
public interface IDetalleVentas {
    public boolean insertarDetalleVenta(List<ModeloDetalleVenta> modelo);
    public boolean eliminarDetalleVenta(int  factura);
    public DefaultTableModel modeloDetalleVenta();
    
}
