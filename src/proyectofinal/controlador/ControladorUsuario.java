/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.controlador;


import java.util.ArrayList;
import java.util.List;
import proyectofinal.dao.UsuarioDao;
import proyectofinal.modelos.ModeloUsuario;

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
            // Si no existe, creamos un nuevo admin
            ModeloUsuario nuevoAdmin = new ModeloUsuario();
            nuevoAdmin.setUsuario("admin");
            nuevoAdmin.setPassword("123"); 
            nuevoAdmin.setRol("admin");
            usuarioDAO.registrarUsuario(nuevoAdmin); 
            
        }
    }

    public void registrarUsuario(String usuario, String password, String rol) {
        if (usuario.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Los campos no pueden estar vacíos");
        }
        ModeloUsuario nuevoUsuario = new ModeloUsuario();
        nuevoUsuario.setUsuario(usuario);
        nuevoUsuario.setPassword(password);
        nuevoUsuario.setRol(rol);
        usuarioDAO.registrarUsuario(nuevoUsuario);
    }

    public ModeloUsuario validarUsuario(String usuario, String password) {
        return usuarioDAO.validarUsuario(usuario, password);
    }
}