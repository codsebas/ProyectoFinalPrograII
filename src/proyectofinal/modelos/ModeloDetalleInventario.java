/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.modelos;

import proyectofinal.vistas.VistaDetalleInventarios;

/**
 *
 * @author javie
 */
public class ModeloDetalleInventario {
    VistaDetalleInventarios vista;
    private int idDetalle;
    private int productoId;
    private String usuario;
    private String fechaModificacion;
    private int cantidadModificada;
    private String tipoModificacion;
    private String MotivoModificacion;

    public ModeloDetalleInventario() {
    }

    public ModeloDetalleInventario(VistaDetalleInventarios vista) {
        this.vista = vista;
    }

    public VistaDetalleInventarios getVista() {
        return vista;
    }

    public void setVista(VistaDetalleInventarios vista) {
        this.vista = vista;
    }

    public ModeloDetalleInventario(int idDetalle, int productoId, String usuario, String fechaModificacion, String tipoModificacion, String MotivoModificacion, int cantidadModificada) {
        this.idDetalle = idDetalle;
        this.productoId = productoId;
        this.usuario = usuario;
        this.fechaModificacion = fechaModificacion;
        this.tipoModificacion = tipoModificacion;
        this.MotivoModificacion = MotivoModificacion;
        this.cantidadModificada= cantidadModificada;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
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

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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

    public int getCantidadModificada() {
        return cantidadModificada;
    }

    public void setCantidadModificada(int cantidadModificada) {
        this.cantidadModificada = cantidadModificada;
    }
    
    
    
   

  
}
