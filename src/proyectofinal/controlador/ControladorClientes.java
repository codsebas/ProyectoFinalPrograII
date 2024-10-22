
package proyectofinal.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import proyectofinal.implementacion.ClientesImp;
import proyectofinal.modelos.ModeloCliente;


public class ControladorClientes implements ActionListener, WindowListener, MouseListener{
    
    ModeloCliente modelo;
    ClientesImp implementacion = new ClientesImp();

    public ControladorClientes(ModeloCliente modelo) {
        this.modelo = modelo;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
        if(e.getComponent().equals(modelo.getVista())){
            modelo.getVista().tblClientes.setModel(implementacion.modeloCliente());
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
        if(e.getComponent().equals(modelo.getVista().tblClientes)){
            modelo.getVista().txtBuscarNit.setText(String.valueOf(modelo.getVista().tblClientes.getValueAt(modelo.getVista().tblClientes.getSelectedRow(), 0)));
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
