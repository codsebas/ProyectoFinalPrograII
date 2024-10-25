/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.reportes;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import proyectofinal.modelos.ModeloReportesDia;
import proyectofinal.sql.Conector;
import proyectofinal.sql.QuerysReportes;








/**
 *
 * @author javie
 */
public class Reportes implements ActionListener {

    ModeloReportesDia modelo;

    public Reportes(ModeloReportesDia modelo) {
        this.modelo = modelo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(modelo.getVista().btnReportePDF.getActionCommand())) {
            generarPDF();
        } else if (e.getActionCommand().equals(modelo.getVista().btnReporteExcel.getActionCommand())) {
    generarExcel();
            
        }
    }

   public void generarPDF() {
    try {
        Conector conector = new Conector();
        PreparedStatement ps;
        QuerysReportes sql = new QuerysReportes();
        ResultSet rs;
             
        String carpetaDescargas = System.getProperty("user.home") + File.separator + "Downloads";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = formatter.format(new Date());
        String nombreArchivo = carpetaDescargas + File.separator + "EjercicioFactorial" + timestamp + ".pdf";

        FileOutputStream archivo = new FileOutputStream(nombreArchivo);
        Document documento = new Document();
        PdfWriter.getInstance(documento, archivo);
        documento.open();

        System.out.println("Ruta del archivo PDF: " + nombreArchivo);

        formatter = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = formatter.format(new Date());
        Paragraph dateParagraph = new Paragraph("Fecha: " + currentDate);
        dateParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
        documento.add(dateParagraph);

        com.itextpdf.text.Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph titulo = new Paragraph("Reportes del día", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        documento.add(titulo);
        documento.add(new Paragraph(" ")); // Espacio en blanco

        com.itextpdf.text.Font contenidoFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        com.itextpdf.text.Font encabezadoFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        
        PdfPTable tabla = new PdfPTable(10); // Número de columnas
        tabla.setWidthPercentage(100); // Ancho de la tabla
        
        float[] columnWidths = {3f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};
        tabla.setWidths(columnWidths);

        PdfPCell cell;
        String[] headers = {"Nombre Cliente", "No Factura", "Usuario", "No. NIT", "Fecha Venta", 
                            "Total Sin Impuestos", "Total Con Impuestos", "Cargo Tarjeta", 
                            "Total Venta", "Métodos de Pago"};
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

                        // Si es la columna "Total Venta" (número 9), acumula el total
                        if (i == 9) {
                            totalVentas += Double.parseDouble(rs.getString(i));
                        }
                    }
                } while (rs.next());
                
                // Agregar la fila con el total de ventas al final
                cell = new PdfPCell(new Phrase("Total Ventas", encabezadoFont));
                cell.setColspan(9); // Ocupa las primeras 9 columnas
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPadding(5);
                tabla.addCell(cell);

                cell = new PdfPCell(new Phrase(String.format("%.2f", totalVentas), contenidoFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                tabla.addCell(cell);

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

public void generarExcel() {
    try {
        // Conexión a la base de datos
        Conector conector = new Conector();
        PreparedStatement ps;
        QuerysReportes sql = new QuerysReportes();
        ResultSet rs;
        
        // Configuración del archivo Excel
        String carpetaDescargas = System.getProperty("user.home") + File.separator + "Downloads";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = formatter.format(new Date());
        String nombreArchivo = carpetaDescargas + File.separator + "ReporteVentas" + timestamp + ".xlsx";
        FileOutputStream archivo = new FileOutputStream(nombreArchivo);

        // Crear libro de trabajo y hoja
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reportes del día");

        // Establecer estilos
        CellStyle dateStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        // Crear fila para la fecha
        Row dateRow = sheet.createRow(0);
        Cell dateCell = dateRow.createCell(9);
        dateCell.setCellValue(new Date());
        dateCell.setCellStyle(dateStyle);

        // Crear título
        org.apache.poi.ss.usermodel.Font tituloFont = workbook.createFont();
        tituloFont.setBold(true);
        tituloFont.setFontHeightInPoints((short) 16);
        CellStyle tituloStyle = workbook.createCellStyle();
        tituloStyle.setFont(tituloFont);
        tituloStyle.setAlignment(HorizontalAlignment.CENTER);

        Row tituloRow = sheet.createRow(1);
        Cell tituloCell = tituloRow.createCell(0);
        tituloCell.setCellValue("Reportes del día");
        tituloCell.setCellStyle(tituloStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));

        // Crear encabezados de tabla
        Row headerRow = sheet.createRow(3);
        String[] headers = {"Nombre Cliente", "No Factura", "Usuario", "No. NIT", "Fecha Venta", 
                            "Total sin Impuestos", "Total con Impuestos", "Cargo Tarjeta", 
                            "Total Venta", "Métodos de Pago"};
        for (int i = 0; i < headers.length; i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headers[i]);
        }

        // Variable para acumular el total de ventas
        double totalVentas = 0;

        // Conectar y rellenar datos
        conector.conectar();
        ps = conector.preparar(sql.getREORTEPDF());
        rs = ps.executeQuery();
        int rowNum = 4;
        while (rs.next()) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(rs.getString(i + 1));
            }
            // Acumular el total de la columna "Total Venta" (índice 8)
            totalVentas += Double.parseDouble(rs.getString(9));
        }
        conector.desconectar();

        // Agregar fila con el total de ventas
        Row totalRow = sheet.createRow(rowNum);
        Cell totalLabelCell = totalRow.createCell(8);
        totalLabelCell.setCellValue("Total Ventas");
        CellStyle totalStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font totalFont = workbook.createFont();
        totalFont.setBold(true);
        totalStyle.setFont(totalFont);
        totalLabelCell.setCellStyle(totalStyle);

        Cell totalValueCell = totalRow.createCell(9);
        totalValueCell.setCellValue(totalVentas);
        totalValueCell.setCellStyle(totalStyle);

        // Autoajustar el tamaño de las columnas
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Escribir y cerrar archivo
        workbook.write(archivo);
        workbook.close();
        archivo.close();

        JOptionPane.showMessageDialog(null, "Guardado en: " + nombreArchivo, "Reporte generado exitosamente", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException | IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Ocurrió un error al generar el Excel", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

}
        

