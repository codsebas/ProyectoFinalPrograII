/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package proyectofinal.interfaces;

import javax.swing.table.DefaultTableModel;
import proyectofinal.modelos.ModeloDetalleInventario;

/**
 *
 * @author javie
 */
public interface IDetalleInventario {
    public boolean actualizarEstadoCompra(ModeloDetalleInventario modelo);
    public DefaultTableModel modeloDetalleInventario();
}
