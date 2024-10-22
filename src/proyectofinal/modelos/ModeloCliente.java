/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.modelos;

import proyectofinal.vistas.VistaClientes;

/**
 *
 * @author gerso
 */
public class ModeloCliente {

    private String NIT;
    private String nombre;
    VistaClientes vista;

    public ModeloCliente() {
    }

    public ModeloCliente(VistaClientes vista) {
        this.vista = vista;
    }

    public ModeloCliente(String NIT, String nombre) {
        this.NIT = NIT;
        this.nombre = nombre;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public VistaClientes getVista() {
        return vista;
    }

    public void setVista(VistaClientes vista) {
        this.vista = vista;
    }

}
