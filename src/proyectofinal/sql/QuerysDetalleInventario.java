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
    private final String MOSTRAR_DETALLES_INVENTARIOS = "select * from detalle_inventarios";
    private final String BUSCAR_DETALLE_INVENTARIOS = "SELECT * FROM detalle_invetarios WHERE id_detalle = ?";

    public String getMOSTRAR_DETALLES_INVENTARIOS() {
        return MOSTRAR_DETALLES_INVENTARIOS;
    }

    public String getBUSCAR_DETALLE_INVENTARIOS() {
        return BUSCAR_DETALLE_INVENTARIOS;
    }
    
    
    
}
