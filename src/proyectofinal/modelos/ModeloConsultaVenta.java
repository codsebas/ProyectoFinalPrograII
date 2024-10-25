package proyectofinal.modelos;
import proyectofinal.vistas.VistaConsultaVentas;
/**
 *
 * @author sebas
 */
public class ModeloConsultaVenta {
    private int noFactura;
    private String user;
    private String nit;
    private String fechaVenta;
    private double subtotal;
    private double impuestos;
    private double cargoTarjeta;
    private double totalVenta;
    private String metodoPago;
    VistaConsultaVentas vista;

    public ModeloConsultaVenta() {
    }

    public ModeloConsultaVenta(VistaConsultaVentas vista) {
        this.vista = vista;
    }

    public ModeloConsultaVenta(int noFactura, String user, String nit, String fechaVenta, double subtotal, double impuestos, double cargoTarjeta, double totalVenta, String metodoPago) {
        this.noFactura = noFactura;
        this.user = user;
        this.nit = nit;
        this.fechaVenta = fechaVenta;
        this.subtotal = subtotal;
        this.impuestos = impuestos;
        this.cargoTarjeta = cargoTarjeta;
        this.totalVenta = totalVenta;
        this.metodoPago = metodoPago;
    }

    public int getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(int noFactura) {
        this.noFactura = noFactura;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(double impuestos) {
        this.impuestos = impuestos;
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

    public VistaConsultaVentas getVista() {
        return vista;
    }

    public void setVista(VistaConsultaVentas vista) {
        this.vista = vista;
    }
    
    
    
}
