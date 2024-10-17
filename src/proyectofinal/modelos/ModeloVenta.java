/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.modelos;

import java.util.List;

/**
 *
 * @author gerso
 */
public class ModeloVenta {
    
    private String idVenta;
    private ModeloCliente cliente;
    private List<ModeloProducto> productos;
    private double total;
    private String tipoPago;
           
    
    public double calcularTotalConIVA(){
        double totalConIVA = this.total*1.12;
        if(this.tipoPago.equals("tarjeta")){
            totalConIVA *= 1.05;
        }
        
 
    
    return totalConIVA;
    
    
}

    public ModeloVenta() {
    }

    public ModeloVenta(String idVenta, ModeloCliente cliente, List<ModeloProducto> productos, double total, String tipoPago) {
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.productos = productos;
        this.total = total;
        this.tipoPago = tipoPago;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public ModeloCliente getCliente() {
        return cliente;
    }

    public void setCliente(ModeloCliente cliente) {
        this.cliente = cliente;
    }

    public List<ModeloProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<ModeloProducto> productos) {
        this.productos = productos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }
    

    
}