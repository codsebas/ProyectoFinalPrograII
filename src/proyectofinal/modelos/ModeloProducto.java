/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.modelos;

/**
 *
 * @author gerso
 */

public class ModeloProducto {
    
    private String codigo;
    private String nombre;
    private String categoria;
    private double precioNormal;
    private double precioPromocion;
    private int stock;
    private String imagenProducto;

    public ModeloProducto() {
    }

    public ModeloProducto(String codigo, String nombre, String categoria, double precioNormal, double precioPromocion, int stock, String imagenProducto) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioNormal = precioNormal;
        this.precioPromocion = precioPromocion;
        this.stock = stock;
        this.imagenProducto = imagenProducto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecioNormal() {
        return precioNormal;
    }

    public void setPrecioNormal(double precioNormal) {
        this.precioNormal = precioNormal;
    }

    public double getPrecioPromocion() {
        return precioPromocion;
    }

    public void setPrecioPromocion(double precioPromocion) {
        this.precioPromocion = precioPromocion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }
    
    
    
    
    
            
    
}
