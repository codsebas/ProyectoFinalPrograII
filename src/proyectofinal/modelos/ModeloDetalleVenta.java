/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.modelos;
import proyectofinal.vistas.VistaVentas;
/**
 *
 * @author sebas
 */
public class ModeloDetalleVenta {
    private int numFactura;
    private int numLinea;
    private int productoId;
    private int cantidadProducto;
    private double  precioUnitario;
    private double totalLinea;
    VistaVentas vista;

    public ModeloDetalleVenta() {
    }

    public ModeloDetalleVenta(VistaVentas vista) {
        this.vista = vista;
    }

    public ModeloDetalleVenta(int numFactura, int numLinea, int productoId, int cantidadProducto, double precioUnitario, double totalLinea) {
        this.numFactura = numFactura;
        this.numLinea = numLinea;
        this.productoId = productoId;
        this.cantidadProducto = cantidadProducto;
        this.precioUnitario = precioUnitario;
        this.totalLinea = totalLinea;
    }

    public int getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(int numFactura) {
        this.numFactura = numFactura;
    }

    public int getNumLinea() {
        return numLinea;
    }

    public void setNumLinea(int numLinea) {
        this.numLinea = numLinea;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getTotalLinea() {
        return totalLinea;
    }

    public void setTotalLinea(double totalLinea) {
        this.totalLinea = totalLinea;
    }

    public VistaVentas getVista() {
        return vista;
    }

    public void setVista(VistaVentas vista) {
        this.vista = vista;
    }
    
        
}
