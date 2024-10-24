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

    public String getREORTEPDF() {
        return REORTEPDF;
    }
    
       
   
}
