/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.modelos;

/**
 *
 * @author gerso
 */
public class ModeloInventario {
    
    public void actualizarStock ( ModeloProducto producto, int cantidad, 
            ModeloUsuario usuario, String razon){
        
        int stockActualizado = producto.getStock() + cantidad;
        producto.setStock(stockActualizado);
    }
    
}
