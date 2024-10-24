package proyectofinal.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import proyectofinal.implementacion.ClientesImp;
import proyectofinal.modelos.ModeloCliente;

public class ControladorClientes implements ActionListener, WindowListener, MouseListener {

    ModeloCliente modelo;
    ClientesImp implementacion = new ClientesImp();

    public ControladorClientes(ModeloCliente modelo) {
        this.modelo = modelo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(modelo.getVista().btnBuscar.getActionCommand())) {
            if (modelo.getVista().txtBuscarNit.getText().equals("")) {
                modelo.getVista().tblClientes.setModel(implementacion.modeloCliente());
            } else {
                modelo.getVista().tblClientes.setModel(implementacion.modeloCliente(modelo.getVista().txtBuscarNit.getText()));
                mostrarCliente();
            }
        } else if (e.getActionCommand().equals(modelo.getVista().btnGuardar.getActionCommand())) {
            if (modelo.getVista().txtNit.getText().equals("") || modelo.getVista().txtNombre.getText().equals("")) {
                JOptionPane.showMessageDialog(null,
                        "No se puede proceder porque los campos están en blanco.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                boolean resultado;
                ModeloCliente modelo = new ModeloCliente();
                modelo.setNIT(this.modelo.getVista().txtNit.getText());
                modelo.setNombre(this.modelo.getVista().txtNombre.getText());
                resultado = implementacion.insertarCliente(modelo);
                if (!resultado) {
                    System.out.println("Inserción exitosa");
                    limpiar();
                    this.modelo.getVista().tblClientes.setModel(implementacion.modeloCliente());
                } else {
                    System.out.println("Inserción falló");
                }
            }

        } else if (e.getActionCommand().equals(modelo.getVista().btnEliminar.getActionCommand())) {
            boolean resultado;
            if (modelo.getVista().txtNit.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Error", "Datos no válidos", 1);
            } else {
                resultado = implementacion.eliminarCliente(modelo.getVista().txtNit.getText());
                if (!resultado) {
                    System.out.println("Eliminación exitosa");
                    limpiar();
                    this.modelo.getVista().tblClientes.setModel(implementacion.modeloCliente());
                } else {
                    System.out.println("Problemas al eliminar");
                }
            }

        } else if (e.getActionCommand().equals(modelo.getVista().btnActualizar.getActionCommand())) {
            boolean resultado;
            if (modelo.getVista().txtNit.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Error", "Datos no válidos", 1);
            } else {
                ModeloCliente modelo = new ModeloCliente();
                modelo.setNIT(this.modelo.getVista().txtNit.getText());
                modelo.setNombre(this.modelo.getVista().txtNombre.getText());
                resultado = implementacion.actualizarCliente(modelo);
                if (!resultado) {
                    System.out.println("Actualización exitosa");
                    limpiar();
                    this.modelo.getVista().tblClientes.setModel(implementacion.modeloCliente());
                } else {
                    System.out.println("Actualización fallida");
                }
            }
        } else if (e.getActionCommand().equals(modelo.getVista().btnLimpiar.getActionCommand())) {
            limpiar();
        } else if (e.getActionCommand().equals(modelo.getVista().btnCancelar.getActionCommand())) {
            limpiar();
            modelo.getVista().tblClientes.setModel(implementacion.modeloCliente());
        } else if (e.getActionCommand().equals(modelo.getVista().btnRegresar.getActionCommand())) {
            this.modelo.getVista().dispose();
        }

    }

    public void mostrarCliente() {
        ModeloCliente model = implementacion.mostrarCliente(modelo.getVista().txtBuscarNit.getText());
        modelo.getVista().txtNit.setText(model.getNIT());
        modelo.getVista().txtNombre.setText(model.getNombre());
    }

    public void limpiar() {
        modelo.getVista().txtBuscarNit.setText("");
        modelo.getVista().txtNombre.setText("");
        modelo.getVista().txtNit.setText("");
    }

    @Override
    public void windowOpened(WindowEvent e) {
        if (e.getComponent().equals(modelo.getVista())) {
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
        if (e.getComponent().equals(modelo.getVista().tblClientes)) {
            modelo.getVista().txtBuscarNit.setText(String.valueOf(modelo.getVista().tblClientes.getValueAt(modelo.getVista().tblClientes.getSelectedRow(), 0)));
            modelo.getVista().txtNit.setText(String.valueOf(modelo.getVista().tblClientes.getValueAt(modelo.getVista().tblClientes.getSelectedRow(), 0)));
            modelo.getVista().txtNombre.setText(String.valueOf(modelo.getVista().tblClientes.getValueAt(modelo.getVista().tblClientes.getSelectedRow(), 1)));
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
