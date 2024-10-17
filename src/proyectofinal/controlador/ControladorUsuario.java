/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.controlador;


import java.util.ArrayList;
import java.util.List;
import proyectofinal.modelos.ModeloUsuario;

/**
 *
 * @author gerso
 */


public class ControladorUsuario {
    private List<ModeloUsuario> usuarios;
    
    
    public ControladorUsuario(){
        
        usuarios = new ArrayList<>();
        
        ModeloUsuario admin = new ModeloUsuario();
        admin.setUsuario("admin");
        admin.setPassword("123");
        admin.setRol("admin");
        usuarios.add(admin);
        
    }

    // Método para validar el login
    public ModeloUsuario validarUsuario(String usuario, String password) {
        for (ModeloUsuario usuarioExistente : usuarios) {
            if (usuarioExistente.getUsuario().equals(usuario) && usuarioExistente.getPassword().equals(password)) {
                return usuarioExistente; // Usuario encontrado
            }
        }
        return null; // Usuario no encontrado
    }
        
        
        
       
        
    }
    

