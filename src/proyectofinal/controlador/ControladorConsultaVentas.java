package proyectofinal.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.table.DefaultTableModel;
import proyectofinal.implementacion.ClientesImp;
import proyectofinal.implementacion.DetalleVentasImp;
import proyectofinal.implementacion.VentasImp;
import proyectofinal.modelos.ModeloConsultaVenta;

/**
 *
 * @author sebas
 */
public class ControladorConsultaVentas implements ActionListener, MouseListener, WindowListener {

    ClientesImp impcli = new ClientesImp();
    ModeloConsultaVenta modelo;

    DetalleVentasImp impDet = new DetalleVentasImp();
    VentasImp implementacion = new VentasImp();

    public ControladorConsultaVentas(ModeloConsultaVenta modelo) {
        this.modelo = modelo;
    }

    private void limpiar() {
        modelo.getVista().txtBuscar.setText("");
        modelo.getVista().txtFactura.setText("");
        modelo.getVista().txtUsuario.setText("");
        modelo.getVista().txtNitCliente.setText("");
        modelo.getVista().txtFechaVenta.setText("");
        modelo.getVista().txtSubtotall.setText("");
        modelo.getVista().txtImpuestos.setText("");
        modelo.getVista().txtCargosAdicionales.setText("");
        modelo.getVista().txtTotalVenta.setText("");
        modelo.getVista().txtMetodoPago.setText("");
        DefaultTableModel detalleModel = (DefaultTableModel) modelo.getVista().tblListaDetalle.getModel();
        detalleModel.setRowCount(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(modelo.getVista().btnBuscar.getActionCommand())) {
            if (modelo.getVista().txtBuscar.getText().equals("")) {
                modelo.getVista().tblListaVentas.setModel(implementacion.modeloConsultaVenta());
                limpiar();
            } else {
                modelo.getVista().tblListaVentas.setModel(implementacion.modeloVenta(Integer.parseInt(modelo.getVista().txtBuscar.getText())));
                mostrarVentas();
            }
        } else if (e.getActionCommand().equals(modelo.getVista().btnLimpiar.getActionCommand())) {
            limpiar();
        } else if (e.getActionCommand().equals(modelo.getVista().btnCancelar.getActionCommand())) {
            modelo.getVista().tblListaVentas.setModel(implementacion.modeloConsultaVenta());
            limpiar();
        }
    }

    private void mostrarVentas() {
        ModeloConsultaVenta modeloConsulta = implementacion.mostrarConsultaVenta(Integer.parseInt(modelo.getVista().txtBuscar.getText()));
        modelo.getVista().txtBuscar.setText(String.valueOf(modeloConsulta.getNoFactura()));
        modelo.getVista().txtFactura.setText(String.valueOf(modeloConsulta.getNoFactura()));
        modelo.getVista().txtUsuario.setText(modeloConsulta.getUser());
        modelo.getVista().txtNitCliente.setText(modeloConsulta.getNit());
        modelo.getVista().txtFechaVenta.setText(modeloConsulta.getFechaVenta());
        modelo.getVista().txtSubtotall.setText(String.valueOf(modeloConsulta.getSubtotal()));
        modelo.getVista().txtImpuestos.setText(String.valueOf(modeloConsulta.getImpuestos()));
        modelo.getVista().txtCargosAdicionales.setText(String.valueOf(modeloConsulta.getCargoTarjeta()));
        modelo.getVista().txtTotalVenta.setText(String.valueOf(modeloConsulta.getTotalVenta()));
        modelo.getVista().txtMetodoPago.setText(modeloConsulta.getMetodoPago());
        modelo.getVista().tblListaDetalle.setModel(impDet.modeloDetalleVenta(Integer.parseInt(modelo.getVista().txtFactura.getText())));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent().equals(modelo.getVista().tblListaVentas)) {
            modelo.getVista().txtBuscar.setText(String.valueOf(modelo.getVista().tblListaVentas.getValueAt(modelo.getVista().tblListaVentas.getSelectedRow(), 0)));
            modelo.getVista().txtFactura.setText(String.valueOf(modelo.getVista().tblListaVentas.getValueAt(modelo.getVista().tblListaVentas.getSelectedRow(), 0)));
            modelo.getVista().txtUsuario.setText(String.valueOf(modelo.getVista().tblListaVentas.getValueAt(modelo.getVista().tblListaVentas.getSelectedRow(), 1)));
            modelo.getVista().txtNitCliente.setText(String.valueOf(modelo.getVista().tblListaVentas.getValueAt(modelo.getVista().tblListaVentas.getSelectedRow(), 2)));
            modelo.getVista().txtFechaVenta.setText(String.valueOf(modelo.getVista().tblListaVentas.getValueAt(modelo.getVista().tblListaVentas.getSelectedRow(), 3)));
            modelo.getVista().txtSubtotall.setText(String.valueOf(modelo.getVista().tblListaVentas.getValueAt(modelo.getVista().tblListaVentas.getSelectedRow(), 4)));
            modelo.getVista().txtImpuestos.setText(String.valueOf(modelo.getVista().tblListaVentas.getValueAt(modelo.getVista().tblListaVentas.getSelectedRow(), 5)));
            modelo.getVista().txtCargosAdicionales.setText(String.valueOf(modelo.getVista().tblListaVentas.getValueAt(modelo.getVista().tblListaVentas.getSelectedRow(), 6)));
            modelo.getVista().txtTotalVenta.setText(String.valueOf(modelo.getVista().tblListaVentas.getValueAt(modelo.getVista().tblListaVentas.getSelectedRow(), 7)));
            modelo.getVista().txtMetodoPago.setText(String.valueOf(modelo.getVista().tblListaVentas.getValueAt(modelo.getVista().tblListaVentas.getSelectedRow(), 8)));
            modelo.getVista().tblListaDetalle.setModel(impDet.modeloDetalleVenta(Integer.parseInt(modelo.getVista().txtFactura.getText())));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
        if (e.getComponent().equals(modelo.getVista())) {
            modelo.getVista().tblListaVentas.setModel(implementacion.modeloConsultaVenta());
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
