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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import proyectofinal.interfaces.IDetalleVentas;
import proyectofinal.modelos.ModeloDetalleVenta;
import proyectofinal.modelos.ModeloInsertarDetalleInventario;
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

    @Override
    public boolean actualizarStock(List<ModeloDetalleVenta> modelo) {
        conector.conectar();
        ps = conector.preparar(sql.getACTUALIZAR_STOCK_PRODUCTO());

        try {
            for (ModeloDetalleVenta detalle : modelo) {
                PreparedStatement psSelect = conector.preparar(sql.getSELECCIONAR_STOCK_PRODUCTO());
                psSelect.setInt(1, detalle.getProductoId());
                ResultSet res = psSelect.executeQuery();

                if (res.next()) {
                    int stockActual = res.getInt("stock_producto");
                    int nuevoStock = stockActual - detalle.getCantidadProducto();

                    ps.setInt(1, nuevoStock);
                    ps.setInt(2, detalle.getProductoId());
                    ps.executeUpdate();
                }
            }
            return true;
        } catch (SQLException ex) {
            conector.mensaje("Error al actualizar el stock", "Error", 1);
            return false;
        }
    }

    @Override
    public boolean insertarDetalleInventario(List<ModeloInsertarDetalleInventario> modeloInventario) {
        boolean resultado = true;
    conector.conectar();
    ps = conector.preparar(sql.getGUARDAR_DETALLE_INVENTARIO());
    try {
        for (ModeloInsertarDetalleInventario detalle : modeloInventario) {
            ps.setInt(1, detalle.getProductoId());
            ps.setString(2, detalle.getUsuario()); 
            ps.setInt(3, detalle.getCantidadModificada());
            ps.setString(4, detalle.getTipoModificacion());
            ps.setString(5, detalle.getMotivoModificacion());
            ps.executeUpdate();
        }
        return true;
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al insertar en detalle de inventario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        resultado = false;
    } finally {
        conector.desconectar();
    }
    return resultado;
    }

}
