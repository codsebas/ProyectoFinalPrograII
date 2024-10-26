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
    private ModeloVenta modelo;
    private JComboBox<String> cmbMetodoPago;

    public ButtonRenderer(JTable tableProductos, JTable tableDetalle, ModeloVenta modelo, JComboBox<String> comboBoxMetodoPago) {
        this.tableProductos = tableProductos;
        this.tableDetalle = tableDetalle;
        this.modelo = modelo; 
        this.cmbMetodoPago = comboBoxMetodoPago; 
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
            int cantidad = pedirCantidad();
            if (cantidad > 0) {
                int stockActual = Integer.parseInt(tableProductos.getValueAt(selectedRow, 3).toString()); // Columna 3: Stock actual
                if (cantidad > stockActual) {
                    JOptionPane.showMessageDialog(null, "No hay suficiente stock disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }
                agregarProductoADetalle(selectedRow, cantidad); 
                
                int nuevoStock = stockActual - cantidad;
                tableProductos.setValueAt(nuevoStock, selectedRow, 3); 
                recalcularTotales();
            }
        } else if (table.equals(tableDetalle)) {
            if (selectedRow != -1) {
                String idProducto = tableDetalle.getValueAt(selectedRow, 0).toString(); 
                int cantidadEliminar = Integer.parseInt(tableDetalle.getValueAt(selectedRow, 2).toString()); 

                for (int i = 0; i < tableProductos.getRowCount(); i++) {
                    if (tableProductos.getValueAt(i, 0).toString().equals(idProducto)) {
                        
                        int stockActual = Integer.parseInt(tableProductos.getValueAt(i, 3).toString()); 
                        int nuevoStock = stockActual + cantidadEliminar; 
                        tableProductos.setValueAt(nuevoStock, i, 3); 
                        break; 
                    }
                }

                ((DefaultTableModel) tableDetalle.getModel()).removeRow(selectedRow);
                recalcularTotales();
            }
        }

        fireEditingStopped();
    }

    
    private int pedirCantidad() {
        String cantidadStr = JOptionPane.showInputDialog(null, "Ingrese la cantidad:", "Cantidad", JOptionPane.QUESTION_MESSAGE);
        if (cantidadStr != null && !cantidadStr.isEmpty()) {
            try {
                return Integer.parseInt(cantidadStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return 0;
    }

    private void agregarProductoADetalle(int selectedRow, int cantidad) {
        DefaultTableModel detalleModel = (DefaultTableModel) tableDetalle.getModel();
        String idProducto = tableProductos.getValueAt(selectedRow, 0).toString(); 
        String nombreProducto = tableProductos.getValueAt(selectedRow, 1).toString(); 
        double precioUnitario = Double.parseDouble(tableProductos.getValueAt(selectedRow, 2).toString()); 
        double totalLinea = precioUnitario * cantidad;

        detalleModel.addRow(new Object[]{idProducto, nombreProducto, cantidad, precioUnitario, totalLinea, "Eliminar"}); 
    }

    private void recalcularTotales() {
        DefaultTableModel detalleModel = (DefaultTableModel) tableDetalle.getModel();
        double subtotal = 0.0;
        double impuestos = 0.0;
        double total = 0.0;
        double totalCargo = 0.0;
        double impuestoPorcentaje = 0.12; 
        double cargoTarjeta = 0.05; 

        // Calcular el subtotal
        for (int i = 0; i < detalleModel.getRowCount(); i++) {
            double totalLinea = Double.parseDouble(detalleModel.getValueAt(i, 4).toString()); 
            subtotal += totalLinea;
        }

        impuestos = subtotal * impuestoPorcentaje;

        // Verificar el método de pago seleccionado y aplicar cargo si es tarjeta
        if (cmbMetodoPago.getSelectedItem().equals("tarjeta")) {
            total = subtotal + impuestos; // Sumar subtotal e impuestos
            totalCargo = total * cargoTarjeta;
            total += totalCargo;
        } else {
            total = subtotal + impuestos; 
            totalCargo = 0;
        }
        System.out.println("Total de cargo por tarjeta");
        System.out.println(totalCargo);
        // Actualizar los campos de texto con los resultados
        modelo.getVista().txtCargosAdicionales.setText(String.format("%.2f", totalCargo));
        modelo.getVista().txtSubtotal.setText(String.format("%.2f", subtotal));
        modelo.getVista().txtImpuestos.setText(String.format("%.2f", impuestos));
        modelo.getVista().txtTotalFinal.setText(String.format("%.2f", total));
    }
}
