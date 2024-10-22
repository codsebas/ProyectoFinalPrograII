/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.controlador;

import proyectofinal.dao.ProductoDao;
import proyectofinal.modelos.ModeloProducto;
import java.util.List;



public class ControladorProducto {
    private ProductoDao productoDAO;

    public ControladorProducto() {
        this.productoDAO = new ProductoDao();
    }

    public void registrarProducto(int categoriaId, String nombre, double precioNormal, double precioPromocion, String descripcion, String rutaImagenProducto, String rutaImagenCodigoBarras) {
        if (nombre.isEmpty() || precioNormal < 0 || precioPromocion < 0) {
            throw new IllegalArgumentException("Los campos no pueden estar vacíos y los precios deben ser mayores o iguales a cero");
        }
        ModeloProducto nuevoProducto = new ModeloProducto();
        nuevoProducto.setCategoriaId(categoriaId);
        nuevoProducto.setNombreProducto(nombre);
        nuevoProducto.setPrecioNormal(precioNormal);
        nuevoProducto.setPrecioPromocion(precioPromocion);
        nuevoProducto.setDescripcion(descripcion);
        nuevoProducto.setRutaImagenProducto(rutaImagenProducto);
        nuevoProducto.setRutaImagenCodigoBarras(rutaImagenCodigoBarras);
      
    }

    public List<ModeloProducto> obtenerTodosLosProductos() {
        return productoDAO.obtenerTodosLosProductos();
    }

    public ModeloProducto obtenerProductoPorId(int idProducto) {
        return productoDAO.obtenerProductoPorId(idProducto);
    }

    public void actualizarProducto(ModeloProducto producto) {
        if (producto.getNombreProducto().isEmpty() || producto.getPrecioNormal() < 0 || producto.getPrecioPromocion() < 0) {
            throw new IllegalArgumentException("Los campos no pueden estar vacíos y los precios deben ser mayores o iguales a cero");
        }
        productoDAO.actualizarProducto(producto);
    }

    public void eliminarProducto(int idProducto) {
        productoDAO.eliminarProducto(idProducto);
    }
}