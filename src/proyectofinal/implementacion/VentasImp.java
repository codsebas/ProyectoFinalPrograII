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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import proyectofinal.interfaces.IVentas;
import proyectofinal.interfaces.IDetalleVentas;
import proyectofinal.modelos.ModeloDetalleVenta;
import proyectofinal.modelos.ModeloVenta;
import proyectofinal.modelos.ProductoTableModel;
import proyectofinal.sql.QuerysVentas;
import proyectofinal.sql.QuerysDetalleVentas;
import proyectofinal.sql.Conector;
import proyectofinal.sql.QuerysClientes;
import proyectofinal.vistas.VistaVentas;

/**
 *
 * @author sebas
 */
public class VentasImp implements IVentas, IDetalleVentas {

    Conector conector = new Conector();
    QuerysVentas sql = new QuerysVentas();
    QuerysClientes sqlCli = new QuerysClientes();
    QuerysDetalleVentas sqlDet = new QuerysDetalleVentas();
    PreparedStatement ps;
    ResultSet rs;
    public static int no_factura;

    private List<ModeloDetalleVenta> agregarDetalleAModelo(JTable tblListaProductos, int numFactura) {
        List<ModeloDetalleVenta> detallesVenta = new ArrayList<>();
        
        
        if (tblListaProductos.getRowCount() == 0) {
            System.out.println("No hay productos en la lista de productos");
            return detallesVenta; // Retornar lista vacía si no hay productos
        }

        // Agregar lógica para llenar el modelo de detalles de venta
        for (int i = 0; i < tblListaProductos.getRowCount(); i++) {
            ModeloDetalleVenta detalle = new ModeloDetalleVenta();
            detalle.setNumFactura(numFactura);
            detalle.setProductoId((int) tblListaProductos.getValueAt(i, 0)); // ID del producto
            detalle.setCantidadProducto((int) tblListaProductos.getValueAt(i, 2)); // Cantidad de producto
            detalle.setPrecioUnitario((double) tblListaProductos.getValueAt(i, 3)); // Precio unitario
            detalle.setTotalLinea((double) tblListaProductos.getValueAt(i, 4)); // Total de la línea

            detallesVenta.add(detalle);
        }

        return detallesVenta;
    }

    @Override
    public boolean insertarVenta(ModeloVenta modelo) {
        boolean resultado = true;
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
                if (res.next()) {
                    no_factura = res.getInt(1); // Obtener el número de factura generado
                    System.out.println(no_factura);

                    // Insertar detalles de la venta
                    List<ModeloDetalleVenta> listaModelo = agregarDetalleAModelo(modelo.getVista().tblListaProductos, no_factura);
                    resultado = insertarDetalleVenta(listaModelo); // Insertar detalles de venta

                    if (resultado) {
                        conector.mensaje("Se ha insertado correctamente", "Confirmación", 2);
                    } else {
                        conector.mensaje("No se insertó correctamente", "Error", 1);
                    }
                }
            }
            return resultado;

        } catch (SQLException ex) {
            conector.mensaje("Error en la inserción de venta", "Error", 1);
            System.out.println(ex.getMessage());
            return false; // Si hay error, devolver false
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

    //Detalles de venta
    @Override
    public boolean insertarDetalleVenta(List<ModeloDetalleVenta> modelo) {
        boolean resultado = true;
        conector.conectar();
        ps = conector.preparar(sqlDet.getINSERTAR_DETALLE_VENTA());

        try {
            for (ModeloDetalleVenta detalle : modelo) {
                ps.setInt(1, detalle.getNumFactura());
                ps.setInt(2, detalle.getNumLinea());
                ps.setInt(3, detalle.getProductoId());
                ps.setInt(4, detalle.getCantidadProducto());
                ps.setDouble(5, detalle.getPrecioUnitario());
                ps.setDouble(6, detalle.getTotalLinea());
                System.out.println(ps);
                ps.executeUpdate();
            }

            resultado = true;
        } catch (SQLException ex) {
            conector.mensaje("Error en la inserción de detalle", "Error", 1);
            resultado = false;
        } finally {
            conector.desconectar();
        }

        return resultado;
    }

    @Override
    public boolean eliminarDetalleVenta(int factura) {
        return false;
    }

    @Override
    public DefaultTableModel modeloDetalleVenta() {
        return null;
    }

}
