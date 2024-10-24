/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.implementacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import proyectofinal.interfaces.IDetalleInventario;
import proyectofinal.modelos.ModeloDetalleInventario;
import proyectofinal.sql.Conector;
import proyectofinal.sql.QuerysDetalleInventario;

/**
 *
 * @author javie
 */
public class DetalleInventarioImp implements IDetalleInventario {

    Conector conector = new Conector();
    QuerysDetalleInventario sql = new QuerysDetalleInventario();
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public DefaultTableModel modeloDetalleInventario() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"id", "producto id", "usuario", "fecha modificacion", "cantdad modificada","tipo modificacion","motivo modificacion"});
        conector.conectar();
        try {
            ps = conector.preparar(sql.getMOSTRAR_TODOS_DETALLES());
            System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("id_detalle"),
                    rs.getString("producto_id"),
                    rs.getString("usuario_user"),
                    rs.getString("fecha_modificacion"),
                    rs.getString("cantidad_modificada"),
                    rs.getString("tipo_modificacion"),
                    rs.getString("motivo_modificacion"),
                });
            }

            conector.desconectar();
        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error", 0);
            conector.desconectar();
        }
        return modelo;
    }

    @Override
    public DefaultTableModel modeloDetalleInventario(int txtDetalleInventario) {

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"id", "producto id", "usuario", "fecha modificacion", "cantdad modificada","tipo modificacion","motivo modificacion"});
        conector.conectar();
        try {
            ps = conector.preparar(sql.getBUSCAR_DETALLE_INVENTARIOS());
            System.out.println(ps);
            ps.setInt(1, txtDetalleInventario);
            rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("id_detalle"),
                    rs.getString("producto_id"),
                    rs.getString("usuario_user"),
                    rs.getString("fecha_modificacion"),
                    rs.getString("cantidad_modificada"),
                 rs.getString("tipo_modificacion"),
                    rs.getString("motivo_modificacion"),});
            }
            conector.desconectar();
        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error", 0);
            conector.desconectar();
        }
        return modelo;
    }
    @Override
    public ModeloDetalleInventario mostrarCliente(int detalle){
        ModeloDetalleInventario modelo = new ModeloDetalleInventario ();
        conector.conectar();

        try {
            ps = conector.preparar(sql.getBUSCAR_DETALLE_INVENTARIOS());
            ps.setInt(1, detalle);
            System.out.println(ps);
            rs = ps.executeQuery();

            while (rs.next()) {
                modelo.setIdDetalle(Integer.parseInt(rs.getString(1)));
               modelo.setProductoId(Integer.parseInt(rs.getString(2)));
               modelo.setUsuario(rs.getString(3));
               modelo.setFechaModificacion(rs.getString(4));
               modelo.setCantidadModificada(Integer.parseInt(rs.getString(5)));
               modelo.setTipoModificacion(rs.getString(6));
               modelo.setMotivoModificacion(rs.getString(7));
            }
            conector.desconectar();

        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error", 1);
            conector.desconectar();
        }
        return modelo;
    }
       
}
