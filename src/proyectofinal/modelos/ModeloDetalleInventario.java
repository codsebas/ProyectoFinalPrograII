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
    private int IdDetalleF;
    private int idProductoF;
    private String FechaF;
    private int cantidadF;
     

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

    public ModeloDetalleInventario(VistaDetalleInventarios vista, int idDetalle, int productoId, String usuario, String fechaModificacion, int cantidadModificada, String tipoModificacion, String MotivoModificacion, int IdDetalleF, int idProductoF, String FechaF, int cantidadF) {
        this.vista = vista;
        this.idDetalle = idDetalle;
        this.productoId = productoId;
        this.usuario = usuario;
        this.fechaModificacion = fechaModificacion;
        this.cantidadModificada = cantidadModificada;
        this.tipoModificacion = tipoModificacion;
        this.MotivoModificacion = MotivoModificacion;
        this.IdDetalleF = IdDetalleF;
        this.idProductoF = idProductoF;
        this.FechaF = FechaF;
        this.cantidadF = cantidadF;
    }

    public int getIdDetalleF() {
        return IdDetalleF;
    }

    public void setIdDetalleF(int IdDetalleF) {
        this.IdDetalleF = IdDetalleF;
    }

    public int getIdProductoF() {
        return idProductoF;
    }

    public void setIdProductoF(int idProductoF) {
        this.idProductoF = idProductoF;
    }

    public String getFechaF() {
        return FechaF;
    }

    public void setFechaF(String FechaF) {
        this.FechaF = FechaF;
    }

    public int getCantidadF() {
        return cantidadF;
    }

    public void setCantidadF(int cantidadF) {
        this.cantidadF = cantidadF;
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
