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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
        modelo.getVista().tblMostrarProductos.setValueAt(nuevoStock, selectedRow, 3);
    }

    private void limpiar() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = modelo.getVista().tblMostrarProductos.getSelectedRow();
        if (selectedRow != -1) {
            int stockActual = (int) modelo.getVista().tblMostrarProductos.getValueAt(selectedRow, 3);

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

        if (e.getActionCommand().equals(modelo.getVista().btnAgregarCliente)) { //Llama a mantenimiento de clientes

        } else if (e.getActionCommand().equals(modelo.getVista().btnBuscar)) { //Busca productos

        } else if (e.getActionCommand().equals(modelo.getVista().btnCancelar)) { //Cancela busqueda

        } else if (e.getActionCommand().equals(modelo.getVista().btnGuardar)) { //Guarda venta y detalle de venta
            boolean resultado;
            ModeloVenta modelo = new ModeloVenta();
            modelo.setUsuario(this.modelo.getVista().txtUsuario.getText());
            modelo.setNitCliente(this.modelo.getVista().cmbClientes.getSelectedItem().toString());
            modelo.setTotalSinImpuestos(Double.parseDouble(this.modelo.getVista().txtSubtotal.getText()));
            modelo.setTotalImpuestos(Double.parseDouble(this.modelo.getVista().txtImpuestos.getText()));
            modelo.setCargoTarjeta(Double.parseDouble(this.modelo.getVista().txtCargosAdicionales.getText()));
            modelo.setTotalVenta(Double.parseDouble(this.modelo.getVista().txtTotalFinal.getText()));
            modelo.setNitCliente(this.modelo.getVista().cmbMetodoPago.getSelectedItem().toString());
            resultado = impVenta.insertarVenta(modelo);
            if (!resultado) {
                System.out.println("Inserción exitosa");
                limpiar();
            } else {
                System.out.println("Inserción falló");
            }
        } else if (e.getActionCommand().equals(modelo.getVista().btnRegresar)) { //Regresa a vista anterior

        } else if (e.getActionCommand().equals(modelo.getVista().btnVaciarLista)) { //Vacía lista de productos

        }
    }

    @Override
public void windowOpened(WindowEvent e) {
    if (e.getComponent().equals(modelo.getVista())) {
        // Cargar productos en la tabla de productos
        DefaultTableModel model = impVenta.modeloProducto(); // Obtén el modelo de productos
        
        // Agregar columna para el botón "Seleccionar"
        model.addColumn("Seleccionar");
        modelo.getVista().tblMostrarProductos.setModel(model);

        // Configurar la columna del botón "Seleccionar" en la tabla de productos
        TableColumn productColumn = modelo.getVista().tblMostrarProductos.getColumnModel().getColumn(model.getColumnCount() - 1);
        productColumn.setCellRenderer(new ButtonRenderer(modelo.getVista().tblMostrarProductos, modelo.getVista().tblListaProductos, modelo)); // Pasa el modelo correctamente
        productColumn.setCellEditor(new ButtonRenderer(modelo.getVista().tblMostrarProductos, modelo.getVista().tblListaProductos, modelo));  // Pasa el modelo correctamente

        // Cargar clientes en el combo box de clientes
        modelo.getVista().cmbClientes.setModel(impVenta.mostrarCliente());

        // Configurar la tabla de detalle de ventas
        DefaultTableModel detalleModel = new DefaultTableModel();
        detalleModel.setColumnIdentifiers(new Object[]{"ID Producto", "Nombre", "Cantidad", "Precio Unitario", "Total Línea", "Eliminar"});
        modelo.getVista().tblListaProductos.setModel(detalleModel);

        // Configurar el botón "Eliminar" en la tabla de detalle
        TableColumn detailColumn = modelo.getVista().tblListaProductos.getColumnModel().getColumn(5);
        detailColumn.setCellRenderer(new ButtonRenderer(modelo.getVista().tblMostrarProductos, modelo.getVista().tblListaProductos, modelo)); // Pasa el modelo correctamente
        detailColumn.setCellEditor(new ButtonRenderer(modelo.getVista().tblMostrarProductos, modelo.getVista().tblListaProductos, modelo));  // Pasa el modelo correctamente
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
