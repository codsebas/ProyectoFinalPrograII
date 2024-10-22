/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.controlador;

import proyectofinal.dao.UsuarioDao;
import proyectofinal.modelos.ModeloUsuario;
import proyectofinal.vistas.VistaMenu;

/**
 *
 * @author gerso
 */


public class ControladorUsuario {
  private UsuarioDao usuarioDAO; 

    public ControladorUsuario() {
        this.usuarioDAO = new UsuarioDao();
        verificarAdmin(); 
    }

    private void verificarAdmin() {
        if (usuarioDAO.obtenerUsuarioPorRol("admin") == null) {
            ModeloUsuario nuevoAdmin = new ModeloUsuario();
            nuevoAdmin.setUsuario("admin");
            nuevoAdmin.setPassword("123"); 
            nuevoAdmin.setRol("admin");
            usuarioDAO.registrarUsuario(nuevoAdmin.getUsuario(), nuevoAdmin.getPassword(), nuevoAdmin.getRol()); 
        }
    }

    public String iniciarSesion(String usuario, String password) {
        // Validar que los campos no estén vacíos
        if (usuario == null || usuario.isEmpty() || password == null || password.isEmpty()) {
            return "Por favor, complete ambos campos.";
        }

        // Validar usuario
        ModeloUsuario usuarioValido = usuarioDAO.validarUsuario(usuario, password);
        if (usuarioValido == null) {
            return "Credenciales incorrectas.";
        }

        // Redirigir según el rol
        String rol = usuarioValido.getRol();
        VistaMenu menu = new VistaMenu(rol); // Crea la vista del menú con el rol del usuario
        menu.setVisible(true); // Muestra el menú
        return "Inicio de sesión exitoso como " + rol + ".";
    }
    
    public void registrarUsuario(String usuario, String nombre, String password, String rol) {
        if (usuario.isEmpty() || password.isEmpty() || nombre.isEmpty()) {
            throw new IllegalArgumentException("Los campos no pueden estar vacíos");
        }
        ModeloUsuario nuevoUsuario = new ModeloUsuario();
        nuevoUsuario.setUsuario(usuario);
        nuevoUsuario.setPassword(password);
        nuevoUsuario.setRol(rol);
        
        usuarioDAO.registrarUsuario(nuevoUsuario.getUsuario(), nuevoUsuario.getPassword(), nuevoUsuario.getRol());
    }

    public ModeloUsuario validarUsuario(String usuario, String password) {
        return usuarioDAO.validarUsuario(usuario, password);
    }
}