/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.table.DefaultTableModel;
import proyectofinal.implementacion.DetalleInventarioImp;
import proyectofinal.modelos.ModeloDetalleInventario;

/**
 *
 * @author javie
 */
public class ControladorDetalleInventario implements ActionListener, WindowListener, MouseListener {

    ModeloDetalleInventario modelo;
    DetalleInventarioImp implementacion = new DetalleInventarioImp();

    public ControladorDetalleInventario(ModeloDetalleInventario modelo) {
        this.modelo = modelo;
    }

    public void mostrar() {
ModeloDetalleInventario model =implementacion.mostrarCliente(Integer.parseInt(modelo.getVista().txtBuscarDetalle.getText()));
modelo.getVista().txtBuscarDetalle.setText(String.valueOf(model.getIdDetalle()));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(modelo.getVista().btnVisualizar.getActionCommand())) {
            if (modelo.getVista().txtBuscarDetalle.getText().equals("")) {
                modelo.getVista().tblDetallesInventarios.setModel(implementacion.modeloDetalleInventario());
                

            } else {
                modelo.getVista().tblDetallesInventarios.setModel(implementacion.modeloDetalleInventario(Integer.parseInt(modelo.getVista().txtBuscarDetalle.getText())));
                mostrar();
            }

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        if (e.getComponent().equals(modelo.getVista())) {
            modelo.getVista().tblDetallesInventarios.setModel(implementacion.modeloDetalleInventario());
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

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getComponent().equals(modelo.getVista().tblDetallesInventarios)){
             modelo.getVista().txtBuscarDetalle.setText(String.valueOf(modelo.getVista().tblDetallesInventarios.getValueAt(modelo.getVista().tblDetallesInventarios.getSelectedRow(), 0)));
             modelo.getVista().txtDetalle.setText(String.valueOf(modelo.getVista().tblDetallesInventarios.getValueAt(modelo.getVista().tblDetallesInventarios.getSelectedRow(), 0)));
           modelo.getVista().txtProducto.setText(String.valueOf(modelo.getVista().tblDetallesInventarios.getValueAt(modelo.getVista().tblDetallesInventarios.getSelectedRow(), 1)));
         modelo.getVista().txtUser.setText(String.valueOf(modelo.getVista().tblDetallesInventarios.getValueAt(modelo.getVista().tblDetallesInventarios.getSelectedRow(), 2)));
           modelo.getVista().txtFecha.setText(String.valueOf(modelo.getVista().tblDetallesInventarios.getValueAt(modelo.getVista().tblDetallesInventarios.getSelectedRow(), 3)));
            modelo.getVista().txtCantidad.setText(String.valueOf(modelo.getVista().tblDetallesInventarios.getValueAt(modelo.getVista().tblDetallesInventarios.getSelectedRow(), 4)));
         modelo.getVista().txtTipoModificacion.setText(String.valueOf(modelo.getVista().tblDetallesInventarios.getValueAt(modelo.getVista().tblDetallesInventarios.getSelectedRow(), 5)));
          modelo.getVista().txtMotivoModificacion.setText(String.valueOf(modelo.getVista().tblDetallesInventarios.getValueAt(modelo.getVista().tblDetallesInventarios.getSelectedRow(), 6)));
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

}
