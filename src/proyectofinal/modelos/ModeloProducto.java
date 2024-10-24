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
  
    
    private int idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private int idCategoria;
    private String descripcionCategoria;
    private double precioNormalProducto;
    private double precioPromocion;
    private String rutaImagen;
    private String rutaCodigoBarras;

    public ModeloProducto() {
        
    }

    public ModeloProducto(String nombreProducto, String descripcionProducto, int idCategoria, String descripcionCategoria, double precioNormalProducto, double precioPromocion, String rutaImagen, String rutaCodigoBarras) {
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.idCategoria = idCategoria;
        this.descripcionCategoria = descripcionCategoria;
        this.precioNormalProducto = precioNormalProducto;
        this.precioPromocion = precioPromocion;
        this.rutaImagen = rutaImagen;
        this.rutaCodigoBarras = rutaCodigoBarras;
    }

    public ModeloProducto(int idProducto, String nombreProducto, String descripcionProducto, int idCategoria, String descripcionCategoria, double precioNormalProducto, double precioPromocion, String rutaImagen, String rutaCodigoBarras) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.idCategoria = idCategoria;
        this.descripcionCategoria = descripcionCategoria;
        this.precioNormalProducto = precioNormalProducto;
        this.precioPromocion = precioPromocion;
        this.rutaImagen = rutaImagen;
        this.rutaCodigoBarras = rutaCodigoBarras;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

    public double getPrecioNormalProducto() {
        return precioNormalProducto;
    }

    public void setPrecioNormalProducto(double precioNormalProducto) {
        this.precioNormalProducto = precioNormalProducto;
    }

    public double getPrecioPromocion() {
        return precioPromocion;
    }

    public void setPrecioPromocion(double precioPromocion) {
        this.precioPromocion = precioPromocion;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getRutaCodigoBarras() {
        return rutaCodigoBarras;
    }

    public void setRutaCodigoBarras(String rutaCodigoBarras) {
        this.rutaCodigoBarras = rutaCodigoBarras;
    }

    
    
    
}
