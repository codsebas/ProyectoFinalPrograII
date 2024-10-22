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
    private int categoriaId;
    private String nombreProducto;
    private double precioNormal;
    private double precioPromocion;
    private String descripcion;
    private String rutaImagenProducto;
    private String rutaImagenCodigoBarras;

    // Getters y setters para cada campo
    public int getIdProducto() {
        return idProducto;
    }
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCategoriaId() {
        return categoriaId;
    }
    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
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

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRutaImagenProducto() {
        return rutaImagenProducto;
    }
    public void setRutaImagenProducto(String rutaImagenProducto) {
        this.rutaImagenProducto = rutaImagenProducto;
    }

    public String getRutaImagenCodigoBarras() {
        return rutaImagenCodigoBarras;
    }
    public void setRutaImagenCodigoBarras(String rutaImagenCodigoBarras) {
        this.rutaImagenCodigoBarras = rutaImagenCodigoBarras;
    }
}