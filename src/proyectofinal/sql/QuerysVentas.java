/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.sql;

/**
 *
 * @author sebas
 */
public class QuerysVentas {

    private final String SELECCIONAR_TODAS_LAS_VENTAS = "SELECT * FROM ventas";
    private final String BUSCAR_VENTA_POR_FACTURA = "SELECT * FROM ventas WHERE no_factura = ?";
    private final String ACTUALIZAR_VENTA = "UPDATE ventas SET total_venta = ?, metodo_pago = ? WHERE no_factura = ?";
    private final String ELIMINAR_VENTA = "DELETE FROM ventas WHERE no_factura = ?";
    private final String INSERTAR_VENTA = "INSERT INTO ventas (usuario_user, cliente_nit, fecha_venta, total_sin_impuestos, total_con_impuestos, cargo_tarjeta, total_venta, metodo_pago) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public String getSELECCIONAR_TODAS_LAS_VENTAS() {
        return SELECCIONAR_TODAS_LAS_VENTAS;
    }

    public String getBUSCAR_VENTA_POR_FACTURA() {
        return BUSCAR_VENTA_POR_FACTURA;
    }

    public String getACTUALIZAR_VENTA() {
        return ACTUALIZAR_VENTA;
    }

    public String getELIMINAR_VENTA() {
        return ELIMINAR_VENTA;
    }

    public String getINSERTAR_VENTA() {
        return INSERTAR_VENTA;
    }

    
}
