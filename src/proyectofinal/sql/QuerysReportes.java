/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.sql;

/**
 *
 * @author javie
 */
public class QuerysReportes {
       private final String REORTEPDF = "SELECT c.nombre_cliente, v.*\n" +
"FROM ventas v\n" +
"JOIN clientes c ON v.cliente_nit = c.nit_cliente\n" +
"WHERE v.fecha_venta >= CURDATE()\n" +
"  AND v.fecha_venta < CURDATE() + INTERVAL 1 DAY";

       
       private final String ReporteFactura ="SELECT \n" +
"    ventas.no_factura, \n" +
"    ventas.cliente_nit, \n" +
"    ventas.fecha_venta, \n" +
"    ventas.total_sin_impuestos, \n" +
"    ventas.total_con_impuestos, \n" +
"    ventas.cargo_tarjeta, \n" +
"    ventas.total_venta, \n" +
"    ventas.metodo_pago, \n" +
"    detalle_ventas.cantidad_producto, \n" +
"    detalle_ventas.precio_unitario, \n" +
"    detalle_ventas.total_linea\n" +
"FROM \n" +
"    ventas\n" +
"JOIN \n" +
"    detalle_ventas ON ventas.no_factura = detalle_ventas.factura_no";

    public String getReporteFactura() {
        return ReporteFactura;
    }
       
       
       
       
    public String getREORTEPDF() {
        return REORTEPDF;
    }
    
       
   
}
