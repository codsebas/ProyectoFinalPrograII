/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.implementacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import proyectofinal.interfaces.IDetalleVentas;
import proyectofinal.modelos.ModeloDetalleVenta;
import proyectofinal.sql.QuerysDetalleVentas;
import proyectofinal.sql.Conector;

/**
 *
 * @author sebas
 */
public class DetalleVentasImp implements IDetalleVentas {

    Conector conector = new Conector();
    QuerysDetalleVentas sql = new QuerysDetalleVentas();
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public boolean insertarDetalleVenta(List<ModeloDetalleVenta> modelo) {
        boolean resultado = true;
        conector.conectar();
        ps = conector.preparar(sql.getINSERTAR_DETALLE_VENTA());
        try {
            for (ModeloDetalleVenta detalle : modelo) {
                ps.setInt(1, detalle.getNumFactura());
                ps.setInt(2, detalle.getNumLinea());
                ps.setInt(3, detalle.getProductoId());
                ps.setInt(4, detalle.getCantidadProducto());
                ps.setDouble(5, detalle.getPrecioUnitario());
                ps.setDouble(6, detalle.getTotalLinea());
                ps.executeUpdate();
            }
            return true;
        } catch (SQLException ex) {
            conector.mensaje("Error en la insersción", "Error", 1);
            return resultado;
        }
    }

    @Override
    public boolean eliminarDetalleVenta(int factura) {
        boolean resultado = false;
        return resultado;
    }

    @Override
    public DefaultTableModel modeloDetalleVenta() {
        return null;
    }

}
