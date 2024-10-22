/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.implementacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import proyectofinal.interfaces.IVentas;
import proyectofinal.modelos.ModeloVenta;
import proyectofinal.sql.QuerysVentas;
import proyectofinal.sql.Conector;

/**
 *
 * @author sebas
 */
public class VentasImp implements IVentas {

    Conector conector = new Conector();
    QuerysVentas sql = new QuerysVentas();
    PreparedStatement ps;
    ResultSet rs;
    public static int no_factura;

    @Override
    public boolean insertarVenta(ModeloVenta modelo) {
        boolean resultado = true;
        conector.conectar();
        ps = conector.preparar(sql.getINSERTAR_VENTA());

        try {
            ps.setString(1, modelo.getUsuario());
            ps.setString(2, modelo.getNitCliente());
            ps.setDouble(3, modelo.getTotalSinImpuestos());
            ps.setDouble(4, modelo.getTotalImpuestos());
            ps.setDouble(5, modelo.getCargoTarjeta());
            ps.setDouble(6, modelo.getTotalVenta());
            ps.setString(7, modelo.getMetodoPago());
            return ps.execute();
        } catch (SQLException ex) {
            conector.mensaje("Error en la insersción", "Error", 1);
            return resultado;
        }
    }

    @Override
    public boolean eliminarVenta(int factura) {
        return false;
    }

    @Override
    public boolean actualizarVenta(ModeloVenta modelo) {
        return false;
    }

    @Override
    public DefaultTableModel modeloVenta() {
        return null;
    }

    @Override
    public DefaultTableModel modeloVenta(int factura) {
        return null;
    }

    @Override
    public ModeloVenta mostrarVenta(int factura) {
        return null;
    }

    @Override
    public DefaultComboBoxModel mostrarCliente() {
        return null;
    }

}
