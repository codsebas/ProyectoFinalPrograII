/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.sql;

/**
 *
 * @author javie
 */
public class QuerysDetalleInventario {
    private final String MOSTRAR_TODOS_DETALLES = "SELECT * FROM detalle_inventarios";
    private final String BUSCAR_DETALLE_INVENTARIOS = "SELECT * FROM detalle_inventarios WHERE id_detalle=?";

    public String getMOSTRAR_TODOS_DETALLES() {
        return MOSTRAR_TODOS_DETALLES;
    }

   

    public String getBUSCAR_DETALLE_INVENTARIOS() {
        return BUSCAR_DETALLE_INVENTARIOS;
    }
    
    
    
}
