package proyectofinal.sql;

public class QuerysDetalleVentas {

    private final String SELECCIONAR_TODOS_LOS_DETALLES_VENTAS = "SELECT * FROM detalle_ventas";
    private final String BUSCAR_DETALLES_POR_FACTURA = "SELECT * FROM detalle_ventas WHERE factura_no = ?";
    private final String ACTUALIZAR_DETALLE_VENTA = "UPDATE detalle_ventas SET cantidad_producto = ?, total_linea = ? WHERE factura_no = ? AND num_linea = ?";
    private final String ELIMINAR_DETALLE_VENTA = "DELETE FROM detalle_ventas WHERE factura_no = ? AND num_linea = ?";
    private final String INSERTAR_DETALLE_VENTA = "INSERT INTO detalle_ventas (factura_no, num_linea, producto_id, cantidad_producto, precio_unitario, total_linea) VALUES (?, ?, ?, ?, ?, ?)";
    private final String SELECCIONAR_STOCK_PRODUCTO = "SELECT stock_producto FROM inventarios WHERE producto_id = ?";
    private final String ACTUALIZAR_STOCK_PRODUCTO = "UPDATE inventarios SET stock_producto = ? WHERE producto_id = ?";
    private final String GUARDAR_DETALLE_INVENTARIO = "INSERT INTO detalle_inventarios "
            + "(producto_id, usuario_user, cantidad_modificada, tipo_modificacion, motivo_modificacion) "
            + "VALUES (?, ?, ?, ?, ?)";

    public String getSELECCIONAR_TODOS_LOS_DETALLES_VENTAS() {
        return SELECCIONAR_TODOS_LOS_DETALLES_VENTAS;
    }

    public String getBUSCAR_DETALLES_POR_FACTURA() {
        return BUSCAR_DETALLES_POR_FACTURA;
    }

    public String getACTUALIZAR_DETALLE_VENTA() {
        return ACTUALIZAR_DETALLE_VENTA;
    }

    public String getELIMINAR_DETALLE_VENTA() {
        return ELIMINAR_DETALLE_VENTA;
    }

    public String getINSERTAR_DETALLE_VENTA() {
        return INSERTAR_DETALLE_VENTA;
    }

    public String getSELECCIONAR_STOCK_PRODUCTO() {
        return SELECCIONAR_STOCK_PRODUCTO;
    }

    public String getACTUALIZAR_STOCK_PRODUCTO() {
        return ACTUALIZAR_STOCK_PRODUCTO;
    }

    public String getGUARDAR_DETALLE_INVENTARIO() {
        return GUARDAR_DETALLE_INVENTARIO;
    }

}
