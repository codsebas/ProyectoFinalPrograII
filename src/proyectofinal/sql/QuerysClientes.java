package proyectofinal.sql;


public class QuerysClientes {
    
    private final String SELECCIONAR_CLIENTES = "SELECT * FROM clientes";
    private final String BUSCAR_CLIENTE_NIT = "SELECT * FROM clientes WHERE nit_cliente = ?";
    private final String ACTUALIZAR_CLIENTE = "UPDATE clientes SET nombre_cliente = ?, direccion_cliente = ?, telefono_cliente = ? WHERE nit_cliente = ?";
    private final String ELIMINAR_CLIENTE = "DELETE FROM clientes WHERE nit_cliente = ?";

    public String getSELECCIONAR_CLIENTES() {
        return SELECCIONAR_CLIENTES;
    }

    public String getBUSCAR_CLIENTE_NIT() {
        return BUSCAR_CLIENTE_NIT;
    }

    public String getACTUALIZAR_CLIENTE() {
        return ACTUALIZAR_CLIENTE;
    }

    public String getELIMINAR_CLIENTE() {
        return ELIMINAR_CLIENTE;
    }

    

}
