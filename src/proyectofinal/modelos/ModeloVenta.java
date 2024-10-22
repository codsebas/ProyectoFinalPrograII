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
public class ModeloVenta {

    private String usuario;
    private String nitCliente;
    private double totalSinImpuestos;
    private double totalImpuestos;
    private double cargoTarjeta;
    private double totalVenta;
    private String metodoPago;
    VistaVentas vista;

    public ModeloVenta() {
    }

    public ModeloVenta(VistaVentas vista) {
        this.vista = vista;
    }

    public ModeloVenta(String usuario, String nitCliente, double totalSinImpuestos, double totalImpuestps, double cargoTarjeta, double totalVenta, String metodoPago) {
        this.usuario = usuario;
        this.nitCliente = nitCliente;
        this.totalSinImpuestos = totalSinImpuestos;
        this.totalImpuestos = totalImpuestps;
        this.cargoTarjeta = cargoTarjeta;
        this.totalVenta = totalVenta;
        this.metodoPago = metodoPago;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public double getTotalSinImpuestos() {
        return totalSinImpuestos;
    }

    public void setTotalSinImpuestos(double totalSinImpuestos) {
        this.totalSinImpuestos = totalSinImpuestos;
    }

    public double getTotalImpuestos() {
        return totalImpuestos;
    }

    public void setTotalImpuestps(double totalImpuestps) {
        this.totalImpuestos = totalImpuestps;
    }

    public double getCargoTarjeta() {
        return cargoTarjeta;
    }

    public void setCargoTarjeta(double cargoTarjeta) {
        this.cargoTarjeta = cargoTarjeta;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public VistaVentas getVista() {
        return vista;
    }

    public void setVista(VistaVentas vista) {
        this.vista = vista;
    }

}
