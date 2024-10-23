/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import proyectofinal.modelos.ModeloDetalleVenta;
import proyectofinal.modelos.ModeloVenta;
import proyectofinal.implementacion.DetalleVentasImp;
import proyectofinal.implementacion.VentasImp;
import proyectofinal.modelos.ButtonRenderer;

/**
 *
 * @author sebas
 */
public class ControladorVentas implements ActionListener, WindowListener, MouseListener {

    ModeloVenta modelo;

    DetalleVentasImp impDetVenta = new DetalleVentasImp();
    VentasImp impVenta = new VentasImp();

    public ControladorVentas(ModeloVenta modelo) {
        this.modelo = modelo;
    }

    private void agregarProductoADetalle(int selectedRow, int cantidad) {
    DefaultTableModel detalleModel = (DefaultTableModel) modelo.getVista().tblListaProductos.getModel(); // Tabla de detalle de venta

    String idProducto = modelo.getVista().tblMostrarProductos.getValueAt(selectedRow, 0).toString(); // Columna 0: ID del producto
    String nombreProducto = modelo.getVista().tblMostrarProductos.getValueAt(selectedRow, 1).toString(); // Columna 1: Nombre del producto
    double precioUnitario = Double.parseDouble(modelo.getVista().tblMostrarProductos.getValueAt(selectedRow, 2).toString()); // Columna 2: Precio unitario
    int stockActual = Integer.parseInt(modelo.getVista().tblMostrarProductos.getValueAt(selectedRow, 3).toString()); // Columna 3: Stock actual

    if (cantidad > stockActual) {
        JOptionPane.showMessageDialog(null, "No hay suficiente stock disponible.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    double totalLinea = precioUnitario * cantidad;

    detalleModel.addRow(new Object[]{idProducto, nombreProducto, cantidad, precioUnitario, totalLinea, "Eliminar"});

    modelo.getVista().tblListaProductos.setModel(detalleModel);

    int nuevoStock = stockActual - cantidad;
    modelo.getVista().tblMostrarProductos.setValueAt(nuevoStock, selectedRow, 3); // Actualizar el stock en la columna 3
}


    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = modelo.getVista().tblMostrarProductos.getSelectedRow();
        if (selectedRow != -1) {
            int stockActual = (int) modelo.getVista().tblMostrarProductos.getValueAt(selectedRow, 3); // Suponiendo que el stock está en la columna 3

            String cantidadStr = JOptionPane.showInputDialog(null, "Ingrese la cantidad:", "Cantidad", JOptionPane.QUESTION_MESSAGE);
            if (cantidadStr != null && !cantidadStr.isEmpty()) {
                int cantidad = Integer.parseInt(cantidadStr);
                if (cantidad > 0 && cantidad <= stockActual) {
                    int nuevoStock = stockActual - cantidad;
                    modelo.getVista().tblMostrarProductos.setValueAt(nuevoStock, selectedRow, 3); // Actualizar visualmente el stock

                    agregarProductoADetalle(selectedRow, cantidad);
                } else {
                    JOptionPane.showMessageDialog(null, "Cantidad inválida o mayor al stock disponible", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    @Override
public void windowOpened(WindowEvent e) {
    if (e.getComponent().equals(modelo.getVista())) {
        DefaultTableModel model = impVenta.modeloProducto(); // Obtén el modelo de productos
        
        model.addColumn("Seleccionar"); 
        
        // Asignar el modelo actualizado a la tabla de productos
        modelo.getVista().tblMostrarProductos.setModel(model);

        // Configurar la columna del botón "Seleccionar" en la tabla de productos
        TableColumn productColumn = modelo.getVista().tblMostrarProductos.getColumnModel().getColumn(model.getColumnCount() - 1); // Obtener la última columna
        productColumn.setCellRenderer(new ButtonRenderer(modelo.getVista().tblMostrarProductos, modelo.getVista().tblListaProductos)); // Pasar ambas tablas
        productColumn.setCellEditor(new ButtonRenderer(modelo.getVista().tblMostrarProductos, modelo.getVista().tblListaProductos));
        
        // Cargar clientes en el combo box de clientes
        modelo.getVista().cmbClientes.setModel(impVenta.mostrarCliente());

        // Cargar la tabla de detalle de ventas
        DefaultTableModel detalleModel = new DefaultTableModel(); // Crear un modelo para la tabla de detalle
        detalleModel.setColumnIdentifiers(new Object[]{"ID Producto", "Nombre", "Cantidad", "Precio Unitario", "Total Línea", "Eliminar"}); // Definir las columnas necesarias
        modelo.getVista().tblListaProductos.setModel(detalleModel); // Asignar el modelo a la tabla de detalle de ventas
        
        // Configurar el botón "Eliminar" en la tabla de detalle
        TableColumn detailColumn = modelo.getVista().tblListaProductos.getColumnModel().getColumn(5); // Columna 5 para "Eliminar"
        detailColumn.setCellRenderer(new ButtonRenderer(modelo.getVista().tblMostrarProductos, modelo.getVista().tblListaProductos)); // Pasar ambas tablas
        detailColumn.setCellEditor(new ButtonRenderer(modelo.getVista().tblMostrarProductos, modelo.getVista().tblListaProductos));
    }
}

    

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
