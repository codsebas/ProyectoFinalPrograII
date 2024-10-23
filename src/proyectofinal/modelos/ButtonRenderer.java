/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.modelos;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class ButtonRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
    private JButton renderButton;
    private JButton editButton;
    private String text;
    private JTable tableProductos;
    private JTable tableDetalle;
    private int selectedRow;

    public ButtonRenderer(JTable tableProductos, JTable tableDetalle) {
        this.tableProductos = tableProductos;
        this.tableDetalle = tableDetalle;
        renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        renderButton.setText((value == null) ? "Seleccionar" : value.toString());
        return renderButton;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.selectedRow = row;
        editButton.setText((value == null) ? "Seleccionar" : value.toString());
        return editButton;
    }

    @Override
    public Object getCellEditorValue() {
        return text;
    }

    @Override
public void actionPerformed(ActionEvent e) {
    JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, (Component) e.getSource());

    if (table.equals(tableProductos)) {
        // Botón "Seleccionar" de la tabla de productos
        int cantidad = pedirCantidad();
        if (cantidad > 0) {
            int stockActual = Integer.parseInt(tableProductos.getValueAt(selectedRow, 3).toString()); // Columna 3: Stock actual
            if (cantidad > stockActual) {
                JOptionPane.showMessageDialog(null, "No hay suficiente stock disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Detenemos si la cantidad supera el stock
            }
            agregarProductoADetalle(selectedRow, cantidad); // Agregar producto a la tabla de detalle
            // Actualizar el stock en la tabla de productos
            int nuevoStock = stockActual - cantidad;
            tableProductos.setValueAt(nuevoStock, selectedRow, 3); // Actualizar el stock en la columna 3
        }
    } else if (table.equals(tableDetalle)) {
        // Botón "Eliminar" de la tabla de detalle
        if (selectedRow != -1) {
            // Obtener el ID del producto que se va a eliminar de la tabla de detalle
            String idProducto = tableDetalle.getValueAt(selectedRow, 0).toString(); // Asumiendo que el ID está en la columna 0
            int cantidadEliminar = Integer.parseInt(tableDetalle.getValueAt(selectedRow, 2).toString()); // Cantidad a eliminar (columna 2)

            // Buscar el producto correspondiente en la tabla de productos
            for (int i = 0; i < tableProductos.getRowCount(); i++) {
                if (tableProductos.getValueAt(i, 0).toString().equals(idProducto)) {
                    // Recuperar el stock actual en la tabla de productos
                    int stockActual = Integer.parseInt(tableProductos.getValueAt(i, 3).toString()); // Columna 3: Stock
                    int nuevoStock = stockActual + cantidadEliminar; // Sumar la cantidad eliminada al stock
                    tableProductos.setValueAt(nuevoStock, i, 3); // Actualizar el stock en la tabla de productos
                    break; // Producto encontrado, romper el bucle
                }
            }

            // Eliminar el producto de la tabla de detalle
            ((DefaultTableModel) tableDetalle.getModel()).removeRow(selectedRow);
        }
    }

    fireEditingStopped(); // Detener la edición de la celda
}


    // Método para pedir cantidad con JOptionPane
    private int pedirCantidad() {
        String cantidadStr = JOptionPane.showInputDialog(null, "Ingrese la cantidad:", "Cantidad", JOptionPane.QUESTION_MESSAGE);
        if (cantidadStr != null && !cantidadStr.isEmpty()) {
            try {
                return Integer.parseInt(cantidadStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return 0; // Retorna 0 si el valor no es válido
    }

    // Método para agregar el producto a la tabla de detalle
    private void agregarProductoADetalle(int selectedRow, int cantidad) {
        DefaultTableModel detalleModel = (DefaultTableModel) tableDetalle.getModel();
        String idProducto = tableProductos.getValueAt(selectedRow, 0).toString(); // Columna 0: ID del producto
        String nombreProducto = tableProductos.getValueAt(selectedRow, 1).toString(); // Columna 1: Nombre del producto
        double precioUnitario = Double.parseDouble(tableProductos.getValueAt(selectedRow, 2).toString()); // Columna 2: Precio unitario
        double totalLinea = precioUnitario * cantidad;

        detalleModel.addRow(new Object[]{idProducto, nombreProducto, cantidad, precioUnitario, totalLinea, "Eliminar"}); // Agregar fila
    }
}
