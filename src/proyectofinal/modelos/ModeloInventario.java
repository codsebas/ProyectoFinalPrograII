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
    
    private int idProducto;
    private int stock;

    public ModeloInventario() {
    }

    public ModeloInventario(int stock) {
        this.stock = stock;
    }

    public ModeloInventario(int idProducto, int stock) {
        this.idProducto = idProducto;
        this.stock = stock;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    
    
    
    
    
    
    
    
    
}
