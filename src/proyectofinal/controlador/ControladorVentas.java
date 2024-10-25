/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.controlador;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import proyectofinal.sql.Conector;
import proyectofinal.sql.QuerysReportes;
import proyectofinal.vistas.VistaClientes;

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

    public List<ModeloDetalleVenta> agregarDetalleAModelo(JTable tblListaProductos, int numFactura) {
        List<ModeloDetalleVenta> detallesVenta = new ArrayList<>();

        if (tblListaProductos.getRowCount() == 0) {
            System.out.println("No hay productos en la lista de productos");
            return detallesVenta; // Retornar lista vacía si no hay productos
        }

        // Agregar lógica para llenar el modelo de detalles de venta
        for (int i = 0; i < tblListaProductos.getRowCount(); i++) {
            ModeloDetalleVenta detalle = new ModeloDetalleVenta();
            detalle.setNumFactura(numFactura);
            detalle.setNumLinea(i + 1);
            detalle.setProductoId((Integer.parseInt(tblListaProductos.getValueAt(i, 0).toString()))); // ID del producto
            detalle.setCantidadProducto(Integer.parseInt(tblListaProductos.getValueAt(i, 2).toString()));
            detalle.setPrecioUnitario(Double.parseDouble(tblListaProductos.getValueAt(i, 3).toString()));
            detalle.setTotalLinea(Double.parseDouble(tblListaProductos.getValueAt(i, 4).toString()));
            detallesVenta.add(detalle);
        }

        return detallesVenta;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(modelo.getVista().btnAgregarCliente.getActionCommand())) { //Llama a mantenimiento de clientes
            VistaClientes vistaClientes = new VistaClientes();
            vistaClientes.setVisible(true);
            modelo.getVista().cmbClientes.setModel(impVenta.mostrarCliente());
        } else if (e.getActionCommand().equals(modelo.getVista().btnBuscar.getActionCommand())) { //Busca productos

        } else if (e.getActionCommand().equals(modelo.getVista().btnCancelar.getActionCommand())) { //Cancela busqueda

        } else if (e.getActionCommand().equals(modelo.getVista().btnGuardar.getActionCommand())) { //Guarda venta y detalle de venta
            int numeroFactura;
            System.out.println("Guardando");
            ModeloVenta modeloVenta = new ModeloVenta();
            modeloVenta.setUsuario("admin");
            String clienteSeleccionado = this.modelo.getVista().cmbClientes.getSelectedItem().toString();
            String[] partesCliente = clienteSeleccionado.split("-");
            String nitCliente = partesCliente[0];
            modeloVenta.setNitCliente(nitCliente);
            modeloVenta.setTotalSinImpuestos(Double.parseDouble(this.modelo.getVista().txtSubtotal.getText()));
            modeloVenta.setTotalImpuestos(Double.parseDouble(this.modelo.getVista().txtImpuestos.getText()));
            modeloVenta.setCargoTarjeta(Double.parseDouble(this.modelo.getVista().txtCargosAdicionales.getText()));
            modeloVenta.setTotalVenta(Double.parseDouble(this.modelo.getVista().txtTotalFinal.getText()));
            modeloVenta.setMetodoPago("efectivo");
            numeroFactura = impVenta.insertarVenta(modeloVenta);

            if (numeroFactura > 0) {
                System.out.println("Inserción del encabezado exitosa");
                boolean resultadoDetalle;
                List<ModeloDetalleVenta> modeloDetalle = agregarDetalleAModelo(this.modelo.getVista().tblListaProductos, numeroFactura);
                resultadoDetalle = impDetVenta.insertarDetalleVenta(modeloDetalle);
                if (resultadoDetalle) {
                    //Logica de Reporte UwU
                    pdfFactura();
                    JOptionPane.showMessageDialog(null, "Se ha insertado todo sastifactoriamente", "Confirmacion", JOptionPane.INFORMATION_MESSAGE);
                    limpiar();
                } else {
                    JOptionPane.showInputDialog(null, "No se pudieron insertar los registros", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("Inserción del encabezado falló");
            }

        } else if (e.getActionCommand().equals(modelo.getVista().btnRegresar)) { //Regresa a vista anterior

        } else if (e.getActionCommand().equals(modelo.getVista().btnVaciarLista)) { //Vacía lista de productos

        } else if (e.getSource() == modelo.getVista().tblMostrarProductos) { // Selección de productos de la tabla
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
        }
    }

    
    public void pdfFactura (){
        
        try {
        Conector conector = new Conector();
        PreparedStatement ps;
        QuerysReportes sql = new QuerysReportes();
        ResultSet rs;
             
        String carpetaDescargas = System.getProperty("user.home") + File.separator + "Downloads";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = formatter.format(new Date());
        String nombreArchivo = carpetaDescargas + File.separator + "Factura" + timestamp + ".pdf";

        FileOutputStream archivo = new FileOutputStream(nombreArchivo);
        Document documento = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(documento, archivo);
        documento.open();

        System.out.println("Ruta del archivo PDF: " + nombreArchivo);

        formatter = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = formatter.format(new Date());
        Paragraph dateParagraph = new Paragraph("Fecha: " + currentDate);
        dateParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
        documento.add(dateParagraph);

        com.itextpdf.text.Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph titulo = new Paragraph("Factura", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        documento.add(titulo);
        documento.add(new Paragraph(" ")); // Espacio en blanco

        com.itextpdf.text.Font contenidoFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        com.itextpdf.text.Font encabezadoFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        
        PdfPTable tabla = new PdfPTable(11); // Número de columnas
        tabla.setWidthPercentage(100); // Ancho de la tabla
        
        float[] columnWidths = {3f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};
        tabla.setWidths(columnWidths);

        PdfPCell cell;
        String[] headers = {"no factura", "NIT", "fecha venta ", "total sin imp.", "total con imp.", 
                            "con tarjeta", "Total de venta", "metodo de pago", 
                            "cantidad producto", "precio unitario","total linea"};
        for (String header : headers) {
            cell = new PdfPCell(new Phrase(header, encabezadoFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderWidth(1);
            tabla.addCell(cell);
        }

        double totalVentas = 0;

        conector.conectar();
        try {
    ps = conector.preparar(sql.getREORTEPDF());
    rs = ps.executeQuery();

    if (rs.next()) {
        do {
            // Agregar filas de datos
            for (int i = 1; i <= 10; i++) {
                cell = new PdfPCell(new Phrase(rs.getString(i), contenidoFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                tabla.addCell(cell);
            }
        } while (rs.next());
        
        // No se agrega la fila con el total de ventas
        documento.add(tabla);
    }
    conector.desconectar();
    documento.close();

} catch (Exception e) {
    e.printStackTrace();
}

        documento.close();
        archivo.close();

        JOptionPane.showMessageDialog(null, "Guardado en: " + nombreArchivo, "Reporte generado exitosamente", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception e) {
        e.printStackTrace(); // Mostrar el error en la consola
        JOptionPane.showMessageDialog(null, "Ocurrió un error al generar el PDF", "Error", JOptionPane.ERROR_MESSAGE);
    }

        
    }
    @Override
    public void windowOpened(WindowEvent e) {
        if (e.getComponent().equals(modelo.getVista())) {
            DefaultTableModel model = impVenta.modeloProducto(); // Obtén el modelo de productos
            modelo.getVista().txtCargosAdicionales.setText("0");
            model.addColumn("Seleccionar");
            modelo.getVista().tblMostrarProductos.setModel(model);

            TableColumn productColumn = modelo.getVista().tblMostrarProductos.getColumnModel().getColumn(model.getColumnCount() - 1);
            productColumn.setCellRenderer(new ButtonRenderer(modelo.getVista().tblMostrarProductos, modelo.getVista().tblListaProductos, modelo)); // Pasa el modelo correctamente
            productColumn.setCellEditor(new ButtonRenderer(modelo.getVista().tblMostrarProductos, modelo.getVista().tblListaProductos, modelo));  // Pasa el modelo correctamente

            modelo.getVista().cmbClientes.setModel(impVenta.mostrarCliente());

            DefaultTableModel detalleModel = new DefaultTableModel();
            detalleModel.setColumnIdentifiers(new Object[]{"ID Producto", "Nombre", "Cantidad", "Precio Unitario", "Total Línea", "Eliminar"});
            modelo.getVista().tblListaProductos.setModel(detalleModel);

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
