package proyectofinal.sql;


public class QuerysClientes {
    
    private final String SELECCIONAR_All_CLIENTES = "SELECT * FROM clientes";
    private final String BUSCAR_CLIENTE_NIT = "SELECT * FROM clientes WHERE nit_cliente = ?";
    private final String ACTUALIZAR_CLIENTE = "UPDATE clientes SET nombre_cliente = ? WHERE nit_cliente = ?";
    private final String ELIMINAR_CLIENTE = "DELETE FROM clientes WHERE nit_cliente = ?";
    private final String INSERTAR_CLIENTE = "INSERT INTO clientes (nit_cliente, nombre_cliente) VALUES (?, ?)";


    public String getSELECCIONAR_All_CLIENTES() {
        return SELECCIONAR_All_CLIENTES;
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

    public String getINSERTAR_CLIENTE(){
        return INSERTAR_CLIENTE;
    }

}