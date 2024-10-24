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
import proyectofinal.modelos.ProductoTableModel;
import proyectofinal.sql.QuerysVentas;
import proyectofinal.sql.Conector;
import proyectofinal.sql.QuerysClientes;

/**
 *
 * @author sebas
 */
public class VentasImp implements IVentas{

    Conector conector = new Conector();
    QuerysVentas sql = new QuerysVentas();
    QuerysClientes sqlCli = new QuerysClientes();
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public int insertarVenta(ModeloVenta modelo) {
        int resultado = 0;
        conector.conectar(); // Conectar a la base de datos
        ps = conector.preparar(sql.getINSERTAR_VENTA(), PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            // Insertar la venta principal
            ps.setString(1, modelo.getUsuario());
            ps.setString(2, modelo.getNitCliente());
            ps.setDouble(3, modelo.getTotalSinImpuestos());
            ps.setDouble(4, modelo.getTotalImpuestos());
            ps.setDouble(5, modelo.getCargoTarjeta());
            ps.setDouble(6, modelo.getTotalVenta());
            ps.setString(7, modelo.getMetodoPago());

            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                ResultSet res = ps.getGeneratedKeys();
                if(res.next()){
                    resultado = res.getInt(1);
                }
            }
            return resultado;

        } catch (SQLException ex) {
            conector.mensaje("Error en la inserción de venta", "Error", 1);
            System.out.println(ex.getMessage());
            return 0; // Si hay error, devolver 0
        } finally {
            conector.desconectar(); // Desconectar la base de datos
        }
    }

    @Override
    public boolean eliminarVenta(int factura) {
        boolean resultado = true;
        conector.conectar();
        try {
            ps = conector.preparar(sql.getELIMINAR_VENTA());
            ps.setInt(1, factura);
            return ps.execute();
        } catch (SQLException ex) {
            conector.mensaje("No se pudo eliminar la factura", "Error al eliminar", 1);
            return resultado;
        }
    }

    @Override
    public boolean actualizarVenta(ModeloVenta modelo) {
        boolean resultado = true;
        conector.conectar();
        ps = conector.preparar(sql.getACTUALIZAR_VENTA());

        try {
            ps.setInt(1, modelo.getNumeroFactura());
            ps.setString(2, modelo.getUsuario());
            ps.setString(3, modelo.getNitCliente());
            ps.setDouble(4, modelo.getTotalSinImpuestos());
            ps.setDouble(5, modelo.getTotalImpuestos());
            ps.setDouble(6, modelo.getCargoTarjeta());
            ps.setDouble(7, modelo.getTotalVenta());
            ps.setString(8, modelo.getMetodoPago());
            return ps.execute();
        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error al actualizar", 1);
        }
        return resultado;
    }

    @Override
    public DefaultTableModel modeloVenta() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.setColumnIdentifiers(new Object[]{"Factura", "Nit", "Fecha", "Total Venta"});
        conector.conectar();

        try {
            ps = conector.preparar(sql.getSELECCIONAR_TODAS_LAS_VENTAS());
            System.out.println(ps);
            rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("no_factura"),
                    rs.getString("cliente_nit"),
                    rs.getString("fecha_venta"),
                    rs.getString("total_venta")
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
    public DefaultTableModel modeloVenta(int factura) {
        return null;

    }

    @Override
    public ModeloVenta mostrarVenta(int factura) {
        ModeloVenta modelo = new ModeloVenta();
        conector.conectar();

        try {
            ps = conector.preparar(sql.getBUSCAR_VENTA_POR_FACTURA());
            ps.setInt(1, factura);
            System.out.println(ps);
            rs = ps.executeQuery();

            while (rs.next()) {
                modelo.setNumeroFactura(rs.getInt(1));
                modelo.setUsuario(rs.getString(2));
                modelo.setNitCliente(rs.getString(3));
                modelo.setTotalSinImpuestos(rs.getDouble(4));
                modelo.setTotalImpuestos(rs.getDouble(5));
                modelo.setCargoTarjeta(rs.getDouble(6));
                modelo.setTotalVenta(rs.getDouble(7));
                modelo.setMetodoPago(rs.getString(8));
            }
            conector.desconectar();

        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error", 1);
            conector.desconectar();
        }
        return modelo;
    }

    @Override
    public DefaultComboBoxModel mostrarCliente() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        conector.conectar();
        try {
            ps = conector.preparar(sqlCli.getSELECCIONAR_All_CLIENTES());
            rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addElement(rs.getString("nit_cliente") + "-" + rs.getString("nombre_cliente"));
            }
            return modelo;
        } catch (SQLException ex) {
            conector.mensaje("Error al cargar los empleados", "Error de conexion", 0);
            return modelo;
        }
    }

    @Override
    public DefaultTableModel modeloProducto() {
        ProductoTableModel modelo = new ProductoTableModel();
        modelo.setColumnIdentifiers(new Object[]{"Id Producto", "Nombre", "Precio", "Stock"});
        conector.conectar();

        try {
            ps = conector.preparar(sql.getSELECCIONAR_TODOS_LOS_PRODUCTOS());

            System.out.println(ps);
            rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("id_producto"),
                    rs.getString("nombre_producto"),
                    rs.getString("precio_normal"),
                    rs.getString("stock_producto"),
                    null
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
    public DefaultTableModel modeloProducto(int idProduto) {
        ProductoTableModel modelo = new ProductoTableModel();
        modelo.setColumnIdentifiers(new Object[]{"Id Producto", "Nombre", "Precio", "Stock"});
        conector.conectar();

        try {
            ps = conector.preparar(sql.getSELECCIONAR_PRODUCTO());
            ps.setInt(1, idProduto);
            System.out.println(ps);
            rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("id_producto"),
                    rs.getString("nombre_producto"),
                    rs.getString("precio_normal"),
                    rs.getString("stock_producto")
                });
            }
            conector.desconectar();

        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error", 1);
            conector.desconectar();
        }
        return modelo;
    }

}
