/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.modelos;

/**
 *
 * @author sebas
 */
public class ModeloInsertarDetalleInventario {
    private int productoId;
    private String usuario;
    private int cantidadModificada;
    private String tipoModificacion;
    private String MotivoModificacion;

    public ModeloInsertarDetalleInventario() {
    }

    public ModeloInsertarDetalleInventario(int productoId, String usuario, int cantidadModificada, String tipoModificacion, String MotivoModificacion) {
        this.productoId = productoId;
        this.usuario = usuario;
        this.cantidadModificada = cantidadModificada;
        this.tipoModificacion = tipoModificacion;
        this.MotivoModificacion = MotivoModificacion;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getCantidadModificada() {
        return cantidadModificada;
    }

    public void setCantidadModificada(int cantidadModificada) {
        this.cantidadModificada = cantidadModificada;
    }

    public String getTipoModificacion() {
        return tipoModificacion;
    }

    public void setTipoModificacion(String tipoModificacion) {
        this.tipoModificacion = tipoModificacion;
    }

    public String getMotivoModificacion() {
        return MotivoModificacion;
    }

    public void setMotivoModificacion(String MotivoModificacion) {
        this.MotivoModificacion = MotivoModificacion;
    }
    
    
    }
